package org.bluett.ssms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.bluett.common.annotation.ExcelDictFormat;
import org.bluett.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;


/**
 * 分数信息视图对象 ssms_score
 *
 * @author bluett
 * @date 2023-05-08
 */
@Data
@ExcelIgnoreUnannotated
public class ScoreVo {

    private static final long serialVersionUID = 1L;

    /**
     * 分数编号
     */
    private Long scoreId;

    /**
     * 学生编号
     */
    @ExcelProperty(value = "学生编号")
    private String userName;

    /**
     * 学生姓名
     */
    @ExcelProperty(value = "学生姓名")
    private String nickName;

    /**
     * 课程编号
     */
    @ExcelProperty(value = "课程编号")
    private Long courseId;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String courseName;

    /**
     * 教师姓名
     */
    @ExcelProperty(value = "教师姓名")
    private String teacherNickName;

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

    /**
     * 学分
     */
    @ExcelProperty(value = "学分")
    private Double credit;

    /**
     * 分数
     */
    @ExcelProperty(value = "分数")
    private Double score;
}
