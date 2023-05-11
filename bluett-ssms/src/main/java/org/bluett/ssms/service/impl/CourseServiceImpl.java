package org.bluett.ssms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.bluett.common.constant.UserConstants;
import org.bluett.common.core.domain.PageQuery;
import org.bluett.common.core.domain.entity.SysUser;
import org.bluett.common.core.domain.model.LoginUser;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.common.exception.ServiceException;
import org.bluett.common.helper.LoginHelper;
import org.bluett.common.utils.StringUtils;
import org.bluett.ssms.domain.Course;
import org.bluett.ssms.domain.bo.CourseBo;
import org.bluett.ssms.domain.vo.CourseVo;
import org.bluett.ssms.mapper.CourseMapper;
import org.bluett.ssms.service.ICourseService;
import org.bluett.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    private final SysUserMapper userMapper;

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
        qw.eq(params.get("userId") != null, "u.user_id", params.get("userId")); // 数据权限校验
        qw.eq(bo.getCredit() != null, "c.credit", bo.getCredit());
        qw.ge(bo.getStartTime() != null, "c.start_time", bo.getStartTime());
        qw.le(bo.getFinishTime() != null, "c.finish_time", bo.getFinishTime());
        qw.like(StringUtils.isNotBlank(bo.getNickName()), "u.nick_name", bo.getNickName());
        qw.like(StringUtils.isNotBlank(bo.getUserName()), "u.user_name", bo.getUserName());
        qw.like(StringUtils.isNotBlank(bo.getCourseName()), "c.course_name", bo.getCourseName());
        return qw;
    }

    /**
     * 新增课程信息
     */
    @Override
    public Boolean insertByBo(CourseBo bo) {
        validEntityBeforeSave(bo);
        if(!checkCourseUnique(bo)){
            throw new ServiceException("课程信息已存在");
        }
        Course add = BeanUtil.toBean(bo, Course.class);
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
        validEntityBeforeSave(bo);
        Course update = BeanUtil.toBean(bo, Course.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CourseBo bo){
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser teacher = userMapper.selectUserByUserName(bo.getUserName());
        if(ObjectUtil.isNull(teacher)) {
            throw new ServiceException("教师编号不存在");
        }
        if(loginUser.getRoles().stream().noneMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.ADMIN_ID))) { //登录用户不是管理员
            if(loginUser.getRoles().stream().noneMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.TEACHER_ID))) { //登录用户也不是教师
                throw new ServiceException("您没有权限编辑该课程");
            }else if(!loginUser.getUsername().equals(teacher.getUserName())) { //登录用户是教师，但不是该课程的教师
                throw new ServiceException("您没有权限编辑其他教师的课程");
            }
        }
    }

    /**
     * 批量删除课程信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            LoginUser loginUser = LoginHelper.getLoginUser();
            if(loginUser.getRoles().stream().noneMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.ADMIN_ID))){//不是管理员
                List<Long> idList = baseMapper.selectCourseIdsByUserName(loginUser.getUsername());
                // 要删除的所有课程id中，有一个不是该用户的课程，则抛出异常
                if(!idList.containsAll(ids)) throw new ServiceException("删除异常,未找到课程信息");
            }
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<Long> queryCourseIdsByUserName(String userName) {
        return baseMapper.selectCourseIdsByUserName(userName);
    }

    @Override
    public Boolean checkCourseUnique(CourseBo bo) {
        boolean exists = baseMapper.exists(new QueryWrapper<Course>()
            .eq(StringUtils.isNotBlank(bo.getUserName()), "user_name", bo.getUserName())
            .eq(StringUtils.isNotBlank(bo.getCourseName()), "course_name", bo.getCourseName()));
        return !exists;
    }
}
