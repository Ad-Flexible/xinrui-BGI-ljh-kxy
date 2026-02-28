package org.xinrui.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xinrui.dto.ApiResponse;
import org.xinrui.entity.ExaminationInfo;
import org.xinrui.service.LisExaminationService;

@Slf4j
@RestController
@RequestMapping("/his/V3/lis/examination")
@Api(tags = "检查表接口")
@Validated
public class LisExaminationController {
    @Autowired
    private LisExaminationService lisExaminationService;

    @ApiOperation("新增检查信息")
    @PostMapping("/add")
    public ApiResponse<ExaminationInfo> addExamination(@RequestBody @Validated ExaminationInfo examinationInfo) {
        return ApiResponse.success(lisExaminationService.save(examinationInfo) ? examinationInfo : null);
    }

    @ApiOperation("修改检查信息")
    @PutMapping("/update")
    public ApiResponse<Boolean> updateExamination(@RequestBody @Validated ExaminationInfo examinationInfo) {
        return ApiResponse.success(lisExaminationService.updateById(examinationInfo));
    }

    @ApiOperation("删除检查信息（物理删除）")
    @DeleteMapping("/delete/{oid}")
    public ApiResponse<Boolean> deleteExamination(@PathVariable Long oid) {
        return ApiResponse.success(lisExaminationService.removeById(oid));
    }

    @ApiOperation("根据ID查询检查信息")
    @GetMapping("/get/{oid}")
    public ApiResponse<ExaminationInfo> getExaminationById(@PathVariable Long oid) {
        return ApiResponse.success(lisExaminationService.getById(oid));
    }

    @ApiOperation("分页查询检查信息")
    @PostMapping("/list")
    public ApiResponse<Page<ExaminationInfo>> listExamination(@RequestBody Page<ExaminationInfo> page) {
        return ApiResponse.success(lisExaminationService.page(page));
    }
}
