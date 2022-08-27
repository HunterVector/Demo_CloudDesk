/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.web.service.impl;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.hunter.demo.cd.up.web.config.UpPortalConfig;
import com.hunter.demo.cd.up.helper.UserFileHelper;
import com.hunter.demo.cd.up.model.FileInfoDto;
import com.hunter.demo.cd.up.model.UserFileDto;
import com.hunter.demo.cd.up.model.UserInfoDto;
import com.hunter.demo.cd.up.param.FileDeleteResp;
import com.hunter.demo.cd.up.param.FileDownReq;
import com.hunter.demo.cd.up.param.FileDownRsp;
import com.hunter.demo.cd.up.param.FileUpReq;
import com.hunter.demo.cd.up.param.FileUpRsp;
import com.hunter.demo.cd.up.web.service.IWebUserFileService;
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

@Service
public class WebUserFileService implements IWebUserFileService {
    @Autowired
    private UpPortalConfig downloadConfig;

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    @Qualifier("userServiceMock")
//    private UserService userService;

    @Autowired
    @Qualifier("userFileServiceMock")
    private UserFileService fileService;
    
    @Override public List<FileInfoDto> buildFileList(UserInfoDto user) {
        List<UserFileDto> list = fileService.getUserFileById(user.getUserId());
        List<FileInfoDto> fileList = new ArrayList<FileInfoDto>();
        if (list != null && list.size() > 0) {
            for (UserFileDto userFileDto : list) {
                FileInfoDto dto = fileService.getFileInfoById(userFileDto.getFileId());
                if (dto != null && dto.getParentFileId() == null) {
                    fileList.add(dto);
                }
            }

        }
        return fileList;
    }

    @Override
    public FileUpRsp uploadFile(MultipartFile file, String parentId, UserInfoDto userInfoDto) throws IOException {
        //        String path = downloadConfig.getUploadPath();
        String fileName = "defaultFileName";
        if (file == null) {
            FileUpRsp upRsp = new FileUpRsp();
            upRsp.setResultCode("UP-003");
            upRsp.setResultMsg(" File error!");
            return upRsp;
        }
        if (file.getOriginalFilename() != null) {
            fileName = file.getOriginalFilename();
        }
        //        path = path + fileName;
        //        File fileNew = new File(path);
        //        if (!fileNew.exists()) {
        //            try {
        //                // 创建新文件
        //                fileNew.createNewFile();
        //            } catch (IOException e) {
        //                System.out.println("创建新文件时出现了错误。。。");
        //                e.printStackTrace();
        //            }
        //        }
        //        file.transferTo(fileNew);

        String fileInfo = buildFileInfo(file);
        //        fileNew.delete();
        FileUpRsp resp = uploadFileToStorage(fileInfo, fileName, parentId);

        if ("UP-000".equals(resp.getResultCode())) {
            FileInfoDto dto = uploadFileInfo(fileName, parentId, resp.getPathId());
            resp.setFileId(dto.getFileId());
            UserFileDto userFileDto = UserFileHelper.buildUserFile(userInfoDto, dto.getFileId());
            fileService.addUserFile(userFileDto);
        }
        return resp;
    }

    @Override public FileUpRsp createFolder(String folderName, String parentId, UserInfoDto userInfoDto) {
        FileInfoDto dto = uploadFileInfo(folderName, parentId, null);
        FileUpRsp resp = new FileUpRsp();
        if (dto != null) {
            UserFileDto userFileDto = UserFileHelper.buildUserFile(userInfoDto, dto.getFileId());
            fileService.addUserFile(userFileDto);
            
            resp.setFileId(dto.getFileId());
            resp.setFileName(dto.getFileName());
            resp.setResultCode("UP-000");
            resp.setResultMsg("Success!!!");
        }
        else {
            resp.setResultCode("UP-011");
            resp.setResultMsg("Create folder failed!!!");
        }
        return resp;
    }

    private FileInfoDto uploadFileInfo(String fileName, String parentId, Long pathId) {
        Long parentFileId = null;
        if (parentId != null && !"0".equals(parentId)) {
            parentFileId = Long.parseLong(parentId);
        }
        FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setFileState("A");
        fileInfoDto.setParentFileId(parentFileId);
        fileInfoDto.setFileName(fileName);
        fileInfoDto.setPathId(pathId);
        if (pathId != null) {
            fileInfoDto.setFolderFlag("N");
        }
        else {
            fileInfoDto.setFolderFlag("Y");
        }
        fileService.addFileInfo(fileInfoDto);

        return fileInfoDto;
    }

