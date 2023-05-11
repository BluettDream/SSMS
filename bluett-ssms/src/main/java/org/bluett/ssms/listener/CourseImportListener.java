package org.bluett.ssms.listener;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.bluett.common.excel.ExcelListener;
import org.bluett.common.excel.ExcelResult;
import org.bluett.common.exception.ServiceException;
import org.bluett.common.utils.spring.SpringUtils;
import org.bluett.ssms.domain.bo.CourseBo;
import org.bluett.ssms.domain.vo.CourseImportVo;
import org.bluett.ssms.service.ICourseService;

import java.util.List;

/**
 * name: MengHao Tian
 * date: 2023/5/10 21:10
 */
@Slf4j
public class CourseImportListener extends AnalysisEventListener<CourseImportVo> implements ExcelListener<CourseImportVo> {
    private final ICourseService courseService;
    private final Boolean isUpdateSupport;
    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public CourseImportListener(Boolean isUpdateSupport) {
        this.courseService = SpringUtils.getBean(ICourseService.class);
        this.isUpdateSupport = isUpdateSupport;
    }

    @Override
    public ExcelResult<CourseImportVo> getExcelResult() {
        return new ExcelResult<CourseImportVo>() {
            @Override
            public List<CourseImportVo> getList() {
                return null;
            }

            @Override
            public List<String> getErrorList() {
                return null;
            }

            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                    throw new ServiceException(failureMsg.toString());
                } else successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
                return successMsg.toString();
            }
        };
    }

    @Override
    public void invoke(CourseImportVo data, AnalysisContext context) {
        // 校验课程是否唯一
        Boolean unique = courseService.checkCourseUnique(BeanUtil.toBean(data, CourseBo.class));
        try {
            if(unique){
                CourseBo bo = BeanUtil.toBean(data, CourseBo.class);
                courseService.insertByBo(bo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、课程 ").append(bo.getCourseName()).append(" 导入成功");
            }else if(isUpdateSupport){
                CourseBo bo = BeanUtil.toBean(data, CourseBo.class);
                courseService.updateByBo(bo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、课程 ").append(bo.getCourseName()).append(" 更新成功");
            }else{
                failureNum++;
                String msg = "<br/>" + failureNum + "、课程 " + data.getCourseName() + " 导入失败：" + "课程已存在";
                failureMsg.append(msg);
            }
        }catch (Exception e){
            failureNum++;
            String msg = "<br/>" + failureNum + "、课程 " + data.getCourseName() + " 导入失败：";
            failureMsg.append(msg).append(e.getMessage());
            log.error(msg, e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
