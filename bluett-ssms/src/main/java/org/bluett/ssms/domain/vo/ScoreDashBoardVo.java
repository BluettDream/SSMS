package org.bluett.ssms.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * name: MengHao Tian
 * date: 2023/5/13 15:56
 */
@Data
@NoArgsConstructor
public class ScoreDashBoardVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 最高分数
     */
    private Double maxScore;

    /**
     * 最低分数
     */
    private Double minScore;

    /**
     * 平均分数
     */
    private Double avgScore;

    /**
     * 全部课程
     */
    private Integer allCourse;

    /**
     * 分数详情
     */
    private List<ScoreVo> scoreVoList;
}
