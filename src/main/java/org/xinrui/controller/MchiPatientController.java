package org.xinrui.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xinrui.dto.ApiResponse;
import org.xinrui.entity.PatientInfo;
import org.xinrui.service.MchiPatientService;

@Slf4j
@RestController
@RequestMapping("/his/V3/lis/patient")
@Api(tags = "患者表接口")
@Validated
public class MchiPatientController {

    @Autowired
    private MchiPatientService mchiPatientService;

    @ApiOperation("新增患者信息")
    @PostMapping("/add")
    public ApiResponse<PatientInfo> addPatient(@RequestBody @Validated PatientInfo patientInfo) {
        return ApiResponse.success(mchiPatientService.save(patientInfo) ? patientInfo : null);
    }

    @ApiOperation("修改患者信息")
    @PutMapping("/update")
    public ApiResponse<Boolean> updatePatient(@RequestBody @Validated PatientInfo patientInfo) {
        return ApiResponse.success(mchiPatientService.updateById(patientInfo));
    }

    @ApiOperation("删除患者信息（物理删除）")
    @DeleteMapping("/delete/{oid}")
    public ApiResponse<Boolean> deletePatient(@PathVariable Long oid) {
        return ApiResponse.success(mchiPatientService.removeById(oid));
    }

    @ApiOperation("根据ID查询患者信息")
    @GetMapping("/get/{oid}")
    public ApiResponse<PatientInfo> getPatientById(@PathVariable Long oid) {
        return ApiResponse.success(mchiPatientService.getById(oid));
    }

    @ApiOperation("分页查询患者信息")
    @PostMapping("/list")
    public ApiResponse<Page<PatientInfo>> listPatient(@RequestBody Page<PatientInfo> page) {
        return ApiResponse.success(mchiPatientService.page(page));
    }
}
