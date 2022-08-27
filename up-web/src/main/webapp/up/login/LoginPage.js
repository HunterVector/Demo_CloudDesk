function login() {
    var userCode = $("#username").val();
    var userPass = $("#password").val();
    if (userCode == null || userCode == "") {
        alert("please input user info");
    }
    else if (userPass == null || userPass == "") {
        alert("please input password");
    }
    else {
        $.post("/portal/user/login",{userCode:userCode,userPass:userPass},function (data, status) {
            if (data.respCode == "LOGIN-000") {
                window.location.href = "up/Index.html";
            }
            else {
                alert("Login failed: " + data.respMsg);
            }
        },"json")
    }
}

function registerSubmit() {
    var userCode = $("#js-register-username").val();
    var userPass = $("#js-register-userpass").val();
    var userName = $("#js-register-name").val();
    var userPassCopy = $("#js-register-userpass-duplicate").val();
    if (userCode == null || userCode == "") {
        alert("用户名不能为空");
    }
    else if (userPass == null || userPass == "") {
        alert("请输入密码");
    }
    else if (userPassCopy == null || userPassCopy == "") {
        alert("请输入重复密码");
    }
    else if (userName == null || userName == "") {
        alert("请输入姓名");
    }
    else if (userPass != userPassCopy) {
        alert("密码不相同");
    }
    else {
        $.post("/portal/user/addUser",{userCode:userCode,userPass:userPass,userName:userName},function (data, status) {
            if (data.respCode == "AU-000") {
                alert(data.respMsg);
                window.location.href = "/portal/Login.html";
            }
            else {
                alert("Add user failed: " + data.respMsg);
            }
        },"json")
    }
}