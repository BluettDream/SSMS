package org.bluett.ssms.service;

import org.bluett.ssms.domain.Course;
import org.bluett.ssms.domain.vo.CourseVo;
import org.bluett.ssms.domain.bo.CourseBo;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程信息Service接口
 *
 * @author bluett
 * @date 2023-05-08
 */
public interface ICourseService {

    /**
     * 查询课程信息
     */
    CourseVo queryById(Long courseId);

    /**
     * 查询课程信息列表
     */
    TableDataInfo<CourseVo> queryPageList(CourseBo bo, PageQuery pageQuery);

    /**
     * 查询课程信息列表
     */
    List<CourseVo> queryList(CourseBo bo);

    /**
     * 新增课程信息
     */
    Boolean insertByBo(CourseBo bo);

    /**
     * 修改课程信息
     */
    Boolean updateByBo(CourseBo bo);

    /**
     * 校验并批量删除课程信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
