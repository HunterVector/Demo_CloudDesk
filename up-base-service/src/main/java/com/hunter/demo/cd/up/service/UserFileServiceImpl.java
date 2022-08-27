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

import com.hunter.demo.cd.up.mapper.FileInfoMapper;
import com.hunter.demo.cd.up.mapper.UserFileMapper;
import com.hunter.demo.cd.up.mapper.UserInfoMapper;
import com.hunter.demo.cd.up.model.FileInfoDto;
import com.hunter.demo.cd.up.model.UserFileDto;

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

public class UserFileServiceImpl implements UserFileService{

    @Autowired
    private UserFileMapper userFileMapper;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public List<UserFileDto> getUserFileById(Long userId) {
        List<UserFileDto> fileDtoList = userFileMapper.getUserFileById(userId);
        return fileDtoList;
    }

    @Override
    public void addUserFile(UserFileDto userFileDto) {
        userFileMapper.addUserFile(userFileDto);
    }

    @Override public void delUserFileById(Long userId, Long fileId) {
        userFileMapper.deleteUserFileById(userId, fileId);
    }

    @Override public String getFileNameById(Long fileId) {
        String name = "";
        FileInfoDto fileInfoDto = fileInfoMapper.getFileInfoById(fileId);
        if (fileInfoDto != null) {
            name = fileInfoDto.getFileName();
        }
        return name;
    }

    @Override public FileInfoDto getFileInfoById(Long fileId) {
        return null;
    }

    @Override public void addFileInfo(FileInfoDto fileInfoDto) {
        fileInfoMapper.addFileInfo(fileInfoDto);
    }

    @Override public void delFileById(Long fileId) {
        fileInfoMapper.delFileInfoById(fileId);
    }

    @Override public List<FileInfoDto> queryFile(Long fileId) {
        return null;
    }
}
