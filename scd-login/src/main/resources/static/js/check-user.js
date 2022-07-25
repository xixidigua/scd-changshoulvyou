function checkUser() {//效验用户信息
    //检验用户名不能为空
    var name = document.getElementsByName("name")[0].value;
    if (name == null || name == "") {
        //window.alert("请输入管理员名称");
        var html = "请输入管理员名称"
        document.getElementById('mytest').innerHTML = html;
        return false;//无法提交
    }
    //效验用户名密码
    var password = document.getElementsByName("password")[0].value;
    if (password == null || password == "") {
        // window.alert("请输入密码");
        var html = "请输入密码"
        document.getElementById('mytest').innerHTML = html;
        return false;//无法提交
    } else if (password != "") {
        var reg = /^[0-9a-zA-Z]{6}$/;
        if (reg.test(password) == false) {
            //   window.alert("输入密码格式不对");
            var html = "入密码格式不对"
          var c=  document.getElementById('mytest').innerHTML = html;
            return false;
        }
    }
   /* if (name !== "管理员" || password !=="111111") {
        // window.alert("登录失败，该用户不存在，请检查登录信息后登录！");
        var html = "登录失败，该用户不存在，请检查登录信息后登录！"
        document.getElementById('mytest').innerHTML = html;
        return false;
    }*/

    var md5Pwd=md5(password);//md5加密
    console.log("原始密码="+password+",md5密码="+md5Pwd);
    document.getElementsByName("password")[0].value=md5Pwd;
    //向后台发送ajax数据
    var url = $("#form").attr("action");
    var formData = $("#form").serialize();// 获取表单数据
    $.ajax({
        url: url,
        type: 'POST',
        async: true,
        cache: false,//不使用缓存
        data: formData,
        success: function (result, status, xhr) {//请求成功回调
            if (status == "success") {
                var obj = JSON.parse(result);
             var message= obj.message;
             var Id=obj.user;
             if (message=="认证成功"){
                 debugger;
                 //认证通过跳转到其他的微服务
                 var url="http://localhost:8090/index?id="+Id;
                 window.location.replace(url);
             }else{
                 var html = message
                 var c=  document.getElementById('mytest').innerHTML = html;
                  return false;
             }
            }
        },
        error: function (xhr) {//请求失败回调
            var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
            var html = "请求存在异常"
            var c=  document.getElementById('mytest').innerHTML = html;
        }
    });
    return  true;

}

//实现跳转功能
/*
$(document).ready(function () {
    $('.submit').click(function () {
        var name = $('#userId').prop('value');
        var pwd = $('#pwdId').prop('value');
        if (name == "管理员" && pwd == "111111") {
            var url = $(this).data('content');
            $("#userLogin").load(url);
            //修改网页标题
            $('title').html('长寿旅游管理后台');
        } else {
            checkUser();
        }
    });
});*/
