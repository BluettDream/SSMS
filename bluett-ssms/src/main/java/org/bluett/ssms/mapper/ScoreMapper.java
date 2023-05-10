package org.bluett.ssms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.bluett.common.annotation.DataColumn;
import org.bluett.common.annotation.DataPermission;
import org.bluett.ssms.domain.Score;
import org.bluett.ssms.domain.vo.ScoreVo;
import org.bluett.common.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 分数信息Mapper接口
 *
 * @author bluett
 * @date 2023-05-08
 */
public interface ScoreMapper extends BaseMapperPlus<ScoreMapper, Score, ScoreVo> {

    ScoreVo selectScoreVoById(@Param("id") Long scoreId);

    Page<ScoreVo> selectScoreVoPage(@Param("page") Page<ScoreVo> page, @Param(Constants.WRAPPER) Wrapper<ScoreVo> queryWrapper);

    List<ScoreVo> selectScoreVoList(@Param(Constants.WRAPPER) Wrapper<ScoreVo> queryWrapper);
}
