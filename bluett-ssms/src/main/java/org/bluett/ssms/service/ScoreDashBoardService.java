package org.bluett.ssms.service;

import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreDashBoardVo;

public interface ScoreDashBoardService {
    ScoreDashBoardVo getScoreDashBoardInfo(ScoreBo bo);
}
