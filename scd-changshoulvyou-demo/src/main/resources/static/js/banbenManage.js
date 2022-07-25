//版本管理
$(document).ready(function () {
    //jQuery的Ajax异步上传缩略图照片
    $("#submit_form_btn").click(function() {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        //如果没有填完所有信息不能进行提交
        publicMethod();
        if(alarm==false){
            return;
        }

        $.ajax({
            url: "updateVersionManage",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
            success:function(result,status,xhr) {//请求成功回调
                if(status == "success") {
                    var str = "提交版本管理成功";
                    alarmChange(str);
                    //触发a标签返回景点列表
                    var link= document.getElementById("VersionMange")
                    link.click();
                }
            },
            error:function(xhr) {//请求失败回调
                var str = "ERROR:"+xhr.statusText+"---"+xhr.status;
                alarmChange(str)
            }
        });
    });
});

function alarmChange(String) {
    var alarm = document.getElementById("user-12");
    alarm.style.display = "block";
    alarm.innerText = String;
    missMessage();
}

function missMessage() {
    $('#user-12').fadeOut(3000);
}

//JS校验
var alarm = false;//全局变量执行判断是否为真然后提交
function publicMethod() {
    var edition= document.getElementById("edition");
    var updateAddress= document.getElementById("updateAddress");
    var explain= document.getElementById("explain");

    if (edition.value == "") {
        alarmChange("版本号不能为空");
        return;
    }else if (!edition.value == "") {
        var reg = /^[1-9](.)\d(.)\d$/;
        if (reg.test(edition.value) == false) {
            alarmChange("版本号不合法");
            return;
        }
    }

    if (updateAddress.value == "" ) {
        alarmChange("更新地址不得为空");
        return ;
    }else if (!updateAddress.value == "") {
        var reg = /^(www)(.)([a-zA-Z0-9]{1,}(.)(com))$/;
        if (reg.test(updateAddress.value) == false) {
            alarmChange("更新地址不合法");
            return;
        }
    }
    if (explain.value == "" ) {
        alarmChange("更新内容不得为空");
        return ;
    }
    alarm = true;
}
