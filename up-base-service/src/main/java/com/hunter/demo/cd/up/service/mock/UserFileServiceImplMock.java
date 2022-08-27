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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hunter.demo.cd.up.model.FileInfoDto;
import com.hunter.demo.cd.up.model.FileInfoExample;
import com.hunter.demo.cd.up.model.UserFileDto;
import com.hunter.demo.cd.up.model.UserFileExample;
import com.hunter.demo.cd.up.service.UserFileService;

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

@Service("userFileServiceMock")
public class UserFileServiceImplMock implements UserFileService {

    @Override public List<UserFileDto> getUserFileById(Long userId) {
        return UserFileExample.getInstance().getUserFileById(userId);
    }

    @Override
    public void addUserFile(UserFileDto userFileDto) {
        List<UserFileDto> list = getUserFileById(userFileDto.getUserId());
        list.add(userFileDto);
        UserFileExample.getInstance().getExampleMap().put(userFileDto.getUserId(), list);
    }

    @Override
    public void delUserFileById(Long userId, Long fileId) {
        List<UserFileDto> list = getUserFileById(userId);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            UserFileDto dto = (UserFileDto)iterator.next();
            if (fileId.equals(dto.getFileId())) {
                iterator.remove();
            }
        }
    }

    @Override public String getFileNameById(Long fileId) {
        return FileInfoExample.getInstance().getFileNameById(fileId);
    }

    @Override public FileInfoDto getFileInfoById(Long fileId) {
        return FileInfoExample.getInstance().getFileInfoById(fileId);
    }

    @Override public void addFileInfo(FileInfoDto fileInfoDto) {
        Long fileId = FileInfoExample.getFileIdSeq();
        fileInfoDto.setFileId(fileId);
        FileInfoExample.getInstance().getExampleMap().put(fileInfoDto.getFileId(), fileInfoDto);
    }

    @Override public void delFileById(Long fileId) {
        FileInfoExample.getInstance().getExampleMap().remove(fileId);
    }

    @Override public List<FileInfoDto> queryFile(Long fileId) {
        List<FileInfoDto> list = new ArrayList<>();
        Map<Long, FileInfoDto> map = FileInfoExample.getInstance().getExampleMap();
        Set<Map.Entry<Long, FileInfoDto>> set = map.entrySet();
        for (Map.Entry<Long, FileInfoDto> entry : set) {
            FileInfoDto dto = entry.getValue();
            if (dto != null && fileId.equals(dto.getParentFileId())) {
                list.add(dto);
            }
        }
        return list;
    }
}
