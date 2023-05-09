package org.bluett.ssms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.bluett.common.annotation.ExcelDictFormat;
import org.bluett.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 分数课程用户关联视图对象 ssms_score_course_user
 *
 * @author bluett
 * @date 2023-05-09
 */
@Data
@ExcelIgnoreUnannotated
public class ScoreCourseUserVo {

    private static final long serialVersionUID = 1L;

    /**
     * 分数ID
     */
    @ExcelProperty(value = "分数ID")
    private Long scoreId;

    /**
     * 课程ID
     */
    @ExcelProperty(value = "课程ID")
    private Long courseId;

    /**
     * 学生编号
     */
    @ExcelProperty(value = "学生编号")
    private String userName;


}
