//登录请求
function login() {
    post('http://localhost:8080/api/auth/login',  {
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

//发送验证码
function askVerifyCode() {
    get('http://localhost:8080/api/auth/verify-code', {
        email: $("#input-email").val()
    }, function (data) {
        //如果注册成功，直接跳转到登陆页面
        if (data.code === 200) {
            alert(data.reason)
        }
    })
}

//


//退出登录
function logout() {
    get('http://localhost:8080/api/auth/logout',{}, function (data) {
        if (data.code === 200) {
            window.location = "login.html"
        }
    })
}

//登录获取用户信息
function initUserInfo() {
    get('http://localhost:8080/api/user/info',{}, function (data) {
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

