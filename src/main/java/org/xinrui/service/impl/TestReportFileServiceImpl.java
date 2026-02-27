package org.xinrui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xinrui.entity.*;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.*;
import org.xinrui.service.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TestReportFileServiceImpl implements TestReportFileService {

    private static final Long UPDATED_BY = 1L; // 固定更新人ID

    //todo 待配置
    @Value("${file.storage.root:D:/fileStorage}")
    private String fileStorageRoot;

    @Autowired
    private SampleInfoMapper sampleInfoMapper;

    @Autowired
    private TestReportFileInfoMapper testReportFileInfoMapper;

    @Autowired
    private PatientInfoMapper patientInfoMapper;

    @Transactional
    @Override
    public boolean receivePdfReport(MultipartFile file) {
        // 1. 验证文件
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            log.warn("文件为空/文件名为空");
            throw new BusinessException("文件为空/文件名为空");
        }
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            log.warn("文件格式错误，须为PDF");
            throw new BusinessException("文件格式错误，须为PDF");
        }

        // 2. 解析文件名
        String fileName = file.getOriginalFilename();
        String[] parts = fileName.split("_");
        if (parts.length != 7) {
            log.warn("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
            throw new BusinessException("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
        }

        // 3. 根据子文库号+芯片号+LaneId+姓名查询数据库
        SampleInfo sampleInfo = findSampleInfoByFileParts(parts);
        long sampleOid = sampleInfo.getOid();
        String sampleId = sampleInfo.getSampleId();

        // 4. 创建日期目录
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String dirPath = Paths.get(fileStorageRoot, currentDate).toString();
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 5. 生成新文件名（使用sample_id命名）
        String newFileName = sampleId + ".pdf";
        String filePath = Paths.get(dirPath, newFileName).toString();

        // 6. 保存文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("文件保存失败", e.getMessage());
            throw new BusinessException("文件保存失败");
        }

        // 7. 更新报告文件表
        int fileType = 1;
        updateReportFileRecord(sampleOid, currentDate, newFileName, fileType);
        return true;
    }


    @Transactional
    @Override
    public boolean receiveWordReport(MultipartFile file) {
        // 1. 验证文件
        String fileName = file.getOriginalFilename();
        if (file.isEmpty() || fileName == null) {
            log.warn("文件为空/文件名为空");
            throw new BusinessException("文件为空/文件名为空");
        }
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        if (!"docx".equals(extension) && !"doc".equals(extension)) {
            log.warn("文件格式错误，须为WORD");
            throw new BusinessException("文件格式错误，须为WORD");
        }

        // 2. 解析文件名
        String[] parts = fileName.split("_");
        if (parts.length != 7) {
            log.warn("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
            throw new BusinessException("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
        }

        // 3. 根据子文库号+芯片号+LaneId+姓名查询数据库
        SampleInfo sampleInfo = findSampleInfoByFileParts(parts);
        long sampleOid = sampleInfo.getOid();
        String sampleId = sampleInfo.getSampleId();

        // 4. 创建日期目录
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String dirPath = Paths.get(fileStorageRoot, currentDate).toString();
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 5. 生成新文件名（使用sample_id命名）
        String newFileName = sampleId + "." + extension;
        String filePath = Paths.get(dirPath, newFileName).toString();

        // 6. 保存文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("文件保存失败", e.getMessage());
            throw new BusinessException("文件保存失败");
        }

        // 7. 更新报告文件表
        int fileType = 0;
        updateReportFileRecord(sampleOid, currentDate, newFileName, fileType);
        return true;
    }


    /**
     * 向表t_lis_test_report_file插入文件路径记录
     */
    private void updateReportFileRecord(long sampleOid, String currentDate, String newFileName, int fileType) {
        String relativePath = currentDate + "/" + newFileName;

        // 检查是否已存在记录
        TestReportFileInfo existingFile = testReportFileInfoMapper.selectOne(
                new QueryWrapper<TestReportFileInfo>()
                        .eq("sample_oid", sampleOid)
                        .eq("file_type", fileType)
        );

        TestReportFileInfo reportFile = new TestReportFileInfo();
        reportFile.setSampleOid(sampleOid);
        reportFile.setFilePath(relativePath);
        reportFile.setFileType(fileType); // 1表示PDF,0为word
        reportFile.setIntver(0);
        reportFile.setUpdatedBy(UPDATED_BY);
        reportFile.setUpdatedOn(LocalDateTime.now());

        if (existingFile != null) {
            testReportFileInfoMapper.updateById(reportFile);
        } else {
            testReportFileInfoMapper.insert(reportFile);
        }
    }


    /**
     * 根据文件名解析的子文库号、芯片号、LaneId查询SampleInfo，再查询patientInfo验证患者姓名
     *
     * @param parts 文件名拆分后的数组
     * @return SampleInfo
     * @throws BusinessException 如果未查询到匹配记录、查询到多条匹配记录但姓名不一致、或没有找到匹配的姓名
     */
    private SampleInfo findSampleInfoByFileParts(String[] parts) {
        String subLibraryNo = parts[0]; // 子文库号
        String chipNo = parts[1]; // 芯片号
        String laneId = parts[2]; // LaneId
        String patientName = parts[3]; // 姓名

        // 1. 根据子文库号、芯片号、LaneId查询SampleInfo
        List<SampleInfo> sampleInfos = sampleInfoMapper.selectList(
                new QueryWrapper<SampleInfo>()
                        .eq("pooling_sub_id", subLibraryNo)
                        .eq("slide_id", chipNo)
                        .eq("lane_id", laneId)
        );

        if (sampleInfos == null || sampleInfos.isEmpty()) {
            log.warn("未查询到匹配的样本记录 - 子文库号: {}, 芯片号: {}, LaneId: {}", subLibraryNo, chipNo, laneId);
            throw new BusinessException("未查询到匹配的样本记录");
        }

        // 2. 遍历匹配的记录，查找患者姓名匹配的记录
        List<SampleInfo> matchingSamples = new ArrayList<>();
        for (SampleInfo sample : sampleInfos) {
            // 根据sampleInfo中的patient_oid查询PatientInfo
            PatientInfo patientInfo = patientInfoMapper.selectOne(
                    new QueryWrapper<PatientInfo>()
                            .eq("oid", sample.getPatientOid())
            );

            // 确保患者信息存在且姓名匹配
            if (patientInfo != null && patientInfo.getName() != null &&
                    patientInfo.getName().equals(patientName)) {
                matchingSamples.add(sample);
            }
        }

        // 3. 处理匹配结果
        if (matchingSamples.isEmpty()) {
            log.warn("查询到匹配的样本记录，但患者姓名不匹配 - 文件提取姓名: {}, 匹配的样本记录: {}",
                    patientName, sampleInfos);
            throw new BusinessException("患者姓名不匹配");
        }

        if (matchingSamples.size() > 1) {
            log.warn("查询到多条匹配的样本记录（姓名相同） - 文件提取姓名: {}, 匹配的样本记录: {}",
                    patientName, matchingSamples);
            throw new BusinessException("查询到多条匹配的样本记录，数据异常");
        }

        // 4. 返回唯一匹配的样本
        return matchingSamples.get(0);
    }

}

