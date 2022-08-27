package com.hunter.demo.cd.up.service;

import com.hunter.demo.cd.up.model.UserInfoDto;

/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate ${date} <br>
 * @see ${package_name} <br>
 * @since V8.0<br>
 */
public interface UserService {

    UserInfoDto getUserInfoByCode(String userCode);
    
    void addUser(UserInfoDto user);
}
