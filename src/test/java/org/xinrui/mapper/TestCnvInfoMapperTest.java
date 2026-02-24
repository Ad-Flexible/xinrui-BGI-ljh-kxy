package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xinrui.entity.TestCnvInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestCnvInfoMapper 的集成测试
 */
@Slf4j
@SpringBootTest // 1. 启动 Spring Boot 上下文，自动注入 Mapper
public class TestCnvInfoMapperTest {

    @Autowired
    private TestCnvInfoMapper testCnvInfoMapper; // 2. 注入我们要测试的 Mapper

    /**
     * 测试批量插入功能
     */
    @Test
    public void testInsertBatch_Success() {
        // ================= 准备数据 =================
        List<TestCnvInfo> list = new ArrayList<>();

        // 构造第一条数据
        TestCnvInfo info1 = new TestCnvInfo();
        info1.setResultOid(1L); // 假设关联的结果ID是1001
        info1.setCnvCategory("D");
        info1.setCytoband("p11.2");
        info1.setChrNum(1);
        info1.setCnvType("DUP");
        info1.setCnvSize(new BigDecimal("50000.50"));
        info1.setSite("chr1:10000-60000");
        info1.setDelDupDesc("Test Disease 1");
        info1.setDiseaseName("Test Name 1");
        info1.setDiseaseDetail("Detail 1");
        info1.setDiseaseDescription("Desc 1");
        info1.setUpdatedBy(1L);
        info1.setUpdatedOn(LocalDateTime.now());
        list.add(info1);

        // 构造第二条数据
        TestCnvInfo info2 = new TestCnvInfo();
        info2.setResultOid(1L);
        info2.setCnvCategory("O"); // 类别不同
        info2.setCytoband("q22.3");
        info2.setChrNum(2);
        info2.setCnvType("DEL");
        info2.setCnvSize(new BigDecimal("10000"));
        info2.setSite("chr2:5000-15000");
        info2.setDelDupDesc("Test Disease 2");
        info2.setDiseaseName("Test Name 2");
        info2.setDiseaseDetail("Detail 2");
        info2.setDiseaseDescription("Desc 2");
        info2.setUpdatedBy(1L);
        info2.setUpdatedOn(LocalDateTime.now());
        list.add(info2);

        // ================= 执行操作 =================
        // 调用 insertBatch 方法
        int rows = testCnvInfoMapper.insertBatch(list);

        // ================= 验证结果 =================
        // 1. 验证返回值：我们插入了2条，期望返回2
        assertEquals(2, rows, "插入行数应该为2");

        // 2. (可选) 验证数据库：为了严谨，可以查出来看看是否真的插进去了
        // 这里利用 MyBatis-Plus 提供的 selectList 查询刚才插入的数据
        // 注意：这需要你的实体类中有主键 ID 且能查到，或者根据业务字段查询
        // 如果没有主键回填，这一步可以根据业务逻辑灵活调整
        List<TestCnvInfo> insertedList = testCnvInfoMapper.selectList(
                Wrappers.<TestCnvInfo>lambdaQuery()
                        .eq(TestCnvInfo::getResultOid, 1L)
        );

        // 简单验证数量是否正确
        assertTrue(insertedList.size() >= 2, "数据库中应该至少存在刚才插入的2条数据");

        // 清理数据 (可选，防止污染测试库)
        testCnvInfoMapper.delete(Wrappers.<TestCnvInfo>lambdaQuery().eq(TestCnvInfo::getResultOid, 1L));
    }


}
