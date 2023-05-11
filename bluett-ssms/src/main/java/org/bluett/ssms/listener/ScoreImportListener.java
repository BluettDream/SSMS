package org.bluett.ssms.listener;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.bluett.common.excel.ExcelListener;
import org.bluett.common.excel.ExcelResult;
import org.bluett.common.exception.ServiceException;
import org.bluett.common.utils.spring.SpringUtils;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreImportVo;
import org.bluett.ssms.service.IScoreService;

import java.util.List;

/**
 * name: MengHao Tian
 * date: 2023/5/11 09:49
 */
@Slf4j
public class ScoreImportListener extends AnalysisEventListener<ScoreImportVo> implements ExcelListener<ScoreImportVo> {
    private final IScoreService scoreService;
    private final Boolean isUpdateSupport;
    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public ScoreImportListener(Boolean isUpdateSupport) {
        this.scoreService = SpringUtils.getBean(IScoreService.class);
        this.isUpdateSupport = isUpdateSupport;
    }

    @Override
    public ExcelResult<ScoreImportVo> getExcelResult() {
        return new ExcelResult<ScoreImportVo>() {
            @Override
            public List<ScoreImportVo> getList() {
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
    public void invoke(ScoreImportVo data, AnalysisContext context) {
        // 校验课程是否唯一
        Boolean unique = scoreService.checkScoreUnique(BeanUtil.toBean(data, ScoreBo.class));
        try {
            if(unique){
                ScoreBo bo = BeanUtil.toBean(data, ScoreBo.class);
                scoreService.insertByBo(bo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、成绩 ").append(bo.getUserName()).append(" 导入成功");
            }else if(isUpdateSupport){
                ScoreBo bo = BeanUtil.toBean(data, ScoreBo.class);
                scoreService.updateByBo(bo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、成绩 ").append(bo.getUserName()).append(" 更新成功");
            }else{
                failureNum++;
                String msg = "<br/>" + failureNum + "、用户 " + data.getUserName() + "的" + "课程成绩" +" 导入失败：" + "成绩已存在";
                failureMsg.append(msg);
            }
        }catch (Exception e){
            failureNum++;
            String msg = "<br/>" + failureNum + "、用户 " + data.getUserName() + "的" + data.getCourseId() + "课程成绩" +" 导入失败：";
            failureMsg.append(msg).append(e.getMessage());
            log.error(msg, e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
