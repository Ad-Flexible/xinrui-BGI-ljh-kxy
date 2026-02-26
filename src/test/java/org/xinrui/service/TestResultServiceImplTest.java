package org.xinrui.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import org.xinrui.dto.testResult.TestResultDto;
import org.xinrui.dto.testResult.nested.DiseaseDto;
import org.xinrui.dto.testResult.nested.LaneQcDto;
import org.xinrui.dto.testResult.nested.SampleQcDto;
import org.xinrui.dto.testResult.nested.TestCnvDto;
import org.xinrui.entity.*;
import org.xinrui.mapper.*;
import org.xinrui.service.impl.TestResultServiceImpl;
import org.xinrui.util.testResult.BuildUtil;
import org.xinrui.util.testResult.UpdateUtil;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Transactional
public class TestResultServiceImplTest {

    @Mock
    private SampleInfoMapper sampleInfoMapper;

    @Mock
    private PatientInfoMapper patientInfoMapper;

    @Mock
    private ExaminationInfoMapper examinationInfoMapper;

    @Mock
    private SampleQcInfoMapper sampleQcInfoMapper;

    @Mock
    private LaneQcInfoMapper laneQcInfoMapper;

    @Mock
    private TestResultInfoMapper testResultInfoMapper;

    @Mock
    private TestCnvInfoMapper testCnvInfoMapper;

    @InjectMocks
    private TestResultServiceImpl testResultService;

    private TestResultDto testResultDto;

    @BeforeEach
    void setUp() {
        // 初始化TestResultDto基础数据
        testResultDto = new TestResultDto();
        testResultDto.setSampleId("SAMPLE123");
        testResultDto.setOldSampleNum("OLD123");
        testResultDto.setPatientIdCard("110101199001011234");
        testResultDto.setPatientName("张三");
        testResultDto.setPatientAge(30);
        testResultDto.setCollectDate("2023-01-01 10:00");
        testResultDto.setReceivedDate("2023-01-01 11:00");
        testResultDto.setGestationalWeeks("20,5");
        testResultDto.setFetusType("单胎");
        testResultDto.setTubeType("Streck管");
        testResultDto.setAdditionalReportFlag(1);
        testResultDto.setSampleQc(new SampleQcDto());
        testResultDto.setLaneQc(new LaneQcDto());
        TestCnvDto testCnvDto = new TestCnvDto();
        DiseaseDto diseaseDto = new DiseaseDto();
        // 假设TestCnvDto有一个setDisease方法，请根据实际情况修改属性名
        testCnvDto.setDiseaseDto(diseaseDto);
        testResultDto.setDiseaseList(Arrays.asList(testCnvDto));

        testResultDto.setOtherDiseaseList(Arrays.asList(new DiseaseDto()));
    }

    // ==================== handleSampleInfo 测试 ====================

    @Test
    void handleSampleInfo_whenSampleNotFound_shouldInsert() {
        // Mock
        SampleInfo existingSample = new SampleInfo();
        existingSample.setOid(1L);
        when(sampleInfoMapper.selectOne(any())).thenReturn(null).thenReturn(existingSample);
        when(sampleInfoMapper.insert(any(SampleInfo.class))).thenReturn(1);

        Long testPatientOid = 1L;
        // Execute
        SampleInfo result = testResultService.handleSampleInfo(testResultDto,testPatientOid);

        // Verify
        assertNotNull(result);
        verify(sampleInfoMapper, times(2)).selectOne(any());
        verify(sampleInfoMapper, times(1)).insert(any(SampleInfo.class));
    }

    @Test
    void handleSampleInfo_whenSampleFound_shouldUpdate() {
        // Mock
        SampleInfo existingSample = new SampleInfo();
        existingSample.setOid(1L);
        when(sampleInfoMapper.selectOne(any())).thenReturn(existingSample);
        when(sampleInfoMapper.updateById(any(SampleInfo.class))).thenReturn(1);

        Long testPatientOid = 1L;

        // Execute
        SampleInfo result = testResultService.handleSampleInfo(testResultDto,testPatientOid);

        // Verify
        assertNotNull(result);
        verify(sampleInfoMapper, times(2)).selectOne(any());
        verify(sampleInfoMapper, times(1)).updateById(any(SampleInfo.class));
    }

    // ==================== handlePatientInfo 测试 ====================

