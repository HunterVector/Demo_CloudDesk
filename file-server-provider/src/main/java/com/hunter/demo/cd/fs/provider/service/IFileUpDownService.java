package com.hunter.demo.cd.fs.provider.service;

import java.io.File;

import com.hunter.demo.cd.fs.common.param.FileDeleteResp;
import com.hunter.demo.cd.fs.common.param.FileUpRsp;

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
public interface IFileUpDownService {
    String hello();

    String buildFileInfo(File file);

    File findFileById(Long fileId);

    String findFileNameById(Long fileId);

    void upFileInfo(String fileInfo, String fileName, String parentId, FileUpRsp upRsp);

    void delFileInfo(Long fileId, FileDeleteResp delRsp);
}
