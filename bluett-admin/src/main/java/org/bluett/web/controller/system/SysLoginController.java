package org.bluett.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.bluett.common.constant.Constants;
import org.bluett.common.core.domain.R;
import org.bluett.common.core.domain.entity.SysMenu;
import org.bluett.common.core.domain.entity.SysUser;
import org.bluett.common.core.domain.model.LoginBody;
import org.bluett.common.core.domain.model.LoginUser;
import org.bluett.common.helper.LoginHelper;
import org.bluett.system.domain.vo.RouterVo;
import org.bluett.system.service.ISysMenuService;
import org.bluett.system.service.ISysUserService;
import org.bluett.system.service.SysLoginService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysLoginController {

    private final SysLoginService loginService;
    private final ISysMenuService menuService;
    private final ISysUserService userService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/login")
    public R<Map<String, Object>> login(@Validated @RequestBody LoginBody loginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
            loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 人脸登录
     *
     * @param faceImg 人脸照片
     * @return 结果
     */
    @SaIgnore
    @PostMapping(value = "/faceLogin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Map<String, Object>> faceLogin(@RequestPart("faceImg") MultipartFile faceImg){
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.faceLogin(faceImg);
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<Map<String, Object>> getInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUserId());
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roles", loginUser.getRolePermission());
        ajax.put("permissions", loginUser.getMenuPermission());
        return R.ok(ajax);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = LoginHelper.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }
}
