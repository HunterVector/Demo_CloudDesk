/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.up.web.service;/**
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.hunter.demo.cd.up.model.FileInfoDto;
import com.hunter.demo.cd.up.model.UserFileDto;
import com.hunter.demo.cd.up.model.UserInfoDto;
import com.hunter.demo.cd.up.param.FileDeleteResp;
import com.hunter.demo.cd.up.param.FileDownReq;
import com.hunter.demo.cd.up.param.FileUpRsp;

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

public interface IWebUserFileService {
    List<FileInfoDto> buildFileList(UserInfoDto user);

    FileUpRsp uploadFile(MultipartFile file, String parentId, UserInfoDto userInfoDto) throws IOException;

    FileUpRsp createFolder(String folderName, String parentId, UserInfoDto userInfoDto);

    FileDeleteResp deleteFile(Long fileId, UserInfoDto user, FileDeleteResp delRsp);

    List<FileInfoDto> queryFile(Long fileId);

    ResponseEntity<?> downloadFile(HttpServletResponse response, FileDownReq request);
}
