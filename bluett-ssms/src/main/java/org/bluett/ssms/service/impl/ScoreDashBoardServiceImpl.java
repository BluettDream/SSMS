package org.bluett.ssms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreDashBoardVo;
import org.bluett.ssms.domain.vo.ScoreVo;
import org.bluett.ssms.service.IScoreService;
import org.bluett.ssms.service.ScoreDashBoardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * name: MengHao Tian
 * date: 2023/5/13 16:20
 */
@RequiredArgsConstructor
@Service
public class ScoreDashBoardServiceImpl implements ScoreDashBoardService {
    private final IScoreService scoreService;
    @Override
    public ScoreDashBoardVo getScoreDashBoardInfo(ScoreBo bo) {
        List<ScoreVo> scoreVoList = scoreService.queryList(bo);
        ScoreDashBoardVo vo = new ScoreDashBoardVo();
        vo.setScoreVoList(scoreVoList);
        vo.setAllCourse(scoreVoList.size());
        vo.setMaxScore(scoreVoList.stream().mapToDouble(ScoreVo::getScore).max().getAsDouble());
        vo.setAvgScore(scoreVoList.stream().mapToDouble(ScoreVo::getScore).average().getAsDouble());
        vo.setMinScore(scoreVoList.stream().mapToDouble(ScoreVo::getScore).min().getAsDouble());
        return vo;
    }

    private QueryWrapper<ScoreBo> buildWrapper(ScoreBo bo) {
        Map<String, Object> params = bo.getParams();
        return Wrappers.query();
    }
}