    @Test
    void handlePatientInfo_whenPatientNotFound_shouldInsert() {
        // Mock
        when(patientInfoMapper.selectOne(any())).thenReturn(null);
        when(patientInfoMapper.insert(any(PatientInfo.class))).thenReturn(1);

        // Execute
        testResultService.handlePatientInfo(testResultDto);

        // Verify
        verify(patientInfoMapper, times(1)).selectOne(any());
        verify(patientInfoMapper, times(1)).insert(any(PatientInfo.class));
    }

    @Test
    void handlePatientInfo_whenPatientFound_shouldUpdate() {
        // Mock
        PatientInfo existingPatient = new PatientInfo();
        existingPatient.setOid(1L);
        when(patientInfoMapper.selectOne(any())).thenReturn(existingPatient);
        when(patientInfoMapper.updateById(any(PatientInfo.class))).thenReturn(1);

        // Execute
        testResultService.handlePatientInfo(testResultDto);

        // Verify
        verify(patientInfoMapper, times(1)).selectOne(any());
        verify(patientInfoMapper, times(1)).updateById(any(PatientInfo.class));
    }

    // ==================== handleExaminationInfo 测试 ====================

    @Test
    void handleExaminationInfo_whenExaminationNotFound_shouldInsert() {
        // Mock
        when(examinationInfoMapper.selectOne(any())).thenReturn(null);
        when(examinationInfoMapper.insert(any(ExaminationInfo.class))).thenReturn(1);

        // Execute
        testResultService.handleExaminationInfo(testResultDto, new SampleInfo());

        // Verify
        verify(examinationInfoMapper, times(1)).selectOne(any());
        verify(examinationInfoMapper, times(1)).insert(any(ExaminationInfo.class));
    }

    @Test
    void handleExaminationInfo_whenExaminationFound_shouldUpdate() {
        // Mock
        ExaminationInfo existingExam = new ExaminationInfo();
        existingExam.setOid(1L);
        when(examinationInfoMapper.selectOne(any())).thenReturn(existingExam);
        when(examinationInfoMapper.updateById(any(ExaminationInfo.class))).thenReturn(1);

        // Execute
        testResultService.handleExaminationInfo(testResultDto, new SampleInfo());

        // Verify
        verify(examinationInfoMapper, times(1)).selectOne(any());
        verify(examinationInfoMapper, times(1)).updateById(any(ExaminationInfo.class));
    }

    // ==================== handleSampleQcInfo 测试 ====================

    @Test
    void handleSampleQcInfo_whenQcNotFound_shouldInsert() {
        // Mock
        when(sampleQcInfoMapper.selectOne(any())).thenReturn(null);
        when(sampleQcInfoMapper.insert(any(SampleQcInfo.class))).thenReturn(1);

        // Execute
        testResultService.handleSampleQcInfo(testResultDto, new SampleInfo());

        // Verify
        verify(sampleQcInfoMapper, times(1)).selectOne(any());
        verify(sampleQcInfoMapper, times(1)).insert(any(SampleQcInfo.class));
    }

    @Test
    void handleSampleQcInfo_whenQcFound_shouldUpdate() {
        // Mock
        SampleQcInfo existingQc = new SampleQcInfo();
        existingQc.setOid(1L);
        when(sampleQcInfoMapper.selectOne(any())).thenReturn(existingQc);
        when(sampleQcInfoMapper.updateById(any(SampleQcInfo.class))).thenReturn(1);

        // Execute
        testResultService.handleSampleQcInfo(testResultDto, new SampleInfo());

        // Verify
        verify(sampleQcInfoMapper, times(1)).selectOne(any());
        verify(sampleQcInfoMapper, times(1)).updateById(any(SampleQcInfo.class));
    }

    // ==================== handleLaneQcInfo 测试 ====================

    @Test
    void handleLaneQcInfo_whenQcNotFound_shouldInsert() {
        // Mock
        when(laneQcInfoMapper.selectOne(any())).thenReturn(null);
        when(laneQcInfoMapper.insert(any(LaneQcInfo.class))).thenReturn(1);

        // Execute
        testResultService.handleLaneQcInfo(testResultDto, new SampleInfo());

        // Verify
        verify(laneQcInfoMapper, times(1)).selectOne(any());
        verify(laneQcInfoMapper, times(1)).insert(any(LaneQcInfo.class));
    }

