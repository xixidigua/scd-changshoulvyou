$(document).ready(function () {
    var files = [];
    $("#myfile").change(function () {
        var file = $("#myfile")[0].files[0];

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
        files.push(file);
        event.target.value = '';//解决不能重复上传同一个文件bug

    });
    /* $('#comeback_message').click(function () {

         var url = $(this).data('content');
         $("#yida-main").load(url);
     });*/
    //jQuery的Ajax异步上传配图照片
    $("#submit_form_btn").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        // 添加文件对象到表单

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
            url: "EditServlet",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
            success: function (result, status, xhr) {//请求成功回调
                if (status == "success") {
                    var str = "发布焦点消息成功,result=" + result;
                    alarmChange(str);
                    //触发a标签返回景点列表
                    var link = document.getElementById("xiaoxiliebiao")
                    link.click();
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alarmChange(str)
            }
        });
    });

    //根据景点标题 选择的景区 发布的时间来搜索功能
    $("#searchThem").click(function () {
        //获取当前标题内容
        var title = document.getElementById("title").value;
        //获取输入的当前开始时间
        var startTime = document.getElementById("startTime").value;
        //获取输入当前结束时间
        var endTime = document.getElementById("endTime").value;
        //结束时间要大于起始时间判断
        if (endTime != "" && startTime != "" && endTime < startTime) {
            alarmChange("错误：结束时间大于起始时间")
            return;
        }
        //输入全部为空时不能提交
        var array = [];
        if (title == "" && startTime == "" && endTime == "") {
            alarmChange("错误：请输入要搜索的内容")
            return;
        }

    });

    /*-----------------去第几页begin-------------------------*/
    //显示出来有页面的1 2 3 4 5

    $(".go_page").click(function () {
        var page = $(this).data('page');
        //将page通过ajx传入后端
        goPage(page);
        ajax();
    });

    $("#input_btn").click(function () {
        var page = parseInt($("#input_text").val());
        // 对当前页做校验：必须是数字，并且要小于等于总页数
        var reg = /^[1-9][0-9]*$/;
        if (reg.test(page) == false) {
            alarmChange("跳转页数必须大于1");
            return;
        }

        var total = parseInt($("#total_page_hidden").val())// 获取总页数
        if (page > total) {
            alarmChange("共" + total + "页，" + "跳转页" + page + ">" + total + "(不允许)");
            return;
        }
        goPage(page);
        ajax();
    });
    /*-----------------去第几页end-------------------------*/
//删除功能

    $(".form-check-input").change(function () {
            //当选择checkbox时候所有的子标签选中 并全给后端
            var checkbox = document.getElementById("checkbox");
            //检测有没有勾选 如果有勾选就看有没有child 有将所有的child勾选
            var tables = document.getElementById("table").children[0].children;

            if (checkbox.checked == true) {
                for (var i = 1; i < tables.length; i++) {
                    //所有勾选上
                    tables[i].children[0].children[0].children[0].checked = true;

                }
            }

        }
    );
    $("#deleteAll").click(function () {
        debugger;
        var ids = [];
        //检测有没有勾选 如果有勾选就看有没有child 有将所有的child勾选
        var tables = document.getElementById("table").children[0].children;
        //把获取到a删除标签的id值记录 生成数组
        for (var i = 1; i < tables.length; i++) {
            //检查那个被勾选了 记录下来数组
            if (tables[i].children[0].children[0].children[0].checked == true) {
                var str = tables[i].children[4].children[1].lastElementChild.search;
                var id = str.split("=")[1];
                ids.push(id);
            }
        }
        //如果没有选中不传入后端
        if (ids.length == 0) {
            return alarmChange("请勾选选中要删除的的部分");
        }
        //将ids数组传入后端
        $.ajax({
            url: "deleteAllMessage",
            type: 'POST',
            ContentTpye: "application/x-www-form-urlencoded",
            data: {"array": ids},
            success: function (data) {//请求成功回调
            }
        });
    });//向后端传入选中焦点非焦点的id值
    $("#changAllMessage").click(function () {
        var ids = [];
        //检测有没有勾选 如果有勾选就看有没有child 有将所有的child勾选
        var tables = document.getElementById("table").children[0].children;
        //把获取到a删除标签的id值记录 生成数组
        for (var i = 1; i < tables.length; i++) {
            //检查那个被勾选了 记录下来数组
            if (tables[i].children[0].children[0].children[0].checked == true) {
                var str = tables[i].children[4].children[1].lastElementChild.search;
                var id = str.split("=")[1];
                ids.push(id);
            }
        }
        //如果没有选中不传入后端
        if (ids.length == 0) {
            return alarmChange("请勾选选中要删除的的部分");
        }
        //将ids数组传入后端
        $.ajax({
            url: "setFocusAllMessage",
            type: 'POST',
            ContentTpye: "application/x-www-form-urlencoded",
            data: {"array": ids},
            success: function (data) {//请求成功回调
            }
        });
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

//自动调整文本域的宽度随屏幕变化(没用)
/*window.onresize=function () {
    var texttarea = document.getElementsByClassName("texttarea")
    var aa = document.body.scrollWidth * 0.70;
    var aaa = aa.toString() + "px";
    texttarea[0].style.width = aaa;
}*/


// 去第几页
function goPage(cur) {
    var curpage = $("#hidden_cur").val();// 获取隐藏域当前页的值
    $("#hidden_cur").val(cur);// 赋值
}

// 提交条件查询表单数据
function ajax() {
    //获取page
    var pa = document.getElementById("hidden_cur").value;
    var page = parseInt(pa);
    // var url = $("#form").attr("action");
    // var formData = $("#form").serialize();// 获取表单数据
    //获取当前页面的URL
    var ur = location.href;
    if (ur == "http://localhost/TravelSystem/xiaoxiliebiao") {
        var url = xiaoxiliebiao;
        $.ajax({
            url: url,
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            ContentTpye: "application/x-www-form-urlencoded",
            data: {"pageNum": page},
            success: function (result, status, xhr) {//请求成功回调
                debugger;
                if (status == "success") {
                    $("#body").html(result);// 加载属性
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alarmChange("请求存在异常");
            }
        })
    } else {
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
                    debugger;
                    $("#body").html(result);// 加载属性
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alarmChange("请求存在异常");
            }
        });
    }

}