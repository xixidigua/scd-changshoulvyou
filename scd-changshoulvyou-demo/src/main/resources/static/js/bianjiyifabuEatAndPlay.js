$(document).ready(function () {
    var files = [];
    $("#myfiles").change(function () {
        //获取上传文件名
        var file = $("#myfiles")[0].files[0];
        // var name = document.getElementsByName("name")[0].value;
        var obj = document.getElementById("myfiles");
        var len = obj.files.length;
        // var addID= addId();
        // var addXd=addID();
        for (var i = 0; i < len; i++) {
            var temp = obj.files[i].name;

            var maxSize = 1024;//最大允许上传1024Kb，即：1M
            var fileSizex = obj.files[i].size / 1024;
            var fileSize = fileSizex.toFixed(2);
            var reg = /\.(jpg|jpeg|png)/;
            if (!reg.test(obj.value)) {
                alarmChange("1个非法文件格式已过滤");
                event.target.value = '';//解决不能重复上传同一个文件bug
                return;
            } else {
                if (fileSizex > maxSize * 1024) {
                    alarmChange("图片大小不能超过1M");
                    event.target.value = '';//解决不能重复上传同一个文件bug
                    return;
                }
            }
            files.push(file);
            var tbody = document.getElementById("my_tbody");
            var tr = document.createElement("tr");
            tbody.appendChild(tr);
            var td1 = document.createElement("td");
            td1.innerHTML = temp;
            tr.appendChild(td1);
            var td2 = document.createElement("td");
            td2.innerHTML = fileSize + "K";
            tr.appendChild(td2);

            var td3 = document.createElement("td");
            tr.appendChild(td3);

            var yyy = document.createElement("span");
            yyy.innerText = "未上传";
            yyy.setAttribute("class", "text-danger");
            var del = document.createElement("button");
            del.setAttribute("onClick", "deletUser(this);");
            del.setAttribute("id", "my_input");
            del.setAttribute("name", "my_del");
            del.setAttribute("class", "iconfont icon-huishouzhan")
            td3.appendChild(yyy);
            td3.appendChild(del);

            //统计文件个数
            var rows = document.getElementById("my_tbody").rows.length - 1;
//修改0个文件的显示
            if (rows >= 1) {
                var x = document.getElementById("my_tfoot_file");//=rows+"个文件（未上传）";
                x.innerText = rows + "个文件（未上传）";
                x.style.color = 'red';
                //修改大小显示
                var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
                xx.innerText = "总计约：";
                xx.style.color = 'red';
                //获取文件大小
                getSzie();
            }
            event.target.value = '';//解决不能重复上传同一个文件bug
        }
    });
    //jQuery的Ajax异步上传配图照片
    $("#myupload").click(function() {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        // 添加文件对象到表单
        if(files.length==0){
            alarmChange("请先传照片")
            return;
        }
        for (var i=0 ;i<files.length;i++){
            formData.append("yida",files[i]);
        }
        files=[];
        $.ajax({
            url: "ajaxUploadrims",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
            success:function(result,status,xhr) {//请求成功回调
                if(status == "success") {
                    var rows = document.getElementById("my_tbody").rows.length - 1;
                    if (rows >= 1) {
                        var x = document.getElementById("my_tfoot_file");//=rows+"个文件（未上传）";
                        x.innerText = rows + "个文件";
                    }

                    var str = "上传成功,result="+result;
                    alarmChange(str);
                }
            },
            error:function(xhr) {//请求失败回调
                var str = "ERROR:"+xhr.statusText+"---"+xhr.status;
                alarmChange(str)
            }
        });
    });
    //根据景点标题 选择的景区 发布的时间来搜索功能
    $("#searchThem").click(function () {
        //获取当前标题内容
        var title = document.getElementById("title").value;
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
        if (title == "" && scenicSpot==1&&startTime == "" && endTime == "") {
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
            url: "deleteAllRim",
            type: 'POST',
            ContentTpye: "application/x-www-form-urlencoded",
            data: {"array": ids},
            success: function (data) {//请求成功回调
            }
        });
    });
});

