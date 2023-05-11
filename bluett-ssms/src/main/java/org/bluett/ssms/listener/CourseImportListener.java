package org.bluett.ssms.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.bluett.common.core.domain.entity.SysUser;
import org.bluett.common.core.domain.model.LoginUser;
import org.bluett.common.excel.ExcelListener;
import org.bluett.common.excel.ExcelResult;
import org.bluett.common.exception.ServiceException;
import org.bluett.common.helper.LoginHelper;
import org.bluett.common.utils.spring.SpringUtils;
import org.bluett.ssms.domain.bo.CourseBo;
import org.bluett.ssms.domain.vo.CourseImportVo;
import org.bluett.ssms.service.ICourseService;
import org.bluett.system.service.ISysUserService;

import java.util.List;

/**
 * name: MengHao Tian
 * date: 2023/5/10 21:10
 */
@Slf4j
public class CourseImportListener extends AnalysisEventListener<CourseImportVo> implements ExcelListener<CourseImportVo> {
    private final ICourseService courseService;
    private final ISysUserService userService;
    private final Boolean isUpdateSupport;
    private final LoginUser loginUser;
    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public CourseImportListener(Boolean isUpdateSupport) {
        this.courseService = SpringUtils.getBean(ICourseService.class);
        this.userService = SpringUtils.getBean(ISysUserService.class);
        this.isUpdateSupport = isUpdateSupport;
        this.loginUser = LoginHelper.getLoginUser();
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
        // 校验添加的教师编号是否存在
        SysUser teacher = userService.selectUserByUserName(data.getUserName());
        try {
            if(ObjectUtil.isNotNull(teacher) && unique){
                validatePermission(teacher);
                CourseBo bo = BeanUtil.toBean(data, CourseBo.class);
                courseService.insertByBo(bo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、课程 ").append(bo.getCourseName()).append(" 导入成功");
            }else if(ObjectUtil.isNotNull(teacher) && !unique && isUpdateSupport){
                validatePermission(teacher);
                CourseBo bo = BeanUtil.toBean(data, CourseBo.class);
                courseService.updateByBo(bo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、课程 ").append(bo.getCourseName()).append(" 更新成功");
            }else{
                failureNum++;
                String msg = "<br/>" + failureNum + "、课程 " + data.getCourseName() + " 导入失败：";
                if(ObjectUtil.isNull(teacher)) msg += "教师编号不存在";
                else msg += "课程已存在";
                failureMsg.append(msg);
            }
        }catch (Exception e){
            failureNum++;
            String msg = "<br/>" + failureNum + "、课程 " + data.getCourseName() + " 导入失败：";
            failureMsg.append(msg).append(e.getMessage());
            log.error(msg, e);
        }
    }

    private void validatePermission(SysUser teacher) {
        if(loginUser.getRoles().stream().noneMatch(roleDTO -> roleDTO.getRoleId().equals(1L))) { //登录用户不是管理员
            if(loginUser.getRoles().stream().noneMatch(roleDTO -> roleDTO.getRoleId().equals(5L))) { //登录用户也不是教师
                throw new ServiceException("您没有权限导入课程");
            }else if(!loginUser.getUsername().equals(teacher.getUserName())) { //登录用户是教师，但不是该课程的教师
                throw new ServiceException("您没有权限导入其他教师的课程");
            }
        }
        // 登录用户是管理员允许放行
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
