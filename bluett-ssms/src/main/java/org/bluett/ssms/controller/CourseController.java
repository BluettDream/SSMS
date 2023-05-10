package org.bluett.ssms.controller;

import java.util.List;
import java.util.Arrays;
import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.bluett.common.constant.UserConstants;
import org.bluett.common.core.domain.dto.RoleDTO;
import org.bluett.common.core.domain.model.LoginUser;
import org.bluett.common.helper.DataPermissionHelper;
import org.bluett.common.utils.StreamUtils;
import org.bluett.common.utils.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.bluett.common.annotation.RepeatSubmit;
import org.bluett.common.annotation.Log;
import org.bluett.common.core.controller.BaseController;
import org.bluett.common.core.domain.PageQuery;
import org.bluett.common.core.domain.R;
import org.bluett.common.core.validate.AddGroup;
import org.bluett.common.core.validate.EditGroup;
import org.bluett.common.enums.BusinessType;
import org.bluett.common.utils.poi.ExcelUtil;
import org.bluett.ssms.domain.vo.CourseVo;
import org.bluett.ssms.domain.bo.CourseBo;
import org.bluett.ssms.service.ICourseService;
import org.bluett.common.core.page.TableDataInfo;

/**
 * 课程信息
 *
 * @author bluett
 * @date 2023-05-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ssms/course")
public class CourseController extends BaseController {

    private final ICourseService iCourseService;

    /**
     * 查询课程信息列表
     */
    @SaCheckPermission("ssms:course:list")
    @GetMapping("/list")
    public TableDataInfo<CourseVo> list(CourseBo bo, PageQuery pageQuery) {
        // 获取当前登录用户,管理员查看所有课程(并且可以查询指定教师编号课程),非管理员只能查看自己课程
        LoginUser loginUser = getLoginUser();
        // 不是管理员会指定user_id查询,是管理员则不指定查询全部
        bo.getParams().put("userId", loginUser.getUserId());
        if(loginUser.getRoles().stream().anyMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.ADMIN_ID))) bo.getParams().put("userId", null);
        return iCourseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程信息列表
     */
    @SaCheckPermission("ssms:course:export")
    @Log(title = "课程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CourseBo bo, HttpServletResponse response) {
        List<CourseVo> list = iCourseService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程信息", CourseVo.class, response);
    }

    /**
     * 获取课程信息详细信息
     *
     * @param courseId 主键
     */
    @SaCheckPermission("ssms:course:query")
    @GetMapping("/{courseId}")
    public R<CourseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long courseId) {
        return R.ok(iCourseService.queryById(courseId));
    }

    /**
     * 新增课程信息
     */
    @SaCheckPermission("ssms:course:add")
    @Log(title = "课程信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CourseBo bo) {
        return toAjax(iCourseService.insertByBo(bo));
    }

    /**
     * 修改课程信息
     */
    @SaCheckPermission("ssms:course:edit")
    @Log(title = "课程信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CourseBo bo) {
        return toAjax(iCourseService.updateByBo(bo));
    }

    /**
     * 删除课程信息
     *
     * @param courseIds 主键串
     */
    @SaCheckPermission("ssms:course:remove")
    @Log(title = "课程信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] courseIds) {
        return toAjax(iCourseService.deleteWithValidByIds(Arrays.asList(courseIds), true));
    }
}
