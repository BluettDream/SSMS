package org.bluett.ssms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.bluett.ssms.domain.ScoreCourseUser;
import org.bluett.ssms.domain.vo.ScoreCourseUserVo;
import org.bluett.common.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 分数课程用户关联Mapper接口
 *
 * @author bluett
 * @date 2023-05-09
 */
@Mapper
public interface ScoreCourseUserMapper extends BaseMapperPlus<ScoreCourseUserMapper, ScoreCourseUser, ScoreCourseUserVo> {

    List<Long> selectScoreIdsByCourseIds(@Param("courseIds") List<Long> courseIdList);
}
