package org.bluett.ssms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.bluett.common.utils.StringUtils;
import org.bluett.ssms.domain.ScoreCourseUser;
import org.bluett.ssms.mapper.ScoreCourseUserMapper;
import org.springframework.stereotype.Service;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreVo;
import org.bluett.ssms.domain.Score;
import org.bluett.ssms.mapper.ScoreMapper;
import org.bluett.ssms.service.IScoreService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

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
        Score addScore = BeanUtil.toBean(bo, Score.class);
        validEntityBeforeSave(addScore);
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
    public Boolean updateByBo(ScoreBo bo) {
        Score updScore = BeanUtil.toBean(bo, Score.class);
        validEntityBeforeSave(updScore);
        ScoreCourseUser scoreCourseUser = BeanUtil.toBean(bo, ScoreCourseUser.class);
        return baseMapper.updateById(updScore) > 0 && scoreCourseUserMapper.updateById(scoreCourseUser) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Score entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除分数信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
