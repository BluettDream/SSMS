package org.bluett.ssms.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.bluett.common.annotation.ExcelDictFormat;
import org.bluett.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 课程信息视图对象 ssms_course
 *
 * @author bluett
 * @date 2023-05-08
 */
@Data
@ExcelIgnoreUnannotated
public class CourseVo {

    private static final long serialVersionUID = 1L;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String courseName;

    /**
     * 学分
     */
    @ExcelProperty(value = "学分")
    private Long credit;

    /**
     * 开始日期
     */
    @ExcelProperty(value = "开始日期")
    private Date startTime;

    /**
     * 结束日期
     */
    @ExcelProperty(value = "结束日期")
    private Date finishTime;


}
