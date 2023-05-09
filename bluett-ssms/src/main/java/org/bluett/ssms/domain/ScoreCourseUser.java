package org.bluett.ssms.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluett.common.core.domain.BaseEntity;


/**
 * 分数课程用户关联对象 ssms_score_course_user
 *
 * @author bluett
 * @date 2023-05-09
 */
@Data
@TableName("ssms_score_course_user")
public class ScoreCourseUser {

    private static final long serialVersionUID=1L;

    /**
     * 分数ID
     */
    @TableId(value = "score_id", type = IdType.INPUT)
    private Long scoreId;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 学生编号
     */
    private String userName;

}
