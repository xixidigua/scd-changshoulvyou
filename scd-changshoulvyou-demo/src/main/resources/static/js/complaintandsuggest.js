$(document).ready(function () {
    //根据景点标题 选择的景区 发布的时间来搜索功能
    $("#searchThem").click(function () {
        //获取当前的选择的景区
        var scenicSpot = document.getElementById("select_scenic_spot").value;
        //获取输入的当前开始时间
        var startTime = document.getElementById("startTime").value;
        //获取输入当前结束时间
        var endTime = document.getElementById("endTime").value;
        //结束时间要大于起始时间判断
        if (endTime != "" && scenicSpot==1&&startTime != "" && endTime < startTime) {
            alarmChange("错误：结束时间大于起始时间")
            return;
        }
        //输入全部为空时不能提交
        var array = [];
        if (scenicSpot==1&&startTime == "" && endTime == "") {
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
        var ids = [];
        //检测有没有勾选 如果有勾选就看有没有child 有将所有的child勾选
        var tables = document.getElementById("table").children[0].children;
        //把获取到a删除标签的id值记录 生成数组
        for (var i = 1; i < tables.length; i++) {
            //检查那个被勾选了 记录下来数组
            if (tables[i].children[0].children[0].children[0].checked == true) {
                var str = tables[i].children[6].children[0].lastElementChild.search;
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
            url: "deleteAllComplaintAndSuggest",
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
// 去第几页
function goPage(cur){
    var curpage = $("#hidden_cur").val();// 获取隐藏域当前页的值
    $("#hidden_cur").val(cur);// 赋值
}
// 提交条件查询表单数据
function ajax() {
    //获取page
    var pa=document.getElementById("hidden_cur").value;
    var page=parseInt(pa);
    // var url = $("#form").attr("action");
    // var formData = $("#form").serialize();// 获取表单数据
    //获取当前页面的URL
    var ur= location.href;
    if(ur=="http://localhost/TravelSystem/tousuAndIdea"){

        var url ="tousuAndIdea";
        $.ajax({
            url: url,
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            ContentTpye: "application/x-www-form-urlencoded",
            data: {"pageNum": page},
            success: function (result, status, xhr) {//请求成功回调
                if (status == "success") {
                    $("#body").html(result);// 加载属性
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alarmChange("请求存在异常");
            }
        })
    }else{

        var url = $("#form").attr("action");
        var formData = $("#form").serialize();// 获取表单数据
        $.ajax({
            url: url,
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            success:function(result,status,xhr) {//请求成功回调
                if(status == "success") {
                    $("#body").html(result);// 加载属性
                }
            },
            error:function(xhr) {//请求失败回调
                var str = "ERROR:"+xhr.statusText+"---"+xhr.status;
                alarmChange("请求存在异常");
            }
        });
    }

}