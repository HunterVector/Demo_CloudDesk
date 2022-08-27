/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.web.controller;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hunter.demo.cd.up.model.UserInfoDto;
import com.hunter.demo.cd.up.param.AddUserReq;
import com.hunter.demo.cd.up.param.CommonResp;
import com.hunter.demo.cd.up.param.UserLoginReq;
import com.hunter.demo.cd.up.web.config.Test;
import com.hunter.demo.cd.up.web.service.IWebUserService;

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

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired IWebUserService userService;

    @Autowired Test test;

    @RequestMapping(value = "/addUser")
    @ResponseBody
    public CommonResp testAddUser(AddUserReq req, HttpSession httpSession){
        UserInfoDto user = new UserInfoDto();
        user.setUserPass(req.getUserPass());
        user.setUserCode(req.getUserCode());
        user.setUserName(req.getUserName());
        return userService.addUser(user);

    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public CommonResp testLogin(UserLoginReq request, HttpSession httpSession){
        //判断password 匹配，不匹配，返回error
        String userCode = request.getUserCode();
        String userPass = request.getUserPass();
        
        if (userCode == null) {
            userCode = test.getTestData().getUserCode();
        }

        if (userPass == null) {
            userPass = test.getTestData().getPassword();
        }
        
        UserInfoDto user = new UserInfoDto();
        user.setUserCode(userCode);
        user.setUserPass(userPass);
        CommonResp resp = userService.userLogin(user);
        if ("LOGIN-000".equals(resp.getRespCode())) {
            //匹配设置session 属性；
            httpSession.setAttribute("userInfo", user);
        }
        return resp;
    }
}
