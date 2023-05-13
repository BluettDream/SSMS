package org.bluett.ssms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.bluett.common.core.mapper.BaseMapperPlus;
import org.bluett.ssms.domain.Course;
import org.bluett.ssms.domain.vo.CourseVo;

import java.util.List;

/**
 * 课程信息Mapper接口
 *
 * @author bluett
 * @date 2023-05-08
 */
@Mapper
public interface CourseMapper extends BaseMapperPlus<CourseMapper, Course, CourseVo> {

    /**
     * 根据条件分页查询课程信息列表
     **/
    Page<CourseVo> selectCourseVoPage(@Param("page") Page<CourseVo> page, @Param(Constants.WRAPPER) Wrapper<CourseVo> queryWrapper);

    /**
     * 根据条件查询课程信息列表
     *
     * @param queryWrapper 查询条件
     * @return 课程信息集合
     **/
    List<CourseVo> selectCourseVoList(@Param(Constants.WRAPPER) Wrapper<CourseVo> queryWrapper);

    /**
     * 根据课程ID查询课程信息
     *
     * @param courseId 课程ID
     * @return 课程信息
     **/
    CourseVo selectCourseVoById(@Param("id") Long courseId);

    List<Long> selectCourseIdsByUserName(@Param("userName") String userName);
}
