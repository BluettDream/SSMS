package org.bluett.ssms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.bluett.common.annotation.Log;
import org.bluett.common.annotation.RepeatSubmit;
import org.bluett.common.constant.UserConstants;
import org.bluett.common.core.controller.BaseController;
import org.bluett.common.core.domain.PageQuery;
import org.bluett.common.core.domain.R;
import org.bluett.common.core.domain.model.LoginUser;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.common.core.validate.AddGroup;
import org.bluett.common.core.validate.EditGroup;
import org.bluett.common.enums.BusinessType;
import org.bluett.common.excel.ExcelResult;
import org.bluett.common.utils.DateUtils;
import org.bluett.common.utils.poi.ExcelUtil;
import org.bluett.ssms.domain.bo.CourseBo;
import org.bluett.ssms.domain.vo.CourseImportVo;
import org.bluett.ssms.domain.vo.CourseVo;
import org.bluett.ssms.listener.CourseImportListener;
import org.bluett.ssms.service.ICourseService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        checkDataPermission(bo);
        return iCourseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程信息列表
     */
    @SaCheckPermission("ssms:course:export")
    @Log(title = "课程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CourseBo bo, HttpServletResponse response) {
        checkDataPermission(bo);
        List<CourseVo> list = iCourseService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程信息", CourseVo.class, response);
    }

    /**
     * 导入数据
     *
     * @param file          导入文件
     * @param updateSupport 是否更新已存在数据
     */
    @Log(title = "课程管理", businessType = BusinessType.IMPORT)
    @SaCheckPermission("ssms:course:import")
    @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Void> importData(@RequestPart("file") MultipartFile file, boolean updateSupport) throws Exception {
        ExcelResult<CourseImportVo> result = ExcelUtil.importExcel(file.getInputStream(), CourseImportVo.class, new CourseImportListener(updateSupport));
        return R.ok(result.getAnalysis());
    }

    /**
     * 获取导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws ParseException {
        List<CourseImportVo> template = new ArrayList<>();
        CourseImportVo courseImportVo = new CourseImportVo();
        courseImportVo.setCourseName("语文(!这一行必须删除)");
        courseImportVo.setCredit(2.0);
        courseImportVo.setUserName("20230510145(!该行为示例行)");
        courseImportVo.setStartTime(DateUtils.parseDate("2023-02-01 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS));
        courseImportVo.setFinishTime(DateUtils.parseDate("2023-06-30 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS));
        template.add(courseImportVo);
        ExcelUtil.exportExcel(template, "课程数据", CourseImportVo.class, response);
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

    private void checkDataPermission(CourseBo bo) {
        // 获取当前登录用户,管理员查看所有课程(并且可以查询指定教师编号课程),非管理员只能查看自己课程
        LoginUser loginUser = getLoginUser();
        // 不是管理员会指定user_id查询,是管理员则不指定查询全部
        bo.getParams().put("userId", loginUser.getUserId());
        if(loginUser.getRoles().stream().anyMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.ADMIN_ID))) bo.getParams().put("userId", null);
    }
}
