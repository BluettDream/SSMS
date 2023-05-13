package org.bluett.ssms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.bluett.common.constant.UserConstants;
import org.bluett.common.core.controller.BaseController;
import org.bluett.common.core.domain.R;
import org.bluett.common.core.domain.dto.RoleDTO;
import org.bluett.common.core.domain.model.LoginUser;
import org.bluett.ssms.domain.bo.ScoreBo;
import org.bluett.ssms.domain.vo.ScoreDashBoardVo;
import org.bluett.ssms.service.ICourseService;
import org.bluett.ssms.service.ScoreDashBoardService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 成绩看板
 * <p>
 * name: MengHao Tian
 * date: 2023/5/12 11:11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ssms/scoreDashBoard")
public class ScoreDashBoardController extends BaseController {
    private final ScoreDashBoardService scoreDashBoardService;
    private final ICourseService courseService;

    /**
     * 查询成绩看板详情
     */
    @SaCheckPermission("ssms:score:view")
    @GetMapping(value = "/info")
    public R<ScoreDashBoardVo> dashBoardInfo(ScoreBo bo) {
        checkDataPermission(bo);
        return R.ok(scoreDashBoardService.getScoreDashBoardInfo(bo));
    }

    private void checkDataPermission(ScoreBo bo) {
        LoginUser loginUser = getLoginUser();
        List<RoleDTO> loginUserRoles = loginUser.getRoles();
        if(loginUserRoles.stream().anyMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.TEACHER_ID))){//教师
            List<Long> ids = courseService.queryCourseIdsByUserName(loginUser.getUsername());
            if(ids.size() == 0) ids.add(-999L); // 如果没有查询到数据，则输入一个不存在的课程ID
            bo.getParams().put("courseIdList", ids);
            return;
        }
        if(loginUserRoles.stream().anyMatch(roleDTO -> roleDTO.getRoleId().equals(UserConstants.STUDENT_ID))){//学生
            bo.getParams().put("studentId", loginUser.getUserId());
        }
    }
}
