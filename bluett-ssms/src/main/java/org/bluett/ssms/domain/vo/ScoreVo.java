package org.bluett.ssms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.bluett.common.annotation.ExcelDictFormat;
import org.bluett.common.convert.ExcelDictConvert;
import lombok.Data;


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
     * 分数
     */
    @ExcelProperty(value = "分数")
    private Long score;


}
