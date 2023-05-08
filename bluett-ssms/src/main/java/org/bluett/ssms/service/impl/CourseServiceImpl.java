package org.bluett.ssms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.bluett.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.bluett.ssms.domain.bo.CourseBo;
import org.bluett.ssms.domain.vo.CourseVo;
import org.bluett.ssms.domain.Course;
import org.bluett.ssms.mapper.CourseMapper;
import org.bluett.ssms.service.ICourseService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程信息Service业务层处理
 *
 * @author bluett
 * @date 2023-05-08
 */
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements ICourseService {

    private final CourseMapper baseMapper;

    /**
     * 查询课程信息
     */
    @Override
    public CourseVo queryById(Long courseId){
        return baseMapper.selectVoById(courseId);
    }

    /**
     * 查询课程信息列表
     */
    @Override
    public TableDataInfo<CourseVo> queryPageList(CourseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Course> lqw = buildQueryWrapper(bo);
        Page<CourseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程信息列表
     */
    @Override
    public List<CourseVo> queryList(CourseBo bo) {
        LambdaQueryWrapper<Course> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Course> buildQueryWrapper(CourseBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Course> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCourseName()), Course::getCourseName, bo.getCourseName());
        lqw.eq(bo.getCredit() != null, Course::getCredit, bo.getCredit());
        lqw.eq(bo.getStartTime() != null, Course::getStartTime, bo.getStartTime());
        lqw.eq(bo.getFinishTime() != null, Course::getFinishTime, bo.getFinishTime());
        return lqw;
    }

    /**
     * 新增课程信息
     */
    @Override
    public Boolean insertByBo(CourseBo bo) {
        Course add = BeanUtil.toBean(bo, Course.class);
        validEntityBeforeSave(add);
        return baseMapper.insert(add) > 0;
    }

    /**
     * 修改课程信息
     */
    @Override
    public Boolean updateByBo(CourseBo bo) {
        Course update = BeanUtil.toBean(bo, Course.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Course entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
