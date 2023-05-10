package org.bluett.ssms.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluett.common.core.domain.BaseEntity;
import org.bluett.common.core.validate.AddGroup;
import org.bluett.common.core.validate.EditGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;


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
     * 分数ID
     */
    @NotNull(message = "分数ID不能为空", groups = { EditGroup.class })
    private Long scoreId;

    /**
     * 学生编号
     */
    @NotNull(message = "学生编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 学生姓名
     */
    private String nickName;

    /**
     * 课程编号
     */
    @NotNull(message = "课程编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师姓名
     */
    private String teacherNickName;

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 学分
     */
    private Double credit;

    /**
     * 分数
     */
    @NotNull(message = "分数不能为空", groups = { AddGroup.class, EditGroup.class })
    @Max(value = 100, message = "分数不能大于100", groups = { AddGroup.class, EditGroup.class })
    @Min(value = 0, message = "分数不能小于0", groups = { AddGroup.class, EditGroup.class })
    private Double score;

}
