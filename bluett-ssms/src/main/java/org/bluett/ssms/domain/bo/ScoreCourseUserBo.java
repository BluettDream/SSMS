package org.bluett.ssms.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluett.common.core.domain.BaseEntity;
import org.bluett.common.core.validate.AddGroup;
import org.bluett.common.core.validate.EditGroup;

import javax.validation.constraints.*;


/**
 * 分数课程用户关联业务对象 ssms_score_course_user
 *
 * @author bluett
 * @date 2023-05-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ScoreCourseUserBo extends BaseEntity {

    /**
     * 分数ID
     */
    @NotNull(message = "分数ID不能为空", groups = { EditGroup.class })
    private Long scoreId;

    /**
     * 课程ID
     */
    @NotNull(message = "课程ID不能为空", groups = { EditGroup.class })
    private Long courseId;

    /**
     * 学生编号
     */
    @NotBlank(message = "学生编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;


}
