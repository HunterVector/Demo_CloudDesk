/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.fs.service.mock;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import org.springframework.stereotype.Service;

import com.hunter.demo.cd.fs.common.model.FileStorageDto;
import com.hunter.demo.cd.fs.common.model.FileStorageExample;
import com.hunter.demo.cd.fs.service.IFileStorageService;

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

@Service("fileStorageServiceMock")
public class FileStorageServiceMock implements IFileStorageService {
    @Override public String getFilePathById(Long fileId) {
        return FileStorageExample.getInstance().getFilePathById(fileId);
    }

    @Override 
    public void addFileStorage(FileStorageDto fileStorageDto) {
        Long fileId = FileStorageExample.getFileIdSeq();
        fileStorageDto.setFileId(fileId);
        FileStorageExample.getInstance().getExampleMap().put(fileId, fileStorageDto.getFilePath());
    }

    @Override public void delFileStorageById(Long fileId) {
        FileStorageExample.getInstance().getExampleMap().remove(fileId);
    }
}
