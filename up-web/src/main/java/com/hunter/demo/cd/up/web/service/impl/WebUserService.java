/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.web.service.impl;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hunter.demo.cd.up.model.UserInfoDto;
import com.hunter.demo.cd.up.param.CommonResp;
import com.hunter.demo.cd.up.web.service.IWebUserService;
import com.hunter.demo.cd.up.service.UserService;

/**
 * <Description> <br> 
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate  <br>
 * @since V8.0<br>
 * @see  <br>
 */

@Service
public class WebUserService implements IWebUserService {

    @Autowired
    @Qualifier("userServiceMock")
    private UserService userService;
    
    @Override
    public CommonResp addUser(UserInfoDto req) {
        CommonResp resp = new CommonResp();
        UserInfoDto user = userService.getUserInfoByCode(req.getUserCode());
        if (user != null) {
            resp.setRespCode("AU-001");
            resp.setRespMsg("User account already exist!!!");
        }
        else {
            userService.addUser(req);
            resp.setRespCode("AU-000");
            resp.setRespMsg("Success!!!");
        }
        return resp;
    }

    @Override public CommonResp userLogin(UserInfoDto loginUser) {
        CommonResp resp = new CommonResp();
        if (loginUser.getUserCode() == null || loginUser.getUserPass() == null) {
            resp.setRespCode("LOGIN-001");
            resp.setRespMsg("userCode is invalid");
            return resp;
        }

        UserInfoDto userLocal = userService.getUserInfoByCode(loginUser.getUserCode());
        if (userLocal == null) {
            resp.setRespCode("LOGIN-001");
            resp.setRespMsg("userCode is invalid");
            return resp;
        }

        if (!userLocal.getUserPass().equals(loginUser.getUserPass())) {
            resp.setRespCode("LOGIN-002");
            resp.setRespMsg("password not correct");
            return resp;
        }
        
        loginUser.setUserId(userLocal.getUserId());
        loginUser.setUserName(userLocal.getUserName());
        
        resp.setRespCode("LOGIN-000");
        resp.setRespMsg("Success!!!");
        return resp;
    }
}
