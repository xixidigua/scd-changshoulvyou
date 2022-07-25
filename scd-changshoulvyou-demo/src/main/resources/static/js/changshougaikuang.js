$(document).ready(function () {
    var files = [];
    $("#myfiles").change(function () {
//获取上传文件名
        var file = $("#myfiles")[0].files[0];

        var obj = document.getElementById("myfiles");
        var len = obj.files.length;

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
            yyy.setAttribute("name","myspan");
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
            var myspan = document.getElementsByName("myspan");
            if (rows >= 1) {
                var x = document.getElementById("my_tfoot_file");//=rows+"个文件（未上传）";
                x.innerText = myspan.length + "个文件（未上传）";
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
    $("#myupload").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        // 添加文件对象到表单
        if (files.length == 0) {
            alarmChange("请先传照片")
            return;
        }
        for (var i = 0; i < files.length; i++) {
            formData.append("yida", files[i]);
        }

        files = [];
        $.ajax({
            url: "ajaxUploadLongevityProfiles",
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

                    var str = "上传成功,result=" + result;
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
    console.log(r.parentNode.parentNode);
    var tbody = document.getElementById("my_tbody");
    tbody.deleteRow(i - 1); //前面两行已经使用-2
    var rows = document.getElementById("my_tbody").rows.length - 1;
    //将修改的字体修改回来

    var myspan = document.getElementsByName("myspan");
    if (rows >= 1&&myspan.length>0) {
        var x = document.getElementById("my_tfoot_file");//获取有多无文件;
        x.innerText = myspan.length+ "个文件（未上传）";
        x.style.color = 'red';
        //修改大小显示
        var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
        xx.innerText = "总计约：";
        xx.style.color = 'red';
        //动态修改文件总大小
        getSzie();

    } else if (rows <= 0) {
        var x = document.getE
        lementById("my_tfoot_file");//获取有多无文件;
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
    if(myspan.length==0){
        var x = document.getElementById("my_tfoot_file");//获取有多无文件;
        x.innerText = rows + "个文件";
        x.style.color = 'black';
        //修改大小显示
        var xx = document.getElementById("my_tfoot_totle");//=rows+"个文件（未上传）";
        xx.innerText = "总计：";
        xx.style.color = 'black';
        //动态修改文件总大小
        getSzie();
        var xxx = document.getElementById("my_tfoot_size");//=rows+"个文件（未上传）";
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

//提交发布功能
$(document).ready(function () {
    //jQuery的Ajax异步上传配图照片
    $("#submit_form_btn").click(function () {
        var form = $("#myform")[0];
        var formData = new FormData(form);
        // 添加富文本内容到表单
        var content = editor.html();
        formData.append("content", content);
            //如果没有填完所有信息不能进行提交
            publicMethod();
            if (alarm == false) {
                return;
            }
            $.ajax({
                url: "ajaxUploadLongevityProfile",
                type: 'POST',
                async: true,
                cache: false,//不使用缓存
                data: formData,
                processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
                contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
                success: function (result, status, xhr) {//请求成功回调
                    if (status == "success") {
                        var str = "发布长寿概况成功";
                        alarmChange(str);
                        //触发a标签返回景点列表
                        var link = document.getElementById("LongevityProfile")
                        link.click();
                    }
                },
                error: function (xhr) {//请求失败回调
                    var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                    alarmChange(str)
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
function publicMethod() {
    var tile = document.getElementById("input_tile");
    var file = document.getElementById("my_tfoot_size");//效验有没有上传文件
    if (tile.value == "") {
        alarmChange("请输入长寿概况标题");
        return;
    }
    var content = editor.html();
    if (content == "" || content == null) {
        alarmChange("消息内容不得为空");
        return;
    }
    if (file.innerText == "0.00KB" || file.style.color == "red") {
        alarmChange("请先上传景点图片");
        return;
    }
    alarm = true;
}

//前端显示数据库文件
var tbody = document.getElementById("my_tbody");
var str = tbody.align;
var fileSourse=[];
if (!str == "") {
    debugger;
    //对str进行处理
    var arr = str.split(",");
    for (var i = 0; i < arr.length ; i++) {
        if (arr[i]!="") {
            var tr = document.createElement("tr");
            var array = arr[i].split(":")
            var temp = array[0];
            var fileSize = array[1];
            tbody.appendChild(tr);
            var td1 = document.createElement("td");
            td1.innerHTML = temp;
            tr.appendChild(td1);
            var td2 = document.createElement("td");
            td2.innerHTML = fileSize;
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
        debugger;
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