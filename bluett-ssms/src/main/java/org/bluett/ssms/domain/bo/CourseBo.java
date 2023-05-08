package org.bluett.ssms.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.bluett.common.core.domain.BaseEntity;
import org.bluett.common.core.validate.AddGroup;
import org.bluett.common.core.validate.EditGroup;

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
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String courseName;

    /**
     * 学分
     */
    @NotNull(message = "学分不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long credit;

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTime;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date finishTime;


}
