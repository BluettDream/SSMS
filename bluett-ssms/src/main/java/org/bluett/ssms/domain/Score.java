package org.bluett.ssms.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluett.common.core.domain.BaseEntity;


/**
 * 分数信息对象 ssms_score
 *
 * @author bluett
 * @date 2023-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ssms_score")
public class Score extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 分数ID
     */
    @TableId(value = "score_id")
    private Long scoreId;
    /**
     * 分数
     */
    private Long score;
    /**
     * 删除标志(0代表存在,2代表删除)
     */
    @TableLogic
    private String delFlag;

}
