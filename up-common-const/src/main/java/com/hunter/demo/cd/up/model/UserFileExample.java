/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.model;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class UserFileExample {
    /* 单例 */
    private static UserFileExample userFileExample;

    private Map<Long, List<UserFileDto>> exampleMap = new HashMap<>();

    public List<UserFileDto> getUserFileById(Long userId) {
        return exampleMap.get(userId);
    }

    public static UserFileExample getInstance() {
        if (userFileExample == null) {
            userFileExample = new UserFileExample();
            List<UserFileDto> userFileList1 = new ArrayList<>();
            UserFileDto fileId1 = new UserFileDto();
            fileId1.setUserId(1L);
            fileId1.setFileId(1L);
            userFileList1.add(fileId1);

            UserFileDto fileId3 = new UserFileDto();
            fileId3.setUserId(1L);
            fileId3.setFileId(2L);
            userFileList1.add(fileId3);

            UserFileDto fileId4 = new UserFileDto();
            fileId4.setUserId(1L);
            fileId4.setFileId(4L);
            userFileList1.add(fileId4);

            List<UserFileDto> userFileList2 = new ArrayList<>();
            UserFileDto fileId2 = new UserFileDto();
            fileId2.setUserId(2L);
            fileId2.setFileId(3L);
            userFileList2.add(fileId2);
            
            userFileExample.getExampleMap().put(1L, userFileList1);
            userFileExample.getExampleMap().put(2L, userFileList2);
        }
        return userFileExample;
    }

    public Map<Long, List<UserFileDto>> getExampleMap() {
        return exampleMap;
    }
}
