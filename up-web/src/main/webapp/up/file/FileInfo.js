function fileRender(data) {
    var fileInfoList = data;
    var fileTemplate = "";
    var folderTemplate = "";
    if (fileInfoList != null && fileInfoList.length > 0) {
        for (var i = 0; i < fileInfoList.length; i++) {
            // var tpl   =  $("#fileInfoTpl").html();

            // // var tpl = require('file/FileInfo.hbs');
            //
            // //原生方法
            // var source = document.getElementById('#fileInfoTpl').innerHTML;
            // //预编译模板
            // var template = Handlebars.compile(source);
            // //模拟json数据
            // // var context = { name: "zhaoshuai", content: "learn Handlebars"};
            // var fileInfo = fileInfoList[i];
            // //匹配json内容
            // var fileTemplate = template(fileInfo);
            // //输入模板
            // // $(body).html(html);
            // $("#fileInfo").html(fileTemplate);

            var fileInfo = fileInfoList[i];
            if (fileInfo.folderFlag != "Y") {
                fileTemplate += "<div class=\"js-file-item\">\n"
                    + "<a id=\"file_"+ fileInfo.fileId + "\" class=\"js-reg-1\">"
                    + fileInfo.fileName + "</a> \n"
                    + "<button class =\"js-button-del\" id=\"fileDel_"+ fileInfo.fileId + "\" onclick=\"deleteFile("+ fileInfo.fileId +")\" >delete</button> \n"
                    + "<button class =\"js-button-down\" id=\"fileDown_"+ fileInfo.fileId + "\" onclick=\"downloadFile("+ fileInfo.fileId +")\" >download</button> \n"
                    +" </div> \n";
            }
            else {
                folderTemplate += "<div class=\"js-folder-item\">\n"
                    + "<a id=\"file_"+ fileInfo.fileId + "\" class=\"js-reg-folder\" onclick=\"queryFile("+ fileInfo.fileId + ")\">"
                    + fileInfo.fileName + "</a> \n"
                    + "<button class =\"js-button-del\" id=\"fileDel_"+ fileInfo.fileId + "\" onclick=\"deleteFile("+ fileInfo.fileId +")\" >delete</button> \n"
                    +" </div> \n";
            }
        }
    }
    $("#fileInfo").html(folderTemplate + fileTemplate);
}

function fileRenderAppend(data) {
    var fileInfo = data;
    var fileTemplate = "";
    var folderTemplate = "";
    
    if (fileInfo.folderFlag != "Y") {
        fileTemplate += "<div class=\"js-file-item\">\n"
            + "<a id=\"file_"+ fileInfo.fileId + "\" class=\"js-reg-1\">"
            + fileInfo.fileName + "</a> \n"
            + "<button class =\"js-button-del\" id=\"fileDel_"+ fileInfo.fileId + "\" onclick=\"deleteFile("+ fileInfo.fileId +")\" >delete</button> \n"
            + "<button class =\"js-button-down\" id=\"fileDown_"+ fileInfo.fileId + "\" onclick=\"downloadFile("+ fileInfo.fileId +")\" >download</button> \n"
            +" </div> \n";
    }
    else {
        folderTemplate += "<div class=\"js-folder-item\">\n"
            + "<a id=\"file_"+ fileInfo.fileId + "\" class=\"js-reg-folder\" onclick=\"queryFile("+ fileInfo.fileId + ")\">"
            + fileInfo.fileName + "</a> \n"
            + "<button class =\"js-button-del\" id=\"fileDel_"+ fileInfo.fileId + "\" onclick=\"deleteFile("+ fileInfo.fileId +")\" >delete</button> \n"
            +" </div> \n";
    }
    
    if ($(".js-folder-item").length == 0) {
        if ($(".js-file-item").length == 0) {
            //什么都没有，则直接新增
            $("#fileInfo").html(folderTemplate);
        }
        else {
            //没有文件夹，有文件，文件夹插入到文件最前面
            $(".js-file-item").first().before(folderTemplate);
        }
    }
    else {
        //有文件夹，文件夹插入文件夹最后面
        $(".js-folder-item").last().after(folderTemplate);
    }

    if ($(".js-file-item").length == 0) {
        if ($(".js-folder-item").length == 0) {
            //什么都没有，则直接新增
            $("#fileInfo").html(fileTemplate);
        }
        else {
            //没有文件，有文件夹，文件插入到文件夹最后面
            $(".js-folder-item").last().after(fileTemplate);
        }
    }
    else {
        //有文件，文件插入到文件最后面
        $(".js-file-item").last().after(fileTemplate);
    }
}

