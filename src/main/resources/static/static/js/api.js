//登录请求
function login() {
    post('http://192.168.89.132:8080/api/auth/login',  {
        username: $("#username").val(),
        password: $("#password").val()
    }, function (data) {
        if (data.code === 200) {
            window.location = "index.html"
        } else {
            alert(data.reason)
        }
    })
}

//注册功能
function register(){
    post('http://192.168.89.132:8080/api/auth/register',{
        username: $("#username").val(),
        email: $("#input-email").val(),
        password: $("#password").val(),
        verify: $("#verify").val()
    },function(data){
        if(data.code === 200){
            window.location="login.html"
        }else{
            alert(data.reason)
        }
    })
}
//发送验证码
function askVerifyCode() {
    get('http://192.168.89.132:8080/api/auth/verify-code', {
        email: $("#input-email").val()
    }, function (data) {
        //如果注册成功，直接跳转到登陆页面
        if (data.code === 200) {
            alert(data.reason)
        }
    })
}

//退出登录
function logout() {
    get('http://192.168.89.132:8080/api/auth/logout',{}, function (data) {
        if (data.code === 200) {
            window.location = "login.html"
        }
    })
}

//登录获取用户信息
function initUserInfo() {
    get('http://192.168.89.132:8080/api/user/info',{}, function (data) {
        if (data.code === 200) {
            $("#profile-name").text(data.data.username)
        } else {
            window.location = 'login.html'
        }
    })
}

//封装get和post请求方法
function get(url, data, success) {
    $.ajax({
        type: "get",
        url: url,
        async: true,
        data: data,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function post(url, data, success) {
    $.ajax({
        type: "post",
        url: url,
        async: true,
        data: data,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

