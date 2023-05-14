package org.bluett.ssms.service;

import org.bluett.common.core.domain.PageQuery;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreDashBoardVo;
import org.bluett.ssms.domain.vo.ScoreVo;

import java.util.Collection;
import java.util.List;

/**
 * 分数信息Service接口
 *
 * @author bluett
 * @date 2023-05-08
 */
public interface IScoreService {

    /**
     * 查询分数信息
     */
    ScoreVo queryById(Long scoreId);

    /**
     * 查询分数信息列表
     */
    TableDataInfo<ScoreVo> queryPageList(ScoreBo bo, PageQuery pageQuery);

    /**
     * 查询分数信息列表
     */
    List<ScoreVo> queryList(ScoreBo bo);

    /**
     * 新增分数信息
     */
    Boolean insertByBo(ScoreBo bo);

    /**
     * 修改分数信息
     */
    Boolean updateByBo(ScoreBo bo);

    /**
     * 校验并批量删除分数信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 检查分数是否唯一
     */
    Boolean checkScoreUnique(ScoreBo bo);

    ScoreDashBoardVo queryDashBoardInfo(ScoreBo bo);
}
