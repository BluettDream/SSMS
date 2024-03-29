package org.bluett.ssms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.bluett.common.core.mapper.BaseMapperPlus;
import org.bluett.ssms.domain.Score;
import org.bluett.ssms.domain.vo.ScoreDashBoardVo;
import org.bluett.ssms.domain.vo.ScoreVo;

import java.util.List;
import java.util.Map;

/**
 * 分数信息Mapper接口
 *
 * @author bluett
 * @date 2023-05-08
 */
@Mapper
public interface ScoreMapper extends BaseMapperPlus<ScoreMapper, Score, ScoreVo> {

    ScoreVo selectScoreVoById(@Param("id") Long scoreId);

    Page<ScoreVo> selectScoreVoPage(@Param("page") Page<ScoreVo> page, @Param(Constants.WRAPPER) Wrapper<ScoreVo> queryWrapper);

    List<ScoreVo> selectScoreVoList(@Param(Constants.WRAPPER) Wrapper<ScoreVo> queryWrapper);

    ScoreDashBoardVo selectScorePanelInfo(@Param(Constants.WRAPPER) Wrapper<ScoreVo> queryWrapper);

    @MapKey("courseName")
    List<Map<String,Object>> selectScoreGroupByCourse(@Param(Constants.WRAPPER) Wrapper<ScoreVo> queryWrapper);
}
