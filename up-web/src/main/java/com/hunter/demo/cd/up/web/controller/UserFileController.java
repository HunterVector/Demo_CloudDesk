/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.web.controller;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hunter.demo.cd.up.model.FileInfoDto;
import com.hunter.demo.cd.up.model.UserInfoDto;
import com.hunter.demo.cd.up.param.FileDeleteResp;
import com.hunter.demo.cd.up.param.FileDownReq;
import com.hunter.demo.cd.up.param.FileQryResult;
import com.hunter.demo.cd.up.param.FileUpRsp;
import com.hunter.demo.cd.up.web.service.IWebUserFileService;

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

@Controller
@RequestMapping("/file")
public class UserFileController {
    
    @Autowired
    private IWebUserFileService userFileService;
    

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public FileQryResult qryFile(HttpSession httpSession) {
        UserInfoDto user = (UserInfoDto) httpSession.getAttribute("userInfo");
        List<FileInfoDto> list = userFileService.buildFileList(user);
        FileQryResult result = new FileQryResult();
        if (list != null && list.size() > 0) {
            result.setResult("Y");
            result.setFileInfoList(list);
        }
        else {
            result.setResult("N");
        }
        result.setUserInfo(user);
        return result;

    }

    @RequestMapping(value = "/upload")
    @Transactional()
    @ResponseBody
    public FileUpRsp fileUpload(@RequestParam("uploadData") MultipartFile testFile,
        HttpSession httpSession, HttpServletRequest request) throws IOException {
        UserInfoDto userInfoDto = (UserInfoDto) httpSession.getAttribute("userInfo");
        String parentId = request.getParameter("parentId");
        return userFileService.uploadFile(testFile, parentId, userInfoDto);
    }

    @RequestMapping(value = "/createFolder", method = RequestMethod.POST)
    @ResponseBody
    public FileUpRsp testCreateFolder(HttpSession httpSession, HttpServletRequest request) {
        UserInfoDto userInfoDto = (UserInfoDto) httpSession.getAttribute("userInfo");
        String parentId = request.getParameter("parentId");
        String folderName = request.getParameter("folderName");
        return userFileService.createFolder(folderName, parentId, userInfoDto);
    }

    @RequestMapping(value = "/delete")
    @Transactional()
    public @ResponseBody FileDeleteResp testFileDelete(@RequestParam("fileId") Long fileId, HttpSession httpSession){
        UserInfoDto user = (UserInfoDto) httpSession.getAttribute("userInfo");
        FileDeleteResp delRsp = new FileDeleteResp();
        userFileService.deleteFile(fileId, user, delRsp);
        return delRsp;

    }

    @RequestMapping(value = "/queryFile")
    @Transactional()
    public @ResponseBody FileQryResult testQueryFile(@RequestParam("fileId") Long fileId, HttpSession httpSession){
        if (0L == fileId) {
            return qryFile(httpSession);
        }

        FileQryResult result = new FileQryResult();
        List<FileInfoDto> list = userFileService.queryFile(fileId);

        if (list != null && list.size() > 0) {
            result.setResult("Y");
            result.setFileInfoList(list);
        }
        else {
            result.setResult("N");
        }
        return result;

    }

    @RequestMapping(value = "/downloadPre", method = RequestMethod.POST)
    public ResponseEntity<?> testDownloadPre(HttpServletResponse response, @RequestBody FileDownReq request) {

        return userFileService.downloadFile(response, request);
    }
}
