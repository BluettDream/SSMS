package org.bluett.ssms.service.impl;

import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
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
        return baseMapper.selectCourseVoById(courseId);
    }

    /**
     * 查询课程信息列表
     */
    @Override
    public TableDataInfo<CourseVo> queryPageList(CourseBo bo, PageQuery pageQuery) {
        QueryWrapper<CourseVo> lqw = buildQueryWrapper(bo);
        Page<CourseVo> result = baseMapper.selectCourseVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程信息列表
     */
    @Override
    public List<CourseVo> queryList(CourseBo bo) {
        QueryWrapper<CourseVo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectCourseVoList(lqw);
    }

    private QueryWrapper<CourseVo> buildQueryWrapper(CourseBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<CourseVo> qw = Wrappers.query();
        qw.eq("c.del_flag", "0");
        qw.like(StringUtils.isNotBlank(bo.getUserName()), "u.user_name", bo.getUserName());
        qw.like(StringUtils.isNotBlank(bo.getCourseName()), "c.course_name", bo.getCourseName());
        qw.eq(bo.getCredit() != null, "c.credit", bo.getCredit());
        qw.eq(bo.getStartTime() != null, "c.start_time", bo.getStartTime());
        qw.eq(bo.getFinishTime() != null, "c.finish_time", bo.getFinishTime());
        qw.like(StringUtils.isNotBlank(bo.getNickName()), "u.nick_name", bo.getNickName());
        return qw;
    }

    /**
     * 新增课程信息
     */
    @Override
    public Boolean insertByBo(CourseBo bo) {
        Course add = BeanUtil.toBean(bo, Course.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCourseId(add.getCourseId());
        }
        return flag;
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
