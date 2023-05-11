package org.bluett.ssms.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * name: MengHao Tian
 * date: 2023/5/11 09:40
 */
@Data
@NoArgsConstructor
public class ScoreImportVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学生编号
     */
    @ExcelProperty(value = "学生编号")
    private String userName;

    /**
     * 课程编号
     */
    @ExcelProperty(value = "课程编号")
    private Long courseId;

    /**
     * 分数
     */
    @ExcelProperty(value = "分数")
    @Max(value = 100, message = "分数不能大于100")
    @Min(value = 0, message = "分数不能小于0")
    private Double score;
}
