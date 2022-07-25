$(document).ready(function () {
    //jQuery的Ajax异步上传配图照片
    $("#submit_form_btn").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        // 添加本内容到表单
        var name= document.getElementById("input_tile").value;
        var phoneNumber= document.getElementById("telphone1").value;
        formData.append("name", name);
        formData.append("PhoneNumber", phoneNumber);
        //如果没有填完所有信息不能进行提交
       publicMethod();
        if (alarm == false) {
            return;
        }
        $.ajax({
            url: "addScenicAreaConsult",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
            success: function (result, status, xhr) {//请求成功回调
                if (status == "success") {
                    var str = "发布景点咨询成功,result=" + result;
                    alarmChange(str);
                    //触发a标签返回景点列表
                    var link = document.getElementById("jingquzixun")
                    link.click();
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alarmChange(str)
            }
        });
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
        var ids = [];
        //检测有没有勾选 如果有勾选就看有没有child 有将所有的child勾选
        var tables = document.getElementById("table").children[0].children;
        //把获取到a删除标签的id值记录 生成数组
        for (var i = 1; i < tables.length; i++) {
            //检查那个被勾选了 记录下来数组
            if (tables[i].children[0].children[0].children[0].checked == true) {
                var str = tables[i].children[3].children[0].lastElementChild.search;
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
            url: "deleteAllScenicAreaConsult",
            type: 'POST',
            ContentTpye: "application/x-www-form-urlencoded",
            data: {"array": ids},
            success: function (data) {//请求成功回调
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
    var tile = document.getElementById("input_tile");
    var telphone = document.getElementById("telphone1");//验证电话号码

    if (tile.value == "") {
        alarmChange("请输入咨询点名称");
        return;
    }

    //电话号码JS校验
    if (telphone.value == "") {
        alarmChange("联系号码不能为空");
        return;
    }else  if(!telphone.value == ""){
        var reg = /^\d{3,8}$/;
        if (reg.test(telphone.value) == false) {
            alarmChange("联系号码非法");
            return;
        }
    }
    alarm = true;
}
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
    if (ur == "http://localhost/TravelSystem/jingquzixun") {
        var url =jingquzixun;
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
    }

}
