package org.xinrui.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xinrui.dto.ApiResponse;
import org.xinrui.entity.SampleInfo;
import org.xinrui.service.LisSampleService;
import org.xinrui.service.SampleService;

@Slf4j
@RestController
@RequestMapping("/his/V3/lis/sample")
@Api(tags = "样本表接口")
@Validated
public class LisSampleController {

    @Autowired
    private LisSampleService lisSampleService;

    @ApiOperation("新增样本信息")
    @PostMapping("/add")
    public ApiResponse<SampleInfo> addSample(@RequestBody @Validated SampleInfo sampleInfo) {
        return ApiResponse.success(lisSampleService.save(sampleInfo) ? sampleInfo : null);
    }

    @ApiOperation("修改样本信息")
    @PutMapping("/update")
    public ApiResponse<Boolean> updateSample(@RequestBody @Validated SampleInfo sampleInfo) {
        return ApiResponse.success(lisSampleService.updateById(sampleInfo));
    }

    @ApiOperation("删除样本信息（物理删除）")
    @DeleteMapping("/delete/{oid}")
    public ApiResponse<Boolean> deleteSample(@PathVariable Long oid) {
        return ApiResponse.success(lisSampleService.removeById(oid));
    }

    @ApiOperation("根据ID查询样本信息")
    @GetMapping("/get/{oid}")
    public ApiResponse<SampleInfo> getSampleById(@PathVariable Long oid) {
        return ApiResponse.success(lisSampleService.getById(oid));
    }

    @ApiOperation("分页查询样本信息")
    @PostMapping("/list")
    public ApiResponse<Page<SampleInfo>> listSample(@RequestBody Page<SampleInfo> page) {
        return ApiResponse.success(lisSampleService.page(page));
    }
}
