package org.bluett.ssms.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.bluett.common.core.domain.BaseEntity;

/**
 * 课程信息对象 ssms_course
 *
 * @author bluett
 * @date 2023-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ssms_course")
public class Course extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 课程ID
     */
    @TableId(value = "course_id")
    private Long courseId;
    /**
     * 用户名称(教师编号)
     */
    private String userName;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 学分
     */
    private Double credit;
    /**
     * 开始日期
     */
    private Date startTime;
    /**
     * 结束日期
     */
    private Date finishTime;
    /**
     * 删除标志(0代表存在,2代表删除)
     */
    @TableLogic
    private String delFlag;

}