    private String buildFileInfo(MultipartFile file) {
        String test = "";
        if (file != null) {
            try {
                byte[] bt = file.getBytes();
                test = Base64.encodeBase64String(bt);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return test;
    }

    private FileUpRsp uploadFileToStorage(String fileInfo, String fileName, String parentId) {

        String url = downloadConfig.getDownloadUrl();
        url = url + "/upload";
        FileUpReq convertObject = new FileUpReq();
        convertObject.setFileInfo(fileInfo);
        convertObject.setFileName(fileName);
        convertObject.setParentId(parentId);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<FileUpReq> formEntity = new HttpEntity<FileUpReq>(convertObject, headers);
        return restTemplate.postForObject(url, formEntity, FileUpRsp.class);
    }

    @Override public FileDeleteResp deleteFile(Long fileId, UserInfoDto user, FileDeleteResp resp) {
        FileInfoDto dto = fileService.getFileInfoById(fileId);
        if ("Y".equals(dto.getFolderFlag())) {
            List<FileInfoDto> list = fileService.queryFile(fileId);
            if (list != null && list.size() > 0) {
                for (FileInfoDto dto1 : list) {
                    deleteFile(dto1.getFileId(), user, resp);
                    if (!"DEL-000".equals(resp.getRespCode()))  {
                        return resp;
                    }
                }
            }
            fileService.delFileById(fileId);
            fileService.delUserFileById(user.getUserId(), dto.getFileId());
            resp.setRespCode("DEL-000");
            resp.setRespMsg("Delete success!!");
        }
        else {
            FileDeleteResp delStorageResp = deleteFileStorage(dto.getPathId());
            if ("DEL-000".equals(delStorageResp.getRespCode())) {
                fileService.delUserFileById(user.getUserId(), dto.getFileId());
                fileService.delFileById(fileId);
                resp.setRespCode("DEL-000");
                resp.setRespMsg("Delete success!!");
            }
            else {
                resp.setRespCode(delStorageResp.getRespCode());
                resp.setRespMsg(delStorageResp.getRespMsg());
            }
        }
        return resp;
    }

    @Transactional()
    public FileDeleteResp deleteFileStorage(Long fileId) {
        String url = downloadConfig.getDownloadUrl();
        url = url + "/delete?fileId={fileId}";
        Map<String, Long> map = new HashMap<>();
        map.put("fileId", fileId);
        return restTemplate.getForObject(url, FileDeleteResp.class, map);
    }
    
    @Override
    public List<FileInfoDto> queryFile(Long fileId) {
//        List<FileInfoDto> list = fileService.queryFile(fileId);
        //        List<FileInfoDto> list = new ArrayList<>();
        //        String url = downloadConfig.getDownloadUrl();
        ////        HttpHeaders headers = new HttpHeaders();
        ////        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        ////        headers.setContentType(type);
        ////        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        ////        HttpEntity<TestUpReq> formEntity = new HttpEntity<TestUpReq>(convertObject, headers);
        //
        //        url = url + "/queryByParent?fileId={fileId}";
        //        Map<String, Long> map = new HashMap<>();
        //        map.put("fileId", fileId);
        //        
        //        TestQryResult resp =  restTemplate.getForObject(url, TestQryResult.class, map);
        //        if (resp != null) {
        //            list = resp.getFileInfoList();
        //        }
        return fileService.queryFile(fileId);
    }

    public ResponseEntity<?> downloadFile(HttpServletResponse response, FileDownReq request) {
        String url = downloadConfig.getDownloadUrl();
        url = url + "/download";
        Long fileId = request.getFileId();
        FileInfoDto dto = fileService.getFileInfoById(fileId);
        if (dto == null || dto.getPathId() == null) {
            //            response.sendRedirect("demo/error.jsp");
            return new ResponseEntity<String>("file not exist!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        FileDownReq convertObject = new FileDownReq();
        convertObject.setParam1("1");
        convertObject.setFileId(dto.getPathId());
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<FileDownReq> formEntity = new HttpEntity<FileDownReq>(convertObject, headers);
        FileDownRsp resp = restTemplate.postForObject(url, formEntity, FileDownRsp.class);
        if (!"DOWN-000".equals(resp.getResultCode())) {
            //            response.sendRedirect("demo/error.jsp");
            return new ResponseEntity<String>(resp.getResultMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String fileInfo = resp.getDocInfo();
        if (null != fileInfo && fileInfo.length() > 0) {
            response.setContentType("APPLICATION/OCTET-STREAM;charset=" + "UTF-8");
            response.setHeader("Content-Disposition", safeHttpHeader("attachment; filename=\"" + resp.getFileName() + "\""));

            OutputStream bos = null;
            try {
                bos = new BufferedOutputStream(response.getOutputStream());
                bos.write(Base64.decodeBase64(fileInfo));
                bos.flush();
                bos.close();
                return new ResponseEntity<String>(
                    "", HttpStatus.valueOf(200));
            }
            catch (IOException e) {
                //                logger.error("Download file error!", e);
            }
            finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new ResponseEntity<String>("fault download file", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String safeHttpHeader(String value) {
        String result = "";
        if (value != null) {
            char[] chars = value.toCharArray();
            StringBuilder buffer = new StringBuilder(chars.length);
            for (int i = 0; i < chars.length; i++) {
                switch (chars[i]) {
                    case '\r':
                        buffer.append('%');
                        buffer.append('0');
                        buffer.append('D');
                        break;
                    case '\n':
                        buffer.append('%');
                        buffer.append('0');
                        buffer.append('A');
                        break;
                    default:
                        buffer.append(chars[i]);
                        break;
                }
            }
            result = buffer.toString();
        }
        return result;
    }
}
