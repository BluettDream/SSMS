package org.bluett.ssms.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 分数信息业务对象 ssms_score
 *
 * @author bluett
 * @date 2023-05-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ScoreBo extends BaseEntity {

    /**
     * 分数
     */
    @NotNull(message = "分数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long score;


}
