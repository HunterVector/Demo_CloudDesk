/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.param;/**
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

import com.hunter.demo.cd.up.model.FileInfoDto;
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

public class FileQryResult {
    
    private String result;

    private List<FileInfoDto> fileInfoList;
    
    private UserInfoDto userInfo;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<FileInfoDto> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<FileInfoDto> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }
}