function deleteFile(fileId) {
    var nowPathId = $("#js-nowPath-id").text();
    $.ajax({
        type: "POST",
        url: "/portal/file/delete",
        data: {fileId:fileId},
        dataType: "json",
        success: function(data){
            if (data.respCode == "DEL-000") {
                alert(data.respMsg);
                queryPathFile(nowPathId);
            }
            else {
                alert(data.respMsg);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

$(function() {
    $("#ifr").hide();
    $("#file_choose").hide();

    $("#chooseFile").on("click", function() {
        $("#uploadData").click();
    });

    $('#uploadData').fileupload({
        url : '/portal/file/upload',//请求发送的目标地址
        Type : 'POST',//请求方式 ，可以选择POST，PUT或者PATCH,默认POST
        dataType : 'json',//服务器返回的数据类型
        autoUpload : false,
        // acceptFileTypes : /(gif|jpe?g|png)$/i,//验证图片格式
        maxNumberOfFiles : 1,//最大上传文件数目
        maxFileSize : 1000000, // 文件上限1MB
        minFileSize : 100,//文件下限  100b
        messages : {//文件错误信息
            acceptFileTypes : '文件类型不匹配',
            maxFileSize : '文件过大',
            minFileSize : '文件过小'
        }
    })

    $('#uploadData').bind('fileuploadsubmit', function (e, data) {
        data.formData = {parentId: $("#js-nowPath-id").text()};  //如果需要额外添加参数可以在这里添加
    })
        //图片添加完成后触发的事件
        .on("fileuploadadd", function(e, data) {
            //validate(data.files[0])这里也可以手动来验证文件格式和大小

            //隐藏或显示页面元素
            $('#progress .bar').css(
                'width', '0%'
            );
            $('#progress').hide();
            $("#chooseFile").hide();
            $("#uploadFile").show();
            $("#rechooseFile").show();

            //获取图片路径并显示
            if (validate(data.files[0])) {
                var url = getUrl(data.files[0]);
                $("#ifr").attr("src", url);
                $("#ifr").show();
            }
            else {
                var fileName = data.files[0].name;
                $("#file_choose").val(fileName);
                $("#file_choose").show();
            }

            //绑定开始上传事件
            $('#uploadFile').click(function() {
                $("#uploadFile").hide();
                jqXHR = data.submit();
                //解绑，防止重复执行
                $("#uploadFile").off("click");
            })

            //绑定点击重选事件
            $("#rechooseFile").click(function(){
                $("#uploadData").click();
                //解绑，防止重复执行
                $("#rechooseFile").off("click");
            })
        })
        //当一个单独的文件处理队列结束触发(验证文件格式和大小)
        .on("fileuploadprocessalways", function(e, data) {
            //获取文件
            file = data.files[0];
            //获取错误信息
            if (file.error) {
                console.log(file.error);
                $("#uploadFile").hide();
            }
        })
        //显示上传进度条
        .on("fileuploadprogressall", function(e, data) {
            $('#progress').show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress').css(
                'width','15%'
            );
            $('#progress .bar').css(
                'width',progress + '%'
            );
        })
        //上传请求失败时触发的回调函数
        .on("fileuploadfail", function(e, data) {
            alert("fileupload fail: " + data.errorThrown);
            console.log(data.errorThrown);
        })
        //上传请求成功时触发的回调函数
        .on("fileuploaddone", function(e, data) {
            alert(data.result.resultMsg);
            if (data.result.resultCode == "UP-000") {
                fileRenderAppend(data.result);
            }

        })
        //上传请求结束后，不管成功，错误或者中止都会被触发
        .on("fileuploadalways", function(e, data) {

        })
    
    
});

//手动验证
function validate(file) {
    //获取文件名称
    var fileName = file.name;
    //验证图片格式
    // if (!/.(gif|jpg|jpeg|png|gif|jpg|png)$/.test(fileName)) {
    //     console.log("文件格式不正确");
    //     return true;
    // }
    //验证excell表格式
    /*  if(!/.(xls|xlsx)$/.test(fileName)){
         alert("文件格式不正确");
         return true;
     } */

    //获取文件大小
    var fileSize = file.size;
    if (fileSize > 1024 * 1024) {
        alert("文件不得大于一兆")
        return true;
    }
    return false;
}

//获取文件地址
function getUrl(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}


function downloadFile(fileId) {
    var fileName = "";

    (function ($) {
        var id = "#file_" + fileId;
        fileName = $(id).text();
    })(jQuery)

    if (fileName == "") {
        alert ("file name is invalid");
        return;
    }

    var url = "/portal/file/downloadPre";
    var xhr = new XMLHttpRequest();

    var data = {};
    data.fileId = fileId;
    xhr.open('POST', url, true);//get请求，请求地址，是否异步
    // xhr.setRequestHeader(header, token);

    xhr.setRequestHeader("Content-Type", "application/json;");
    // 发送ajax请求
    xhr.send(JSON.stringify(data));
    xhr.responseType = 'arraybuffer';
    xhr.onload = function () {// 请求完成处理函数
        if (this.status === 200) {
            var blob = new Blob([xhr.response], {type: 'application/pdf'});
            var a = document.createElement('a');
            a.download = fileName;
            a.href=window.URL.createObjectURL(blob);
            a.click();
        }
        else {
            reader = new FileReader();
            reader.onload = function () {
                alert(reader.result)
            };
            reader.readAsText(new Blob([xhr.response]), 'utf-8');
        }
    };

    // (function ($) {
    //     $("#fileInfo").append("<iframe width='100%' height='95%' style='display: none;' id='ifr1'></iframe>")
    //
    //     var src ="/demomvc/demo/download?fileId=" + fileId;
    //     $("#ifr1").attr('src', src);
    //
    // })(jQuery)
}