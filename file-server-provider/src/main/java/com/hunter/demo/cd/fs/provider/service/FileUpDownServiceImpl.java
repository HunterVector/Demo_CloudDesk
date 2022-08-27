/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.fs.provider.service;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hunter.demo.cd.fs.common.model.FileStorageDto;
import com.hunter.demo.cd.fs.common.param.FileDeleteResp;
import com.hunter.demo.cd.fs.common.param.FileUpRsp;
import com.hunter.demo.cd.fs.provider.config.FileServerConfig;
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

@Service("fileUpDownService")
public class FileUpDownServiceImpl implements IFileUpDownService {
    
    @Autowired
    private FileServerConfig fileServerConfig;

    @Autowired
    @Qualifier("fileStorageServiceMock")
    private IFileStorageService fileStorageService;

    @Override public String hello() {
        return "hello world!!!!";
    }

    @Override public String buildFileInfo(File file) {
        String fileInfo = "";
        if (file != null) {
            // try with resource，小括号里面资源，自动释放，自动关闭流
            try (FileInputStream fis = new FileInputStream(file)){
                ByteArrayOutputStream bAOutputStream = new ByteArrayOutputStream();
                int ch;
                while((ch = fis.read() ) != -1){
                    bAOutputStream.write(ch);
                }
                byte[] data = bAOutputStream.toByteArray();
                bAOutputStream.close();

                fileInfo = Base64.encodeBase64String(data);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileInfo;
    }

    @Override public File findFileById(Long fileId) {
        String filePath = fileStorageService.getFilePathById(fileId);
        if (filePath != null && filePath.length() > 0) {
            //判断file 是否存在
            File attachFile =  new File(filePath);
            if (attachFile.exists()) {
                return attachFile;
            }
        }
        return null;
    }

    @Override 
    public String findFileNameById(Long fileId) {
        String filePath = fileStorageService.getFilePathById(fileId);
        if (filePath != null && filePath.length() > 0) {
            return FilenameUtils.getName(filePath);
        }
        return fileServerConfig.getFileDefaultName();
    }

    @Override public void upFileInfo(String fileInfo, String fileName, String parentId, FileUpRsp upRsp) {
        if (fileInfo != null && fileInfo.length() > 0) {
            String filePath = fileServerConfig.getUploadPath() + fileName;
            File attachFile = new File(filePath);
            if (attachFile.exists()) {
                upRsp.setResultCode("UP-001");
                upRsp.setResultMsg(fileName + " : File exist!");
            }
            else {
                try (FileOutputStream fos = new FileOutputStream(attachFile)){
                    if (attachFile.createNewFile()) {
                        fos.write(Base64.decodeBase64(fileInfo));
                        fos.close();
                        
                        saveFileInfo(filePath, fileName, upRsp); 
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                    upRsp.setResultCode("UP-002");
                    upRsp.setResultMsg(e.getMessage());
                }
            }
        }
    }

    private void saveFileInfo(String filePath, String fileName, FileUpRsp upRsp) {
        // 获取fileStorage ID 可以 用redis 
        FileStorageDto fileStorageDto = new FileStorageDto();
        fileStorageDto.setFilePath(filePath);
        fileStorageDto.setFileState("A");
        fileStorageService.addFileStorage(fileStorageDto);
        if (fileStorageDto.getFileId() != null) {
            upRsp.setResultCode("UP-000");
            upRsp.setResultMsg("Success!!!");
            upRsp.setFileName(fileName);
            upRsp.setPathId(fileStorageDto.getFileId());
        }
        else {
            upRsp.setResultCode("UP-003");
            upRsp.setResultMsg("save file error");
            upRsp.setFileName(fileName);
        }
    }

    @Override public void delFileInfo(Long fileId, FileDeleteResp delRsp) {
        String filePath = fileStorageService.getFilePathById(fileId);
        if (filePath != null && filePath.length() > 0) {
            File attachFile = new File(filePath);
            if (attachFile.exists()) {
                if (attachFile.delete()) {
                    fileStorageService.delFileStorageById(fileId);
                    delRsp.setRespCode("DEL-000");
                    delRsp.setRespMsg("Delete success!!");
                }
                else {
                    delRsp.setRespCode("DEL-002");
                    delRsp.setRespMsg("Delete failed!!");
                }
            }
        }
        else {
            delRsp.setRespCode("DEL-001");
            delRsp.setRespMsg("File not exist!!");
        }
    }
}
