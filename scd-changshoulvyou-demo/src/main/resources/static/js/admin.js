$(document).ready(function () {
    //获取当前时间
    /*let curDate = new Date();
    let year = curDate.getFullYear();
    let month = curDate.getMonth() + 1;
    let day = curDate.getDate();
    $('.yida-time').text(year + "-" + month + "-" + day)*!
*/
//   动态设置选中菜单样式
    $('.yida-submenu').click(function () {
        /* /!* var arr = $('.yida-submenu ');
          for (var i = 0; i < arr.length; i++) {
              $(arr[i]).removeClass('text-success');//原生元素arr[i]变成jQuery元素$(arr[i])，反过来在$(arr[i])加个[0]这样$(arr[i])
          }
          $(this).addClass('text-success');*!/*/

        //动态切换右侧主界面
        //var url=$(this).attr('data-content');
        var url = $(this).data('content');

        $("#yida-main").load(url);

    });
    var ids = [];
    $(".form-check-input").change(function () {
        //当选择checkbox时候所有的子标签选中 并全给后端
        var checkbox = document.getElementById("checkbox");
        //检测有没有勾选 如果有勾选就看有没有child 有将所有的child勾选
        var tables = document.getElementById("table").children[0].children;

        if (checkbox.checked == true) {
            for (var i = 1; i < tables.length; i++) {
                //所有勾选上
                tables[i].children[0].children[0].children[0].checked = true;
                //把获取到a删除标签的id值记录 生成数组
                var str = tables[i].children[4].children[1].lastElementChild.search;
                var id = str.split("=")[1];
                ids.push(id);
            }
        } else {
            //把获取到a删除标签的id值记录 生成数组
            for (var i = 1; i < tables.length; i++) {
                //检查那个被勾选了 记录下来数组
                if (tables[i].children[0].children[0].children[0].checked == true) {
                    var str = tables[i].children[4].children[1].lastElementChild.search;
                    var id = str.split("=")[1];
                    ids.push(id);
                }
            }
        }
        //将ids数组传入后端
        $.ajax({
            url: "deleteAll",
            type: 'POST',
            ContentTpye: "application/x-www-form-urlencoded",
            data: {"array": ids},
            success: function (data) {//请求成功回调
            }
        });

    });
   //根据景点标题 选择的景区 发布的时间来搜索功能
    $("#searchThem").click(function() {
        //获取当前标题内容
        var title = document.getElementById("title").value;
        //获取当前的选择的景区
        var scenicSpot = document.getElementById("select_scenic_spot").value;
        //获取输入的当前开始时间
        var startTime = document.getElementById("startTime").value;
        //获取输入当前结束时间
        var endTime = document.getElementById("endTime").value;
        //结束时间要大于起始时间判断
        if (endTime!=""&&startTime!=""&&endTime<startTime) {
            alarmChange("错误：结束时间大于起始时间")
            return;
        }
        //输入全部为空时不能提交
        var array = [];
        if(title==""&&scenicSpot==1&&startTime==""&&endTime==""){
            alarmChange("错误：请输入要搜索的内容")
            return;
        }

    });
    /*-----------------去第几页begin-------------------------*/
  //显示出来有页面的1 2 3 4 5
    $(".go_page").click(function(){
        var page = $(this).data('page');
        //将page通过ajx传入后端
        goPage(page);
        ajax();
    });

    $("#input_btn").click(function(){

        var page = parseInt($("#input_text").val());
        // 对当前页做校验：必须是数字，并且要小于等于总页数
        var reg = /^[1-9][0-9]*$/;
        if (reg.test(page) == false) {
            alarmChange("跳转页数必须大于1");
            return;
        }

        var total = parseInt($("#total_page_hidden").val())// 获取总页数
        if (page > total) {
           alarmChange("共"+total+"页，"+"跳转页"+page+">"+total+"(不允许)");
            return;
        }
        goPage(page);
        ajax();
    });
    /*-----------------去第几页end-------------------------*/

});
/*
function include(id, url) {
    var req = new XMLHttpRequest();
    var element = document.getElementById(id);
    req.open("get", url, false);
    req.send();
    element.innerHTML = req.responseText;
}
*/
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
    //获取当前页面的URL
   var ur= location.href;
   //打包时发布到网页 要改Ip地址
  if(ur=="http://192.168.85.131/TravelSystem/jingdianliebiao"||ur=="http://192.168.85.131/TravelSystem/fanhuijingdianliebiao"){
     //if(ur=="http://localhost/TravelSystem/jingdianliebiao"||ur=="http://localhost/TravelSystem/fanhuijingdianliebiao"){
       var url =jingdianliebiao;
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