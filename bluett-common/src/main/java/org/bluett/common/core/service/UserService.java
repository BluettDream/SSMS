package org.bluett.common.core.service;

import org.bluett.common.core.domain.entity.SysUser;

/**
 * 通用 用户服务
 *
 * @author Lion Li
 */
public interface UserService {

    /**
     * 通过用户ID查询用户账户
     *
     * @param userId 用户ID
     * @return 用户账户
     */
    String selectUserNameById(Long userId);

    /**
     * 通过用户名称查询用户
     *
     * @param userName 用户名称
     * @return 用户
     */
    SysUser selectUserByUserName(String userName);

}