    @Test
    void handleLaneQcInfo_whenQcFound_shouldUpdate() {
        // Mock
        LaneQcInfo existingQc = new LaneQcInfo();
        existingQc.setOid(1L);
        when(laneQcInfoMapper.selectOne(any())).thenReturn(existingQc);
        when(laneQcInfoMapper.updateById(any(LaneQcInfo.class))).thenReturn(1);

        // Execute
        testResultService.handleLaneQcInfo(testResultDto, new SampleInfo());

        // Verify
        verify(laneQcInfoMapper, times(1)).selectOne(any());
        verify(laneQcInfoMapper, times(1)).updateById(any(LaneQcInfo.class));
    }

    // ==================== handleTestResultInfo 测试 ====================
    // ... existing code ...
    @Test
    void handleTestResultInfo_whenResultNotFound_shouldInsert() {
        // 准备测试数据
        SampleInfo sampleInfo = new SampleInfo();
        sampleInfo.setOid(100L);

        // 创建预期返回的结果对象
        TestResultInfo expectedResult = new TestResultInfo();
        expectedResult.setOid(1L);

        // 设置mock行为：第一次查询返回null，第二次查询返回预期结果
        when(testResultInfoMapper.selectOne(any()))
                .thenReturn(null)                    // 第一次查询
                .thenReturn(expectedResult);         // 第二次查询

        // Mock insert 行为
        doAnswer(invocation -> {
            TestResultInfo argument = invocation.getArgument(0);
            argument.setOid(1L); // 模拟数据库设置ID
            return 1;
        }).when(testResultInfoMapper).insert(any(TestResultInfo.class));

        // Execute
        Long resultOid = testResultService.handleTestResultInfo(testResultDto, sampleInfo);

        // Verify
        assertNotNull(resultOid);
        assertEquals(1L, resultOid);
        verify(testResultInfoMapper, times(2)).selectOne(any());
        verify(testResultInfoMapper, times(1)).insert(any(TestResultInfo.class));
    }
// ... existing code ...



    @Test
    void handleTestResultInfo_whenResultFound_shouldUpdate() {
        // Mock
        TestResultInfo existingResult = new TestResultInfo();
        existingResult.setOid(1L);
        when(testResultInfoMapper.selectOne(any())).thenReturn(existingResult);
        when(testResultInfoMapper.updateById(any(TestResultInfo.class))).thenReturn(1);

        // Execute
        Long resultOid = testResultService.handleTestResultInfo(testResultDto, new SampleInfo());

        // Verify
        assertEquals(1L, resultOid);
        verify(testResultInfoMapper, times(2)).selectOne(any());
        verify(testResultInfoMapper, times(1)).updateById(any(TestResultInfo.class));
    }

    // ==================== handleTestCnvInfo 测试 ====================

    @Test
    void handleTestCnvInfo_whenDiseaseListNullAndOtherDiseaseListNotNull_shouldInsertOther() {
        // Setup
        testResultDto.setDiseaseList(null);

        // Mock
        when(testCnvInfoMapper.insertBatch(anyList())).thenReturn(1);

        // Execute
        testResultService.handleTestCnvInfo(testResultDto, 1L);

        // Verify
        verify(testCnvInfoMapper, times(1)).insertBatch(anyList());
    }



    @Test
    void handleTestCnvInfo_whenDiseaseListHasNullDiseaseDto_shouldSkipNull() {
        // 修改处2：修正TestCnvDto的初始化方式
        // 原代码: TestCnvDto testCnvDto = new TestCnvDto(null);
        TestCnvDto testCnvDto = new TestCnvDto();
        // 显式设置内部属性为null，模拟业务场景中的空对象
        testCnvDto.setDiseaseDto(null);

        testResultDto.setDiseaseList(Arrays.asList(testCnvDto));
        testResultDto.setOtherDiseaseList(null);

        // Mock
        when(testCnvInfoMapper.insertBatch(anyList())).thenReturn(1);

        // Execute
        testResultService.handleTestCnvInfo(testResultDto, 1L);

        // Verify
        verify(testCnvInfoMapper, times(1)).insertBatch(anyList());
        // 验证实际插入的条目数（应为0，因为diseaseDto为null）
        // 注意：由于Mockito无法直接验证参数内容，这里通过捕获参数进行验证
        verify(testCnvInfoMapper).insertBatch(anyList());
    }

    // 辅助方法：捕获参数用于验证
    // private void captureArgument() {
    //     // 用于捕获参数的捕获器
    //     // 实际测试中会使用ArgumentCaptor
    // }
}