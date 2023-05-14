package org.bluett.ssms.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

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
    private Double scoreMax;

    /**
     * 最低分数
     */
    private Double scoreMin;

    /**
     * 平均分数
     */
    private Double scoreAvg;

    /**
     * 全部课程
     */
    private Long courseCount;

    /**
     * 课程分数详情
     */
    private Map<String,Double> scoreData;
}
