var filesUpload = [];
$(document).ready(function () {
    $("#myfiles").change(function () {
        var file = $("#myfiles")[0].files;
        for (var i = 0; i < file.length; i++) {
            var temp = file[i].name;
            var maxSize = 1024;//最大允许上传1024Kb，即：1M
            var fileSizex = file[i].size / 1024;
            var fileSize = fileSizex.toFixed(2);
            var type = file[i].type.split("/")[1];
            var reg = /(jpg|jpeg|png)/;
            if (!reg.test(type)) {
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
            filesUpload.push(file[i]);
            var yyy = document.createElement("span");
            yyy.innerText = "未上传";
            yyy.setAttribute("class", "text-danger");
            var del = document.createElement("button");
            del.setAttribute("onClick", "deletUser(this);");
            del.setAttribute("id", "my_input");
            del.setAttribute("type", "button");
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

        }

        event.target.value = '';
    });

//jQuery的Ajax异步上传配图照片
    $("#myupload").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        // 添加文件对象到表单
        if (filesUpload.length == 0) {
            alarmChange("请先传照片")
            return;
        }
        //最终判断 是什么文件对象需要传进去

        for (var i = 0; i < filesUpload.length; i++) {
            formData.append("yida", filesUpload[i]);
        }
        $.ajax({
            url: "ajaxUploadFilesAndFields",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
            success: function (result, status, xhr) {//请求成功回调
                if (status == "success") {

                    var rows = document.getElementById("my_tbody").rows.length - 1;
                    if (rows >= 1) {
                        var x = document.getElementById("my_tfoot_file");//=rows+"个文件（未上传）";
                        x.innerText = rows + "个文件";
                    }

//                  上传修改统计栏颜色
                    var xx = document.getElementById("my_tfoot_file");
                    xx.style.color = 'black';
                    var xxx = document.getElementById("my_tfoot_totle");
                    xxx.style.color = 'black';
                    var xxxx = document.getElementById("my_tfoot_size");
                    xxxx.style.color = 'black';
                    var str = "图片上传成功";
                    alarmChange(str);
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alarmChange(str)
            }
        });
    });

});


