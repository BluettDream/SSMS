package org.bluett.ssms.controller;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.bluett.common.helper.DataPermissionHelper;
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
import org.bluett.ssms.domain.vo.ScoreVo;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.service.IScoreService;
import org.bluett.common.core.page.TableDataInfo;

/**
 * 分数信息
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

    /**
     * 查询分数信息列表
     */
    @SaCheckPermission("ssms:score:list")
    @GetMapping("/list")
    public TableDataInfo<ScoreVo> list(ScoreBo bo, PageQuery pageQuery) {
        DataPermissionHelper.setVariable("userNameValue", getUsername());
        return iScoreService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出分数信息列表
     */
    @SaCheckPermission("ssms:score:export")
    @Log(title = "分数信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ScoreBo bo, HttpServletResponse response) {
        List<ScoreVo> list = iScoreService.queryList(bo);
        ExcelUtil.exportExcel(list, "分数信息", ScoreVo.class, response);
    }

    /**
     * 获取分数信息详细信息
     *
     * @param scoreId 主键
     */
    @SaCheckPermission("ssms:score:query")
    @GetMapping("/{scoreId}")
    public R<ScoreVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long scoreId) {
        DataPermissionHelper.setVariable("userNameValue", getUsername());
        return R.ok(iScoreService.queryById(scoreId));
    }

    /**
     * 新增分数信息
     */
    @SaCheckPermission("ssms:score:add")
    @Log(title = "分数信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ScoreBo bo) {
        DataPermissionHelper.setVariable("userNameValue", getUsername());
        return toAjax(iScoreService.insertByBo(bo));
    }

    /**
     * 修改分数信息
     */
    @SaCheckPermission("ssms:score:edit")
    @Log(title = "分数信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ScoreBo bo) {
        DataPermissionHelper.setVariable("userNameValue", getUsername());
        return toAjax(iScoreService.updateByBo(bo));
    }

    /**
     * 删除分数信息
     *
     * @param scoreIds 主键串
     */
    @SaCheckPermission("ssms:score:remove")
    @Log(title = "分数信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{scoreIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] scoreIds) {
        DataPermissionHelper.setVariable("userNameValue", getUsername());
        return toAjax(iScoreService.deleteWithValidByIds(Arrays.asList(scoreIds), true));
    }
}
