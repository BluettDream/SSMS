package org.bluett.ssms.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * name: MengHao Tian
 * date: 2023/5/10 21:17
 */
@Data
@NoArgsConstructor
public class CourseImportVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */
    @ExcelProperty(value = "教师编号")
    private String userName;

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
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束日期
     */
    @ExcelProperty(value = "结束日期")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
}
