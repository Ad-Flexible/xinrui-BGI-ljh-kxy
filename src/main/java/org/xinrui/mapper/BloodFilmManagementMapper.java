package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.xinrui.entity.BloodFilmManagementInfo;

@Mapper
public interface BloodFilmManagementMapper extends BaseMapper<BloodFilmManagementInfo> {

    /**
     * 根据筛查档案id查询实验编号
     * @param screeningArchivesId 筛查档案id
     * @return 实验编号
     */
    @Select("SELECT experiment_number FROM t_mchi_blood_film_management WHERE screening_archives_id = #{screeningArchivesId}")
    String selectExperimentNumberByScreeningArchivesId(@Param("screeningArchivesId") Long screeningArchivesId);

}
