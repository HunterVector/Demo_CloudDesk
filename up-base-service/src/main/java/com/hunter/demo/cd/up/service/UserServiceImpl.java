/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.service;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hunter.demo.cd.up.mapper.UserInfoMapper;
import com.hunter.demo.cd.up.model.UserFileDto;
import com.hunter.demo.cd.up.model.UserInfoDto;

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
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Override
    public UserInfoDto getUserInfoByCode(String userCode) {
        return userInfoMapper.getUserInfoByUserCode(userCode);
    }
    
    @Override public void addUser(UserInfoDto user) {
        
    }
}
