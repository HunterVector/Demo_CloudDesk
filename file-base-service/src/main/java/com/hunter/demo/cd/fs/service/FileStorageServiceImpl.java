/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.fs.service;/**
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
import com.hunter.demo.cd.fs.common.model.FileStorageDto;
import com.hunter.demo.cd.fs.mapper.FileStorageMapper;

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
@Service("fileStorageService")
public class FileStorageServiceImpl implements IFileStorageService {
    
    @Autowired
    private FileStorageMapper fileStorageMapper;

    @Override
    public String getFilePathById(Long fileId) {
        String path = "";
        FileStorageDto fileStorageDto = fileStorageMapper.getFileStorageById(fileId);
        if (fileStorageDto != null) {
            path = fileStorageDto.getFilePath();
        }
        return path;
    }


    @Override 
    public void addFileStorage(FileStorageDto fileStorageDto) {
        fileStorageMapper.addFileStorage(fileStorageDto);
    }

    @Override public void delFileStorageById(Long fileId) {
        fileStorageMapper.delFileStorageById(fileId);
    }
}
