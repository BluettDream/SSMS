package org.bluett.ssms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreVo;
import org.bluett.ssms.domain.Score;
import org.bluett.ssms.mapper.ScoreMapper;
import org.bluett.ssms.service.IScoreService;

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

    /**
     * 查询分数信息
     */
    @Override
    public ScoreVo queryById(Long scoreId){
        return baseMapper.selectVoById(scoreId);
    }

    /**
     * 查询分数信息列表
     */
    @Override
    public TableDataInfo<ScoreVo> queryPageList(ScoreBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Score> lqw = buildQueryWrapper(bo);
        Page<ScoreVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询分数信息列表
     */
    @Override
    public List<ScoreVo> queryList(ScoreBo bo) {
        LambdaQueryWrapper<Score> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Score> buildQueryWrapper(ScoreBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Score> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getScore() != null, Score::getScore, bo.getScore());
        return lqw;
    }

    /**
     * 新增分数信息
     */
    @Override
    public Boolean insertByBo(ScoreBo bo) {
        Score add = BeanUtil.toBean(bo, Score.class);
        validEntityBeforeSave(add);
        return baseMapper.insert(add) > 0;
    }

    /**
     * 修改分数信息
     */
    @Override
    public Boolean updateByBo(ScoreBo bo) {
        Score update = BeanUtil.toBean(bo, Score.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
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
