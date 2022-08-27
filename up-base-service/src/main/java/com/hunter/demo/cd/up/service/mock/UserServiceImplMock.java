/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.service.mock;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hunter.demo.cd.up.model.UserFileDto;
import com.hunter.demo.cd.up.model.UserFileExample;
import com.hunter.demo.cd.up.model.UserInfoDto;
import com.hunter.demo.cd.up.model.UserInfoExample;
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
@Service("userServiceMock")
public class UserServiceImplMock implements UserService {
    @Override public UserInfoDto getUserInfoByCode(String userCode) {
        return UserInfoExample.getInstance().getUserByCode(userCode);
    }

    @Override public void addUser(UserInfoDto user) {
        user.setUserId(UserInfoExample.getUserIdSeq());
        UserInfoExample.getInstance().getUserMap().put(user.getUserCode(), user);
        UserInfoExample.getInstance().getExampleMap().put(user.getUserId(), user);
    }
}
