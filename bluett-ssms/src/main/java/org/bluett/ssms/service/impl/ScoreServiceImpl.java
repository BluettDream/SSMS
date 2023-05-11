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
import org.bluett.ssms.domain.Score;
import org.bluett.ssms.domain.ScoreCourseUser;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.CourseVo;
import org.bluett.ssms.domain.vo.ScoreCourseUserVo;
import org.bluett.ssms.domain.vo.ScoreVo;
import org.bluett.ssms.mapper.CourseMapper;
import org.bluett.ssms.mapper.ScoreCourseUserMapper;
import org.bluett.ssms.mapper.ScoreMapper;
import org.bluett.ssms.service.IScoreService;
import org.bluett.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 分数信息Service业务层处理
 *
 * @author bluett
 * @date 2023-05-08
 */
@RequiredArgsConstructor
@Service
public class ScoreServiceImpl implements IScoreService {

    private final ScoreMapper baseMapper;
    private final ScoreCourseUserMapper scoreCourseUserMapper;
    private final SysUserMapper userMapper;
    private final CourseMapper courseMapper;

    /**
     * 查询分数信息
     */
    @Override
    public ScoreVo queryById(Long scoreId){
        return baseMapper.selectScoreVoById(scoreId);
    }

    /**
     * 查询分数信息列表
     */
    @Override
    public TableDataInfo<ScoreVo> queryPageList(ScoreBo bo, PageQuery pageQuery) {
        QueryWrapper<ScoreVo> qw = buildQueryWrapper(bo);
        Page<ScoreVo> result = baseMapper.selectScoreVoPage(pageQuery.build(), qw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询分数信息列表
     */
    @Override
    public List<ScoreVo> queryList(ScoreBo bo) {
        QueryWrapper<ScoreVo> qw = buildQueryWrapper(bo);
        return baseMapper.selectScoreVoList(qw);
    }

    private QueryWrapper<ScoreVo> buildQueryWrapper(ScoreBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<ScoreVo> qw = Wrappers.query();
        qw.eq("s.del_flag", "0");
        qw.eq(params.get("studentId") != null,"u.user_id", params.get("studentId"));//学生数据权限
        qw.in(params.get("courseIdList") != null,"scu.course_id", (List<Long>) params.get("courseIdList"));//教师数据权限
        qw.eq(StringUtils.isNotBlank(bo.getUserName()),"u.user_name", bo.getUserName());
        qw.eq(bo.getScore() != null,"s.score", bo.getScore());
        qw.ge(bo.getStartTime() != null,"c.start_time", bo.getStartTime());
        qw.le(bo.getFinishTime() != null,"c.finish_time", bo.getFinishTime());
        qw.like(StringUtils.isNotBlank(bo.getCourseName()),"c.course_name", bo.getCourseName());
        qw.like(StringUtils.isNotBlank(bo.getNickName()),"u.nick_name", bo.getNickName());
        return qw;
    }

    /**
     * 新增分数信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(ScoreBo bo) {
        validEntityBeforeSave(bo);
        if(!checkScoreUnique(bo)) throw new ServiceException("成绩已存在");
        Score addScore = BeanUtil.toBean(bo, Score.class);
        boolean flag = baseMapper.insert(addScore) > 0;
        if(flag){
            bo.setScoreId(addScore.getScoreId());
            ScoreCourseUser addScoreCourseUser = BeanUtil.toBean(bo, ScoreCourseUser.class);
            flag = scoreCourseUserMapper.insert(addScoreCourseUser) > 0;
        }
        return flag;
    }

    /**
     * 修改分数信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(ScoreBo bo) {
        validEntityBeforeSave(bo);
        if(bo.getScoreId() == null && !checkScoreUnique(bo)){//成绩已存在并且是导入模式则添加成绩ID
            ScoreCourseUserVo scoreCourseUserVo = scoreCourseUserMapper.selectVoOne(new QueryWrapper<ScoreCourseUser>()
                .eq(StringUtils.isNotBlank(bo.getUserName()), "user_name", bo.getUserName())
                .eq(bo.getCourseId() != null, "course_id", bo.getCourseId()));
            bo.setScoreId(scoreCourseUserVo.getScoreId());
        }
        Score updScore = BeanUtil.toBean(bo, Score.class);
        ScoreCourseUser scoreCourseUser = BeanUtil.toBean(bo, ScoreCourseUser.class);
        return baseMapper.updateById(updScore) > 0 && scoreCourseUserMapper.updateById(scoreCourseUser) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ScoreBo bo){
        SysUser student = userMapper.selectUserByUserName(bo.getUserName());
        CourseVo course = courseMapper.selectCourseVoById(bo.getCourseId());
        if(ObjectUtil.isNull(student)) throw new ServiceException("学生不存在");
        if(ObjectUtil.isNull(course)) throw new ServiceException("课程不存在");
    }

    /**
     * 批量删除分数信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            LoginUser loginUser = LoginHelper.getLoginUser();
            if(loginUser.getRoles().stream().noneMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.ADMIN_ID))){//不是管理员
                // 找到教师授课的所有课程
                List<Long> courseIdList = courseMapper.selectCourseIdsByUserName(loginUser.getUsername());
                // 根据课程找到所有的成绩id
                List<Long> idList = scoreCourseUserMapper.selectScoreIdsByCourseIds(courseIdList);
                // 要删除的所有成绩id中，有一个不是该用户的成绩，抛出异常
                if(!idList.containsAll(ids)) throw new ServiceException("删除异常,未找到成绩信息");
            }
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean checkScoreUnique(ScoreBo bo) {
        boolean exists = scoreCourseUserMapper.exists(new QueryWrapper<ScoreCourseUser>()
            .eq(StringUtils.isNotBlank(bo.getUserName()), "user_name", bo.getUserName())
            .eq(bo.getCourseId() != null, "course_id", bo.getCourseId()));
        return !exists;
    }
}
