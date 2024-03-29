package org.bluett.ssms.domain.vo;

import java.io.Serializable;
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
public class CourseVo  implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    @ExcelProperty(value = "课程ID")
    private Long courseId;

    /**
     * 用户名称(教师编号)
     */
    @ExcelProperty(value = "用户名称(教师编号)")
    private String userName;

    /**
     * 用户姓名(教师姓名)
     */
    @ExcelProperty(value = "教师姓名")
    private String nickName;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String courseName;

    /**
     * 学分
     */
    @ExcelProperty(value = "学分")
    private Double credit;

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
