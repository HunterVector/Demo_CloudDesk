
$(document).ready(function(){
    query();
    $("#js-fileupload-div").hide();
})

function query() {
    $.post("/portal/file/query",{},function (data, status) {
        fileRender(data.fileInfoList);
        if (data.userInfo) {
            userRender(data.userInfo);
        }
    },"json")
}

function createFolder() {
    var folderName = prompt("请输入文件夹名");
    var parentId = $("#js-nowPath-id").text();
    if (folderName != null) {
        $.post("/portal/file/createFolder",{folderName:folderName,parentId:parentId},function (data, status) {
            if (data.resultCode == "UP-000") {
                data.folderFlag = 'Y';
                fileRenderAppend(data);
            }
        },"json") 
    }
}


function queryFile(fileId) {
    $.post("/portal/file/queryFile",{fileId:fileId},function (data, status) {
        fileRender(data.fileInfoList);
    },"json")
    var id = "#file_" + fileId;
    var folderName = $(id).text();
    
    
    var fileTemplate = "";
    fileTemplate += "<a id=\"path_"+ fileId + "\" class=\"js-reg-folder\" onclick=\"queryPathFile("+ fileId + ")\">"
        + folderName + "</a> \n"
        + "<a> / </a> \n";
    
    $("#js-nowPath-id").text(fileId);
    $("#js-file-path").append(fileTemplate);
}

function queryPathFile(fileId) {
    $.post("/portal/file/queryFile",{fileId:fileId},function (data, status) {
        fileRender(data.fileInfoList);
    },"json")
    var id = "#path_" + fileId;
    
    $("#js-nowPath-id").text(fileId);
    
    $(id).next().nextAll().remove();
    
}


function uploadFile() {
    $("#js-fileupload-div").show();
    $("#fileuploadBtn").off("click");
}

function userRender(data) {
    var userInfo = data;
    
    $("#js-user-name").text(userInfo.userName);
}