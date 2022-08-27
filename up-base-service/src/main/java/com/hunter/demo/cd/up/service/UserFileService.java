package com.hunter.demo.cd.up.service;

import java.util.List;

import com.hunter.demo.cd.up.model.FileInfoDto;
import com.hunter.demo.cd.up.model.UserFileDto;

/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate ${date} <br>
 * @see ${package_name} <br>
 * @since V8.0<br>
 */
public interface UserFileService {
    List<UserFileDto> getUserFileById(Long userId);

    void addUserFile(UserFileDto userFileDto);

    void delUserFileById(Long userId, Long fileId);

    String getFileNameById(Long fileId);

    FileInfoDto getFileInfoById(Long fileId);

    void addFileInfo(FileInfoDto fileInfoDto);

    void delFileById(Long fileId);

    List<FileInfoDto> queryFile(Long fileId);
}
