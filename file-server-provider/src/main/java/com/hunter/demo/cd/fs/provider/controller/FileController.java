/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hunter.demo.cd.fs.provider.controller;/**
 * <Description> <br>
 *
 * @author zheng.yangyang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate <br>
 * @see <br>
 * @since V8.0<br>
 */

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hunter.demo.cd.fs.common.param.FileDeleteResp;
import com.hunter.demo.cd.fs.common.param.FileDownReq;
import com.hunter.demo.cd.fs.common.param.FileDownRsp;
import com.hunter.demo.cd.fs.common.param.FileUpReq;
import com.hunter.demo.cd.fs.common.param.FileUpRsp;
import com.hunter.demo.cd.fs.provider.service.IFileUpDownService;

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
@RestController
public class FileController {
    
    @Resource
    private IFileUpDownService fileUpDownService;
    
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public @ResponseBody FileDownRsp testDownloadProv(@RequestBody FileDownReq req) {
        // service调⽤
        FileDownRsp testResp = new FileDownRsp();
        String fileInfo = fileUpDownService.buildFileInfo(fileUpDownService.findFileById(req.getFileId()));
        if (fileInfo != null && fileInfo.length() > 0) {
            testResp.setDocInfo(fileInfo);
            testResp.setFileName(fileUpDownService.findFileNameById(req.getFileId()));
            testResp.setResultCode("DOWN-000");
            testResp.setResultMsg("success");
        }
        else {
            testResp.setResultCode("DOWN-001");
            if (fileUpDownService.findFileNameById(req.getFileId()) != null) {
                testResp.setResultMsg("can not find file:" + fileUpDownService.findFileNameById(req.getFileId()));
            }
            else {
                testResp.setResultMsg("can not find file");
            }
        }
        return testResp;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody FileUpRsp testUploadProv(@RequestBody FileUpReq req) {
        // service调⽤
        FileUpRsp upRsp = new FileUpRsp();
        fileUpDownService.upFileInfo(req.getFileInfo(), req.getFileName(), req.getParentId(), upRsp);
        return upRsp;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody FileDeleteResp testDeleteProv(@RequestParam("fileId") Long fileId) {
        // service调⽤
        FileDeleteResp delRsp = new FileDeleteResp();
        fileUpDownService.delFileInfo(fileId, delRsp);
        return delRsp;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody String testHelloWorld() {
        return fileUpDownService.hello();
    }

    @RequestMapping(value = "/helloWorld1", method = RequestMethod.GET)
    public @ResponseBody String testHelloWorld1() {
        return "Hello World1111111";
    }
}
