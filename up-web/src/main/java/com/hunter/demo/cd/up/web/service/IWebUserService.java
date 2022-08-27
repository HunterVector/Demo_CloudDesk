package com.hunter.demo.cd.up.web.service;

import com.hunter.demo.cd.up.model.UserInfoDto;
import com.hunter.demo.cd.up.param.CommonResp;

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
public interface IWebUserService {
    CommonResp addUser(UserInfoDto user);

    CommonResp userLogin(UserInfoDto user);
}
