$(document).ready(function () {
    var files = [];
    $("#myfile").change(function () {
        var file = $("#myfile")[0].files[0];
        files.push(file);
        var maxSize = 1024;//最大允许上传1024Kb，即：1M
        if ($("#myfile").val() == "" || $("#myfile").val() == null) {
            alarmChange("请先上传图片");
            return false;
        }
        var reg = /\.(jpg|jpeg|png)/;
        if (!reg.test($("#myfile").val())) {
            alarmChange("缩略图格式必须是[jpeg,jpg,png]");

        } else {
            var file = $("#myfile")[0].files[0];
            var size = file.size;
            if (size > maxSize * 1024) {
                alarmChange("图片大小不能超过1M");
            } else {
                //显示图片预览
                var url = window.URL.createObjectURL(file);
                $("#myimage").attr("src", url);
            }
        }

        event.target.value = '';//解决不能重复上传同一个文件bug

    });
    //jQuery的Ajax异步上传配图照片
    $("#submit_form_btn").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);

        // 添加文件对象到表单
        if (files.length > 0) {
            for (var i = 0; i < files.length; i++) {
                formData.append("yida", files[i]);
            }
            // 添加富文本内容到表单
            var content = editor.html();
            formData.append("content", content);
            files = [];
            //如果没有填完所有信息不能进行提交
            submitMethod();
            if (alarm == false) {
                return;
            }
            $.ajax({
                url: "updateFocusMessageColumn",
                type: 'POST',
                async: true,
                cache: false,//不使用缓存
                data: formData,
                processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
                contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
                success: function (result, status, xhr) {//请求成功回调
                    if (status == "success") {
                        var str = "发布景点成功,result=" + result;
                        alarmChange(str);
                        //触发a标签返回景点列表
                        var link = document.getElementById("jiaodianxinxiliebiao")
                        link.click();
                    }
                },
                error: function (xhr) {//请求失败回调
                    var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                    alarmChange(str)
                }
            });
        }else {
            debugger;
            submitMethod();
            if (alarm == false) {
                return;
            }
            //获取表单里面的数据 用数组保存
            var id = document.getElementById("id").value;
            var tile = document.getElementById("input_message").value;
            var arrs=[];
            arrs.push(tile);
            // 添加富文本内容到表单
            var content = editor.html();
            arrs.push(content);
            arrs.push(id);
            $.ajax({
                url: "updateFocusMessageColumns",
                type: 'POST',
                async: false,
                dataType:"json",
                data:{"arr":arrs},
                traditional:true,//传数组必填
                success: function (result, status, xhr) {//请求成功回调
                    if (status == "success") {
                        if(result.code==400){
                            var str = "更新景点失败,result=" + result;
                            alarmChange(str);
                            return;
                        }
                        if (result.code==200){
                            var str = "更新景点成功,result=" + result;
                            alarmChange(str);
                            //触发a标签返回景点列表
                            var link = document.getElementById("jiaodianxinxiliebiao")
                            link.click();
                        }


                    }
                },
                error: function (xhr) {//请求失败回调
                    var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                    alarmChange(str)
                }
            });
        }

    });






});

function message() {
    var image = document.getElementById("myimage");
    var span = document.getElementById("litimg_del_btn");
    var name = image.attributes.src.value;
    if (name == "image/icon.jpg") {
        span.style.opacity = "0.0";
    } else {
        span.style.opacity = "0.6";
    }
}

function leave() {
    var span = document.getElementById("litimg_del_btn");
    span.style.opacity = "0.0";
}

function deletePhoto() {
    var image = document.getElementById("myimage");
    if (name == "image/icon.jpg") {
        span.style.opacity = "0.0";
    } else {
        image.attributes.src.value = "image/icon.jpg";
    }
}

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
function submitMethod() {
    var tile = document.getElementById("input_message");
    var image = document.getElementById("myimage");//效验有没有缩略图

    alarm = false;
    if (tile.value == "") {
        alarmChange("请输入消息标题");
        return;
    }
    var content = editor.html();
    if (content == "" || content == null) {
        alarmChange("消息内容不得为空");
        return;
    }

    if (image.outerHTML.includes("icon.jpg")) {
        alarmChange("请先上传景缩略图");
        return;
    }
    alarm = true;
}


