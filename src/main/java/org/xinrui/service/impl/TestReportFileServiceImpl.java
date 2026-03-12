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
    @Value("${file.storage.root:D:/service/BGI/HalosReportFile}")
    private String fileStorageRoot;

    @Autowired
    private SampleInfoMapper sampleInfoMapper;

    @Autowired
    private TestReportFileInfoMapper testReportFileInfoMapper;



    @Transactional
    @Override
    public boolean receiveReport(MultipartFile file) {
        // 1. 验证文件
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            log.warn("文件为空/文件名为空");
            throw new BusinessException("文件为空/文件名为空");
        }

        String fileName = file.getOriginalFilename();
        log.info("接收文件: {}", fileName);
        String extension = getFileExtension(fileName);

        // 2. 验证文件格式
        int fileType;
        int extensionLen;
        if ("pdf".equals(extension)) {
            fileType = 1; // PDF
            extensionLen = 4; // ".pdf" 长度
        } else if ("docx".equals(extension) || "doc".equals(extension)) {
            fileType = 0; // WORD
            extensionLen = extension.length() + 1;
        } else {
            log.warn("文件格式错误，须为 PDF 或 WORD");
            throw new BusinessException("文件格式错误，须为 PDF 或 WORD");
        }

        // 3. 解析文件名 - 提取 sampleId
        String sampleId = extractSampleIdFromFileName(fileName, extensionLen);

        // 4. 根据 sampleId 查询数据库
        SampleInfo sampleInfo = findSampleInfoBySampleId(sampleId);
        long sampleOid = sampleInfo.getOid();

        // 5. 创建日期目录
        String subDir = fileType == 1 ? "pdf" : "word";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/" + subDir;
        String dirPath = Paths.get(fileStorageRoot, currentDate).toString();
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 6. 使用原始文件名保存
        String filePath = Paths.get(dirPath, fileName).toString();

        // 7. 保存文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("文件保存失败", e.getMessage());
            throw new BusinessException("文件保存失败");
        }

        // 8. 更新报告文件表
        updateReportFileRecord(sampleOid, currentDate, fileName, fileType);
        return true;
    }



    /**
     * 从文件名中提取 sampleId
     *
     * @param fileName 文件名
     * @param extensionLen 文件扩展名长度
     * @return sampleId
     */
    private String extractSampleIdFromFileName(String fileName, int extensionLen) {

        // 移除后缀
        String sampleId = fileName.substring(0, fileName.length() - extensionLen);

        if (sampleId.isEmpty()) {
            throw new BusinessException("sampleId 不能为空");
        }

        log.info("从文件名 {} 中提取到 sampleId: {}", fileName, sampleId);
        return sampleId;
    }


    /**
     * 获取文件扩展名（小写）
     *
     * @param fileName 文件名
     * @return 文件扩展名（不含点号）
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }



//    @Transactional
//    @Override
//    public boolean receivePdfReport(MultipartFile file) {
//        // 1. 验证文件
//        if (file.isEmpty() || file.getOriginalFilename() == null) {
//            log.warn("文件为空/文件名为空");
//            throw new BusinessException("文件为空/文件名为空");
//        }
//        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
//            log.info(file.getOriginalFilename());
//            log.warn("文件格式错误，须为PDF");
//            throw new BusinessException("文件格式错误，须为PDF");
//        }
//
//        // 2. 解析文件名 - 提取sampleId
//        String fileName = file.getOriginalFilename();
//        String sampleId = extractSampleIdFromFileName(fileName,4);
//
//        // 3. 根据sampleId查询数据库
//        SampleInfo sampleInfo = findSampleInfoBySampleId(sampleId);
//        long sampleOid = sampleInfo.getOid();
//
//        // 4. 创建日期目录
//        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))+"/pdf";
//        String dirPath = Paths.get(fileStorageRoot, currentDate).toString();
//        File dir = new File(dirPath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        // 5. 使用原始文件名保存
//        String filePath = Paths.get(dirPath, fileName).toString();
//
//        // 6. 保存文件
//        try {
//            file.transferTo(new File(filePath));
//        } catch (IOException e) {
//            log.error("文件保存失败", e.getMessage());
//            throw new BusinessException("文件保存失败");
//        }
//
//        // 7. 更新报告文件表
//        int fileType = 1;
//        updateReportFileRecord(sampleOid, currentDate, fileName, fileType);
//        return true;
//    }
//
//
//
//
//    @Transactional
//    @Override
//    public boolean receiveWordReport(MultipartFile file) {
//        // 1. 验证文件
//        String fileName = file.getOriginalFilename();
//        if (file.isEmpty() || fileName == null) {
//            log.warn("文件为空/文件名为空");
//            throw new BusinessException("文件为空/文件名为空");
//        }
//        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
//        if (!"docx".equals(extension) && !"doc".equals(extension)) {
//            log.warn("文件格式错误，须为WORD");
//            throw new BusinessException("文件格式错误，须为WORD");
//        }
//
//        // 2. 解析文件名 - 提取sampleId
//        int len = extension.length()+1;
//        String sampleId = extractSampleIdFromFileName(fileName,len);
//
//        // 3. 根据sampleId查询数据库
//        SampleInfo sampleInfo = findSampleInfoBySampleId(sampleId);
//        long sampleOid = sampleInfo.getOid();
//
//        // 4. 创建日期目录
//        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))+"/word";
//        String dirPath = Paths.get(fileStorageRoot, currentDate).toString();
//        File dir = new File(dirPath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        // 5. 使用原始文件名保存（修改这里）
//        String filePath = Paths.get(dirPath, fileName).toString();
//
//        // 6. 保存文件
//        try {
//            file.transferTo(new File(filePath));
//        } catch (IOException e) {
//            log.error("文件保存失败", e.getMessage());
//            throw new BusinessException("文件保存失败");
//        }
//
//        // 7. 更新报告文件表
//        int fileType = 0;
//        updateReportFileRecord(sampleOid, currentDate, fileName, fileType);
//        return true;
//    }


//    /**
//     * 从文件名中提取sampleId
//     * 支持格式: sampleId.pdf
//     *
//     * @param fileName 文件名
//     * @return sampleId
//     * @throws BusinessException 如果文件名格式不正确
//     */
//    private String extractSampleIdFromFileName(String fileName, int extensionLen) {
//
//        // 移除后缀
//        String sampleId = fileName.substring(0, fileName.length() - extensionLen);
//
//        if (sampleId.isEmpty()) {
//            throw new BusinessException("sampleId不能为空");
//        }
//
//        log.info("从文件名 {} 中提取到sampleId: {}", fileName, sampleId);
//        return sampleId;
//    }

    /**
     * 根据sampleId查询SampleInfo
     *
     * @param sampleId 样本ID
     * @return SampleInfo
     * @throws BusinessException 如果未查询到匹配记录
     */
    private SampleInfo findSampleInfoBySampleId(String sampleId) {
        // 根据sampleId查询SampleInfo
        SampleInfo sampleInfo = sampleInfoMapper.selectOne(
                new QueryWrapper<SampleInfo>()
                        .eq("sample_id", sampleId)
        );

        if (sampleInfo == null) {
            log.warn("未查询到匹配的样本记录 - sampleId: {}", sampleId);
            throw new BusinessException("未查询到匹配的样本记录，sampleId: " + sampleId);
        }

        return sampleInfo;
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
            reportFile.setOid(existingFile.getOid());
            testReportFileInfoMapper.updateById(reportFile);
        } else {
            testReportFileInfoMapper.insert(reportFile);
        }
    }



}

