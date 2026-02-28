package org.xinrui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xinrui.entity.*;
import org.xinrui.mapper.*;
import org.xinrui.service.LisSampleService;
import org.xinrui.service.SampleService;

import javax.annotation.Resource;

@Service
public class LisSampleServiceImpl extends ServiceImpl<SampleInfoMapper, SampleInfo> implements LisSampleService {

    @Resource
    private ExaminationInfoMapper examinationInfoMapper;

    @Resource
    private LaneQcInfoMapper laneQcInfoMapper;

    @Resource
    private SampleQcInfoMapper sampleQcInfoMapper;

    @Resource
    private TestReportFileInfoMapper testReportFileInfoMapper;

    @Resource
    private TestResultInfoMapper testResultInfoMapper;

    @Override
    @Transactional // 确保级联删除在同一个事务中
    public boolean removeWithCascade(Long oid) {
        // 删除所有关联子表数据
        examinationInfoMapper.delete(new QueryWrapper<ExaminationInfo>().eq("sample_oid", oid));
        laneQcInfoMapper.delete(new QueryWrapper<LaneQcInfo>().eq("sample_oid", oid));
        sampleQcInfoMapper.delete(new QueryWrapper<SampleQcInfo>().eq("sample_oid", oid));
        testReportFileInfoMapper.delete(new QueryWrapper<TestReportFileInfo>().eq("sample_oid", oid));
        testResultInfoMapper.delete(new QueryWrapper<TestResultInfo>().eq("sample_oid", oid));

        // 删除父表数据
        return super.removeById(oid);
    }
}