/*
var a=0;
function addId() {
   return function (){return ++a;}
}*/
function deletUser(r) {//单行删除

    var i = r.parentNode.parentNode.rowIndex;//获取所在行在表格中的位置
    console.log(r.parentNode.parentNode);
    var tbody = document.getElementById("my_tbody");
    tbody.deleteRow(i - 1); //前面两行已经使用-2
    var rows = document.getElementById("my_tbody").rows.length - 1;
    //将修改的字体修改回来

    if (rows >= 1) {
        var x = document.getElementById("my_tfoot_file");//获取有多无文件;
        x.innerText = rows + "个文件（未上传）";
        x.style.color = 'red';
        //修改大小显示
        var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
        xx.innerText = "总计约：";
        xx.style.color = 'red';
        //动态修改文件总大小
        getSzie();

    } else if (rows <= 0) {
        var x = document.getElementById("my_tfoot_file");//获取有多无文件;
        x.innerText = "0个文件";
        x.style.color = 'black';
        //修改大小显示
        var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
        xx.innerText = "总计：";
        xx.style.color = 'black';
        //修改文件大小
        var xxx = document.getElementById("my_tfoot_size");//=rows+"个文件（未上传）";
        xxx.innerText = "0.00KB";
        xxx.style.color = 'black';
    }

}

function upload() {
    //修改移除文件栏内容
    var datas = document.getElementById("my_tbody");
    if (datas.rows.length > 1) {
        for (var m = 1; m < datas.rows.length; m++) {
            var data = datas.rows[m].cells[2].childNodes[0];
            data.innerText = "";
            var datax = datas.rows[m].cells[2].childNodes[1];
            datax.style.color = 'black';
        }
    }
    //上传修改统计栏颜色
    var xx = document.getElementById("my_tfoot_file");
    xx.style.color = 'black';
    var xxx = document.getElementById("my_tfoot_totle");
    xxx.style.color = 'black';
    var xxxx = document.getElementById("my_tfoot_size");
    xxxx.style.color = 'black';
}

//获取文件函数
function getSzie() {

    var datas = document.getElementById("my_tbody");
    var sumes = 0;
    var data = "";
    for (var m = 1; m < datas.rows.length; m++) {
        var data = datas.rows[m].cells[1].innerText;
        //去掉K转成数字
        var sum = parseFloat(data.slice(0, data.length - 1));
        var sumes = sumes + sum;
    }
    if (sumes > 1000) {
        var yy = sumes / 1024;
        var sums = yy.toFixed(2);
        var xx = document.getElementById("my_tfoot_size");//修改总文件的大小
        xx.innerText = sums + "M";
        xx.style.color = 'red';
    } else {
        var xx = document.getElementById("my_tfoot_size");//
        xx.innerText = sumes + "K";
        xx.style.color = 'red';
    }

}

//获取经纬度

