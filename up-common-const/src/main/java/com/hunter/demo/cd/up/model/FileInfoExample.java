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

import java.util.HashMap;
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

public class FileInfoExample {

    /* 单例 */
    private static FileInfoExample fileInfoExample;
    
    private static long fileIdSeq = 10L;

    private Map<Long, FileInfoDto> exampleMap = new HashMap<>();

    public String getFileNameById(Long fileId) {
        if (exampleMap.get(fileId) != null) {
            return exampleMap.get(fileId).getFileName();
        }
        return "";
    }

    public FileInfoDto getFileInfoById(Long fileId) {
        return exampleMap.get(fileId);
    }

    public static FileInfoExample getInstance() {
        if (fileInfoExample == null) {
            fileInfoExample = new FileInfoExample();
            FileInfoDto file = new FileInfoDto();
            file.setFileId(1L);
            file.setFileName("testFolder");
            file.setFolderFlag("Y");
            fileInfoExample.getExampleMap().put(1L, file);

            FileInfoDto file1 = new FileInfoDto();
            file1.setFileId(2L);
            file1.setFileName("testDownLoad1.txt");
            file1.setFolderFlag("N");
            file1.setPathId(1L);
            fileInfoExample.getExampleMap().put(2L, file1);

            FileInfoDto file2 = new FileInfoDto();
            file2.setFileId(3L);
            file2.setFileName("testDownLoad2.txt");
            file2.setFolderFlag("N");
            file2.setPathId(2L);
            fileInfoExample.getExampleMap().put(3L, file2);

            FileInfoDto file3 = new FileInfoDto();
            file3.setFileId(4L);
            file3.setFileName("errorFile.txt");
            file3.setFolderFlag("N");
            file3.setParentFileId(1L);
            file3.setPathId(0L);
            fileInfoExample.getExampleMap().put(4L, file3);
        }
        return fileInfoExample;
    }

    public Map<Long, FileInfoDto> getExampleMap() {
        return exampleMap;
    }
    
    public static long getFileIdSeq() {
        long nowFileId = fileIdSeq;
        fileIdSeq++;
        return nowFileId;
    }
}