function deletUser(r) {//单行删除
    var i = r.parentNode.parentNode.rowIndex;//获取所在行在表格中的位置
    //删除files数组里面的文件对象，不让传入后端
    filesUpload.splice(i - 2, 1);
    console.log(r.parentNode.parentNode);
    //删除
    var tbody = document.getElementById("my_tbody");
    tbody.deleteRow(i - 1); //前面两行已经使用-2
    var rows = document.getElementById("my_tbody").rows.length - 1;

//将已经上传的图片filesUpload从新提交给后台
    var datas = document.getElementById("my_tbody");
    if (i > 2) {
        var datax = datas.rows[i - 2].cells[2].childNodes[1];
        if (datax.style.color == "black") {
            var form = $("#myform")[0];
            var formData = new FormData(form);

            // 添加文件对象到表单
            if (filesUpload.length == 0) {
                alarmChange("请先传照片")
                return;
            }
            //最终判断 是什么文件对象需要传进去

            for (var i = 0; i < filesUpload.length; i++) {
                formData.append("yida", filesUpload[i]);
            }
            $.ajax({
                url: "ajaxUploadFilesAndFields",
                type: 'POST',
                async: true,
                cache: false,//不使用缓存
                data: formData,
                processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
                contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
                success: function (result, status, xhr) {//请求成功回调
                    if (status == "success") {
                        var str = "图片删除成功";
                        alarmChange(str);
                    }
                },
                error: function (xhr) {//请求失败回调
                    var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                    alarmChange(str)
                }
            });
        }
    }

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
        var xxx = document.getElementById("my_tfoot_size");

        //判断是不是回收站是不是都是红的(杠杠的)
        if (datas.rows.length > 1) {
            for (var m = 1; m < datas.rows.length; m++) {
                var datax = datas.rows[m].cells[2].childNodes[1];
                if (datax.style.color !== 'red' && datax.style.color !== 'black') {
                    xxx.style.color = 'red';
                    x.style.color = 'red';
                    xx.style.color = 'red';
                } else {
                    xxx.style.color = 'black';
                    x.style.color = 'black';
                    xx.style.color = 'black';
                    x.innerText = rows + "个文件";
                }
            }
        }

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

function uploadFile() {

    //修改移除文件栏内容
    var datas = document.getElementById("my_tbody");
    if (datas.rows.length > 1) {
        for (var m = 1; m < datas.rows.length; m++) {
            var data = datas.rows[m].cells[2].childNodes[0];
            data.innerText = "";
            var datax = datas.rows[m].cells[2].childNodes[1];
            datax.style.color = 'black';
        }
    } else {
        alarmChange('请上传配图');
        return;
    }
    /*//上传修改统计栏颜色
    var xx = document.getElementById("my_tfoot_file");
    xx.style.color = 'black';
    var xxx = document.getElementById("my_tfoot_totle");
    xxx.style.color = 'black';
    var xxxx = document.getElementById("my_tfoot_size");
    xxxx.style.color = 'black';*/
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
    if (sumes.toString().split(".")[1].length > 2) {
        var sumes = sumes.toFixed(2);
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

    //jQuery的Ajax异步上传配图照片提交
    $("#submit_form_btn").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        // 添加文件对象到表单

        for (var i = 0; i < files.length; i++) {
            formData.append("yida", files[i]);
        }
        //如果没有填完所有信息不能进行提交
        publicMethod();
        if (alarm == false) {
            return;
        }
        files = [];
        $.ajax({
            url: "ajaxUploadFilesAndField",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
            success: function (result, status, xhr) {//请求成功回调
                if (status == "success") {
                    var str = "发布景点成功" ;
                    alarmChange(str);
                    debugger;
                    //触发a标签返回景点列表
                    var link = document.getElementById("comeback")
                    link.click();

                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alarmChange(str)
            }
        });
    });
//修改更新
    $("#change_form_btn").click(function () {
        debugger;
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
                url: "ajaxUpload",
                type: 'POST',
                async: true,
                cache: false,//不使用缓存
                data: formData,
                processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
                contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
                success: function (result, status, xhr) {//请求成功回调
                    if (status == "success") {
                        var str = "修改景点成功" ;
                        alarmChange(str);
                        //触发a标签返回景点列表
                        var link = document.getElementById("comeback")
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
            var scenicSpot = document.getElementById("select_scenic_spot").value;
            var palyTime = document.getElementById("play_time").value;
            var openTime = document.getElementById("open_time").value;
            var transportationMessage = document.getElementById("transportation_message").value;
            var personPrice = document.getElementById("person_price").value;
            var childrenPrice = document.getElementById("children_price").value;
            var address = document.getElementById("address").value;
            var ticketShow = document.getElementById("ticket_show").value;
            var mapShowName = document.getElementById("map_show_name").value;
            var longitude = document.getElementById("longitude").value;//经度
            var latitude = document.getElementById("latitude").value;//纬度
            var intro = document.getElementById("explain").value;//说明
            var arrs=[];
            arrs.push(id);
            arrs.push(tile);
            arrs.push(scenicSpot);
            arrs.push(palyTime);
            arrs.push(openTime);
            arrs.push(transportationMessage);
            arrs.push(personPrice);
            arrs.push(childrenPrice);
            arrs.push(address);
            arrs.push(ticketShow);
            arrs.push(mapShowName);
            arrs.push(longitude);
            arrs.push(latitude);
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
                url: "ajaxUploadForm",
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
                            var link = document.getElementById("comeback")
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
function publicMethod() {
    var tile = document.getElementById("input_tile");
    var scenicSpot = document.getElementById("select_scenic_spot");
    var palyTime = document.getElementById("play_time");
    var openTime = document.getElementById("open_time");
    var transportationMessage = document.getElementById("transportation_message");
    var personPrice = document.getElementById("person_price");
    var childrenPrice = document.getElementById("children_price");
    var address = document.getElementById("address");
    var ticketShow = document.getElementById("ticket_show");
    var mapShowName = document.getElementById("map_show_name");
    var longitude = document.getElementById("longitude");//经度
    var latitude = document.getElementById("latitude");//纬度
    var explain = document.getElementById("explain");//说明
    var file = document.getElementById("my_tfoot_size");//效验有没有上传文件
    var image = document.getElementById("myimage");//效验有没有缩略图

    alarm = false;
    if (tile.value == "") {
        alarmChange("请输入景点标题");
        return;
    } else if (scenicSpot.value == "请选择景区") {
        alarmChange("请选择景区");
        return;
    }
    if (palyTime.value == "") {
        alarmChange("游玩时长不能为空");
        return;
    } else if (!palyTime.value == "") {//可以用正则做出来
        var reg = /^\d|[0-1]\d|2[0-4]$/;
        if (reg.test(palyTime.value) == false) {
            alarmChange("输入正确的格式游玩时间（0-24）");
            return;
        }
    }

    if (openTime.value == "") {
        alarmChange("开放时间不能为空");
        return;
    } else if (!openTime.value == "") {
        var reg = /^(\d|[0-1]\d|2[0-3]):([0-5]\d)-(\d|[0-1]\d|2[0-3]):([0-5]\d)$/;
        if (reg.test(openTime.value) == false) {
            alarmChange("请输入正确的格式开放时间（06：00-12：00）");
            return;
        }
    }
    if (transportationMessage.value == "") {
        alarmChange("交通信息不能为空");
        return;
    }
    if (personPrice.value == "") {
        alarmChange("成人门票不能为空");
        return;
    } else if (!personPrice.value == "") {
        var reg = /^\d+(\.\d{0,2})?$/;
        if (reg.test(personPrice.value) == false) {
            alarmChange("请输入成人门票：整数或者保留2位小数");
            return;
        }
    }
    if (childrenPrice.value == "") {
        alarmChange("儿童门票不能为空");
        return;
    } else if (!childrenPrice.value == "") {
        var reg = /^\d+(\.\d{0,2})?$/;
        if (reg.test(childrenPrice.value) == false) {
            alarmChange("请输入儿童门票：整数或者保留2位小数");
            return;
        }
    }
    if (parseInt(childrenPrice.value) > parseInt(personPrice.value)) {
        alarmChange("儿童价格不能大于成人价格");
        return;
    }
    if (address.value == "") {
        alarmChange("地址信息不能为空");
        return;
    }
    if (ticketShow.value == "") {
        alarmChange("门票说明信息不能为空");
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
    if (explain.value == "") {
        alarmChange("简介不能为空");
        return;
    }

    if (file.innerText == "0.00KB" || file.style.color == "red") {
        alarmChange("请先上传景点图片");
        return;
    }

    if (image.outerHTML.includes("icon.jpg")) {
        alarmChange("请先上传景缩略图");
        return;
    }
    alarm = true;
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
