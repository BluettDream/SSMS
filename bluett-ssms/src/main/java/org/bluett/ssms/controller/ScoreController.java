package org.bluett.ssms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.bluett.common.annotation.Log;
import org.bluett.common.annotation.RepeatSubmit;
import org.bluett.common.constant.UserConstants;
import org.bluett.common.core.controller.BaseController;
import org.bluett.common.core.domain.PageQuery;
import org.bluett.common.core.domain.R;
import org.bluett.common.core.domain.dto.RoleDTO;
import org.bluett.common.core.domain.model.LoginUser;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.common.core.validate.AddGroup;
import org.bluett.common.core.validate.EditGroup;
import org.bluett.common.enums.BusinessType;
import org.bluett.common.excel.ExcelResult;
import org.bluett.common.utils.poi.ExcelUtil;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreDashBoardVo;
import org.bluett.ssms.domain.vo.ScoreImportVo;
import org.bluett.ssms.domain.vo.ScoreVo;
import org.bluett.ssms.listener.ScoreImportListener;
import org.bluett.ssms.service.ICourseService;
import org.bluett.ssms.service.IScoreService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 成绩信息
 *
 * @author bluett
 * @date 2023-05-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ssms/score")
public class ScoreController extends BaseController {

    private final IScoreService iScoreService;
    private final ICourseService iCourseService;

    /**
     * 查询成绩信息列表
     */
    @SaCheckPermission("ssms:score:list")
    @GetMapping("/list")
    public TableDataInfo<ScoreVo> list(ScoreBo bo, PageQuery pageQuery) {
        checkDataPermission(bo);
        return iScoreService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询成绩看板
     */
    @SaCheckPermission("ssms:score:view")
    @GetMapping(value = "/panelInfo")
    public R<ScoreDashBoardVo> panelInfo(ScoreBo bo) {
        checkDataPermission(bo);
        return R.ok(iScoreService.queryDashBoardInfo(bo));
    }

    /**
     * 导出成绩信息列表
     */
    @SaCheckPermission("ssms:score:export")
    @Log(title = "成绩信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ScoreBo bo, HttpServletResponse response) {
        checkDataPermission(bo);
        List<ScoreVo> list = iScoreService.queryList(bo);
        ExcelUtil.exportExcel(list, "成绩信息", ScoreVo.class, response);
    }

    /**
     * 导入数据
     *
     * @param file          导入文件
     * @param updateSupport 是否更新已存在数据
     */
    @Log(title = "成绩管理", businessType = BusinessType.IMPORT)
    @SaCheckPermission("ssms:score:import")
    @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Void> importData(@RequestPart("file") MultipartFile file, boolean updateSupport) throws Exception {
        ExcelResult<ScoreImportVo> result = ExcelUtil.importExcel(file.getInputStream(), ScoreImportVo.class, new ScoreImportListener(updateSupport));
        return R.ok(result.getAnalysis());
    }

    /**
     * 获取导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        List<ScoreImportVo> template = new ArrayList<>();
        ScoreImportVo scoreImportVo = new ScoreImportVo();
        scoreImportVo.setUserName("20230511105(!该行为示例行,这一行必须删除)");
        scoreImportVo.setCourseId(1L);
        scoreImportVo.setScore(98.5);
        template.add(scoreImportVo);
        ExcelUtil.exportExcel(template, "成绩数据", ScoreImportVo.class, response);
    }

    /**
     * 获取成绩信息详细信息
     *
     * @param scoreId 主键
     */
    @SaCheckPermission("ssms:score:query")
    @GetMapping("/{scoreId}")
    public R<ScoreVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long scoreId) {
        return R.ok(iScoreService.queryById(scoreId));
    }

    /**
     * 新增成绩信息
     */
    @SaCheckPermission("ssms:score:add")
    @Log(title = "成绩信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ScoreBo bo) {
        return toAjax(iScoreService.insertByBo(bo));
    }

    /**
     * 修改成绩信息
     */
    @SaCheckPermission("ssms:score:edit")
    @Log(title = "成绩信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ScoreBo bo) {
        return toAjax(iScoreService.updateByBo(bo));
    }

    /**
     * 删除成绩信息
     *
     * @param scoreIds 主键串
     */
    @SaCheckPermission("ssms:score:remove")
    @Log(title = "成绩信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{scoreIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] scoreIds) {
        return toAjax(iScoreService.deleteWithValidByIds(Arrays.asList(scoreIds), true));
    }

    private void checkDataPermission(ScoreBo bo) {
        LoginUser loginUser = getLoginUser();
        List<RoleDTO> loginUserRoles = loginUser.getRoles();
        if(loginUserRoles.stream().anyMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.TEACHER_ID))){//教师
            List<Long> ids = iCourseService.queryCourseIdsByUserName(loginUser.getUsername());
            if(ids.size() == 0) ids.add(-999L); // 如果没有查询到数据，则输入一个不存在的课程ID
            bo.getParams().put("courseIdList", ids);
            return;
        }
        if(loginUserRoles.stream().anyMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.STUDENT_ID))){//学生
            bo.getParams().put("studentId", loginUser.getUserId());
        }
    }
}
