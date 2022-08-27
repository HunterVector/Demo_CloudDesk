/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.helper;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

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

public class UserFileHelper {
    
    public static UserFileDto buildUserFile(UserInfoDto user, Long fileId) {
        UserFileDto userFileDto = new UserFileDto();
        userFileDto.setFileId(fileId);
        if (user != null) {
            userFileDto.setUserId(user.getUserId());
            return userFileDto;
        }
        return null;
    }
}
