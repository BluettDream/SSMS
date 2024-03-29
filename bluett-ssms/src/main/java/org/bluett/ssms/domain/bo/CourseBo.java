package org.bluett.ssms.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluett.common.core.domain.BaseEntity;
import org.bluett.common.core.validate.AddGroup;
import org.bluett.common.core.validate.EditGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 课程信息业务对象 ssms_course
 *
 * @author bluett
 * @date 2023-05-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CourseBo extends BaseEntity {

    /**
     * 课程ID
     */
    @NotNull(message = "课程ID不能为空", groups = { EditGroup.class })
    private Long courseId;

    /**
     * 用户名称(教师编号)
     */
    @NotBlank(message = "用户名称(教师编号)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String courseName;

    /**
     * 学分
     */
    @NotNull(message = "学分不能为空", groups = { AddGroup.class, EditGroup.class })
    private Double credit;

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期不能为空", groups = { AddGroup.class, EditGroup.class })
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期不能为空", groups = { AddGroup.class, EditGroup.class })
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 教师姓名
     */
    private String nickName;
}
