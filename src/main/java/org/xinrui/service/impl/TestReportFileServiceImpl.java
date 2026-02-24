package org.xinrui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xinrui.dto.ApiResponse;
import org.xinrui.entity.*;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.*;
import org.xinrui.service.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class TestReportFileServiceImpl implements TestReportFileService {

    private static final Long UPDATED_BY = 1L; // 固定更新人ID

    //todo 待配置
   // @Value("")
    private String fileStorageRoot;

    @Autowired
    private TestResultInfoMapper testResultInfoMapper;

    @Autowired
    private TestReportFileInfoMapper testReportFileInfoMapper;

    @Transactional
    @Override
    public boolean receivePdfReport(MultipartFile file) {
        try {
            // 1. 验证文件
            if (file.isEmpty()) {
                log.warn("文件为空");
                throw new BusinessException("文件为空");
            }
            if (!file.getOriginalFilename().endsWith(".pdf")) {
                log.warn("文件格式错误，须为PDF");
                throw new BusinessException("文件格式错误，须为PDF");
            }

            // 2. 解析文件名
            String fileName = file.getOriginalFilename();
            String[] parts = fileName.split("_");
            if (parts.length < 4) {
                log.warn("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
                throw new BusinessException("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
            }
            String subLibraryNo = parts[0];// 子文库号
            String chipNo = parts[1];// 芯片号
            String laneId = parts[2];// LaneId
            String patientName = parts[3];// 姓名


            // 3. 根据子文库号+芯片号+LaneId+姓名查询数据库
            TestResultInfo testResult = testResultInfoMapper.selectOne(
                    new QueryWrapper<TestResultInfo>()
                            .eq("sub_library_no", subLibraryNo)
                            .eq("chip_no", chipNo)
                            .eq("lane_id", laneId)
                            .eq("patient_name", patientName)
            );

            if (testResult == null) {
                log.warn("未查询到匹配的样本记录");
                throw new BusinessException("未查询到匹配的样本记录");
            }

            Long sampleOid = testResult.getSampleOid();

            // 4. 创建日期目录
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String dirPath = Paths.get(fileStorageRoot, currentDate).toString();
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 5. 生成新文件名（使用sample_oid命名）
            String newFileName = sampleOid + ".pdf";
            String filePath = Paths.get(dirPath, newFileName).toString();

            // 6. 保存文件
            file.transferTo(new File(filePath));

            // 7. 更新报告文件表
            int fileType = 1;
            updateReportFileRecord(sampleOid, currentDate, newFileName, fileType);

            return true;
        } catch (IOException e) {
            log.error("文件保存失败", e);
            throw new BusinessException("文件保存失败");
        } catch (Exception e) {
            log.warn("系统异常: " + e.getMessage());
            throw new BusinessException("系统异常");
        }
    }


    @Transactional
    @Override
    public boolean receiveWordReport(MultipartFile file) {
        try {
            // 1. 验证文件
            if (file.isEmpty()) {
                log.warn("文件为空");
                throw new BusinessException("文件为空");
            }
            if (!file.getOriginalFilename().endsWith(".word")) {
                log.warn("文件格式错误，须为WORD");
                throw new BusinessException("文件格式错误，须为WORD");
            }

            // 2. 解析文件名
            String fileName = file.getOriginalFilename();
            String[] parts = fileName.split("_");
            if (parts.length < 4) {
                log.warn("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
                throw new BusinessException("文件名命名错误，不符合要求（子文库号_芯片号_LaneId_姓名_医院名称_报告类型_时间标识.pdf）");
            }

            String subLibraryNo = parts[0];// 子文库号
            String chipNo = parts[1];// 芯片号
            String laneId = parts[2];// LaneId
            String patientName = parts[3];// 姓名

            // 3. 根据子文库号+芯片号+LaneId+姓名查询数据库
            TestResultInfo testResult = testResultInfoMapper.selectOne(
                    new QueryWrapper<TestResultInfo>()
                            .eq("sub_library_no", subLibraryNo)
                            .eq("chip_no", chipNo)
                            .eq("lane_id", laneId)
                            .eq("patient_name", patientName)
            );

            if (testResult == null) {
                log.warn("未查询到匹配的样本记录");
                throw new BusinessException("未查询到匹配的样本记录");
            }

            Long sampleOid = testResult.getSampleOid();

            // 4. 创建日期目录
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String dirPath = Paths.get(fileStorageRoot, currentDate).toString();
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 5. 生成新文件名（使用sample_oid命名）
            String newFileName = sampleOid + ".word";
            String filePath = Paths.get(dirPath, newFileName).toString();

            // 6. 保存文件
            file.transferTo(new File(filePath));

            // 7. 更新报告文件表
            int fileType = 0;
            updateReportFileRecord(sampleOid, currentDate, newFileName, fileType);

            return true;
        } catch (IOException e) {
            log.error("文件保存失败", e);
            throw new BusinessException("文件保存失败");
        } catch (Exception e) {
            log.warn("系统异常: " + e.getMessage());
            throw new BusinessException("系统异常");
        }
    }


    /**
     * 向表t_lis_test_report_file插入文件路径记录
     */
    private void updateReportFileRecord(Long sampleOid, String currentDate, String newFileName, int fileType) {
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
}