function getLongitudeAndLatitude() {
    var geocoder = new AMap.Geocoder();
    var addr = document.getElementById('address').value;
    geocoder.getLocation(addr, function (status, result) {
        if (status === 'complete' && result.geocodes.length) {
            var lnglat = result.geocodes[0].location;
            var longitude = lnglat.lng;//经度
            var latitude = lnglat.lat;//纬度
            document.getElementById('longitude').value = longitude;//经度
            document.getElementById('latitude').value = latitude;//纬度

        } else {
            alarmChange("还没输入地址哦");
        }
    });
}
//上传缩略图 一起发布
//修改功能
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
    })
    /*  $('.addjingdian').click(function () {

          var url = $(this).data('content');
          $("#yida-main").load(url);
      });*/

    //jQuery的Ajax异步上传配图照片
    $("#submit_form_btn").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        //将删除的绝对地址传入后端
        var arrs=[];
        if(fileSourse.length>0){
            for(var i=0;i<fileSourse.length;i++){
                arrs.push(fileSourse[i]+",");
            }
            document.getElementById('hidden').value = arrs;
        }else {
            arrs.push("noDelete")
        }
        // 添加文件对象到表单

        if (files.length > 0) {
            for (var i = 0; i < files.length; i++) {
                formData.append("yida", files[i]);
            }

            files = [];
            //如果没有填完所有信息不能进行提交
            publicMethod();
            if (alarm == false) {
                return;
            }

            $.ajax({
                url: "ajaxUploadUpdateRims",
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
                        var link = document.getElementById("zhoubianliebiao")
                        link.click();
                    }
                },
                error: function (xhr) {//请求失败回调
                    var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                    alarmChange(str)
                }
            });
        }else {
            publicMethod();
            if (alarm == false) {
                return;
            }
            //获取表单里面的数据 用数组保存
            var id = document.getElementById("id").value;
            var tile = document.getElementById("input_tile").value;
            var classify = document.getElementById("select_scenic_spot").value;
            var abodeClassify = document.getElementById("play_time").value;
            var transportationMessage = document.getElementById("transportation_message").value;
            var consultPrice= document.getElementById("refer_to_Price").value;
            var recommendGreens = document.getElementById("tuijiancai").value;
            var address = document.getElementById("address").value;
            var mapShowName = document.getElementById("map_show_name").value;
            var longitude = document.getElementById("longitude").value;//经度
            var latitude = document.getElementById("latitude").value;//纬度
            var telphone = document.getElementById("telphone1").value;//联系电话1
            var intro = document.getElementById("explain").value;//说明
            var arrs=[];
            arrs.push(id);
            arrs.push(tile);
            arrs.push(classify);
            arrs.push(abodeClassify);
            arrs.push(transportationMessage);
            arrs.push(consultPrice);
            arrs.push(recommendGreens);
            arrs.push(address);
            arrs.push(mapShowName);
            arrs.push(longitude);
            arrs.push(latitude);
            arrs.push(telphone);
            arrs.push(intro);
            //将删除的绝对地址传入后端
            if(fileSourse.length>0){
                for(var i=0;i<fileSourse.length;i++){
                    arrs.push(fileSourse[i]+",");
                }
            }else {
                arrs.push("noDelete")
            }
            $.ajax({
                url: "ajaxUploadUpdateRim",
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
                            var link = document.getElementById("zhoubianliebiao")
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
var  alarm =false;//全局变量执行判断是否为真然后提交
function publicMethod() {
    var tile = document.getElementById("input_tile");
    var scenicSpot = document.getElementById("select_scenic_spot");
    var palyTime = document.getElementById("play_time");
    var openTime = document.getElementById("open_time");
    var transportationMessage = document.getElementById("transportation_message");
    var personPrice = document.getElementById("refer_to_Price");
    var tuijiancai = document.getElementById("tuijiancai");
    var address = document.getElementById("address");
    var ticketShow = document.getElementById("ticket_show");
    var mapShowName = document.getElementById("map_show_name");
    var telphone = document.getElementById("telphone1");//验证电话号码
    var longitude = document.getElementById("longitude");//经度
    var latitude = document.getElementById("latitude");//纬度
    var explain = document.getElementById("explain");//说明
    var file = document.getElementById("my_tfoot_size");//效验有没有上传文件
    var image = document.getElementById("myimage");//效验有没有缩略图

    if (tile.value == "") {
        alarmChange("请输入标题");
        return;
    } else if (scenicSpot.value == "请选择类型") {
        alarmChange("请选择分类");
        return;
    }
    if (palyTime.value == "") {
        alarmChange("住所类型不能为空");
        return;
    }

    if (transportationMessage.value == "") {
        alarmChange("交通信息不能为空");
        return;
    }
    if (personPrice.value == "") {
        alarmChange("参考价格不能为空");
        return;
    } else if (!personPrice.value == "") {
        var reg = /^\d+(\.\d{0,2})?$/;
        if (reg.test(personPrice.value) == false) {
            alarmChange("请输入参考价格：整数或者保留2位小数");
            return;
        }
    }
    if (tuijiancai.value == "") {
        alarmChange("推荐菜不能为空");
        return;
    }
    if (address.value == "") {
        alarmChange("地址信息不能为空");
        return;
    }

    if (mapShowName.value == "") {
        alarmChange("地图显示名称不能为空");
        return;
    }
    //经度JS校验

    if (longitude.value == "") {
        alarmChange("经度不能为空");
        return;
    } else if (!longitude.value == "") {
        var reg = /^-?(((0|1?[0-7]?[0-9]?)|[8-9][0-9])((\.\d{0,6})?)|180(\.[0]{0,6}?))$/;
        if (reg.test(longitude.value) == false) {
            alarmChange("经度整数部分为（+|-）0到180，小数部分0-6位");
            return;
        }
    }
    //纬度JS校验
    if (latitude.value == "") {
        alarmChange("纬度不能为空");
        return;
    } else if (!latitude.value == "") {
        var reg = /^-?((0|[1-8]?[0-9]?)((\.\d{0,6})?)|90(\.[0]{0,6}?))$/;
        if (reg.test(latitude.value) == false) {
            alarmChange("纬度整数部分为（+|-）0到90，小数部分0-6位");
            return;
        }
    }
    //电话号码JS校验
    if (telphone.value == "") {
        alarmChange("联系号码1不能为空");
        return;
    } else if (!telphone.value == "") {
        var reg = /^\d{3,8}$/;
        if (reg.test(telphone.value) == false) {
            alarmChange("必填号码1非法");
            return;
        }
    }
    if (explain.value == "") {
        alarmChange("简介不能为空");
        return;
    }
//不用
    /* if (file.innerText == "0.00KB"|| file.style.color=="red" ) {
         alarmChange("请先上传景点图片");
         return ;
     }*/

    if (image.outerHTML.includes("icon.jpg")) {
        alarmChange("请先上传景缩略图");
        return;
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
    if (ur == "http://localhost/TravelSystem/zhoubianliebiao") {
        var url = zhoubianliebiao;
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
//前端显示数据库文件
var tbody = document.getElementById("my_tbody");
var str = tbody.align;
var fileSourse=[];
if (!str == "") {
    //对str进行处理
    var arr = str.split(",");
    for (var i = 0; i < arr.length - 1; i++) {
        var tr = document.createElement("tr");
        var array = arr[i].split(":")
        var temp = array[0];
        var fileSize = array[1];
        tbody.appendChild(tr);
        var td1 = document.createElement("td");
        td1.innerHTML = temp;
        tr.appendChild(td1);
        var td2 = document.createElement("td");
        td2.innerHTML = fileSize + "K";
        tr.appendChild(td2);

        var td3 = document.createElement("td");
        tr.appendChild(td3);

        var yyy = document.createElement("span");
        //yyy.innerText = "未上传";
        // yyy.setAttribute("class", "text-danger");
        var del = document.createElement("button");
        del.setAttribute("onClick", "deletUserFile(this);");
        del.setAttribute("id", "my_input_del");
        del.setAttribute("name", "my_del");
        del.setAttribute("class", "iconfont icon-huishouzhan")
        td3.appendChild(yyy);
        td3.appendChild(del);
    }


    //统计文件个数
    var rows = document.getElementById("my_tbody").rows.length - 1;

    //修改0个文件的显示
    if (rows >= 1) {
        var x = document.getElementById("my_tfoot_file");//=rows+"个文件（未上传）";
        x.innerText = rows + "个文件";
        x.style.color = 'black';
        //修改大小显示
        var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
        xx.innerText = "总计：";
        xx.style.color = 'black';
        //获取文件大小
        getSzie();
        var xxxx = document.getElementById("my_tfoot_size");
        xxxx.style.color = 'black';
    }

    function deletUserFile(r) {//单行删除
        //对str进行处理并传入后端
        var arr = str.split(",");
        var i = r.parentNode.parentNode.rowIndex;//获取所在行在表格中的位置
        //被删除的地址
        var file= arr[i - 2];
        fileSourse.push(file);
        //将fileSourse地址传入后端 前端产生一个隐藏的input
        var hidden = document.getElementById("hidden");
        hidden.value = fileSourse;
        console.log(r.parentNode.parentNode);
        var tbody = document.getElementById("my_tbody");
        tbody.deleteRow(i - 1); //前面两行已经使用-2
        var rows = document.getElementById("my_tbody").rows.length - 1;
        //将修改的字体修改回来

        if (rows >= 1) {
            var x = document.getElementById("my_tfoot_file");//获取有多无文件;
            x.innerText = rows + "个文件";
            x.style.color = 'balck';
            //修改大小显示
            var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
            xx.innerText = "总计：";
            xx.style.color = 'balack';
            //动态修改文件总大小
            getSzie();
            var xxx = document.getElementById("my_tfoot_size");//=rows+"个文件（未上传）";
            xxx.style.color = 'black';

        } else if (rows <= 0) {
            var x = document.getElementById("my_tfoot_file");//获取有多无文件;
            x.innerText = "0个文件";
            x.style.color = 'black';
            //修改大小显示
            var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
            xx.innerText = "总计：";
            xx.style.color = 'black';
            //修改文件大小
            var xxx = document.getElementById("my_tfoot_size");//=rows+"个文件（未上传）";
            xxx.innerText = "0.00KB";
            xxx.style.color = 'black';
        }
    }
}