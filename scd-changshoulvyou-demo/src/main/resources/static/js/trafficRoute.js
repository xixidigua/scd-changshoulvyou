//发布交通路线
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
            url: "updateTrafficRoute",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指定过了
            success:function(result,status,xhr) {//请求成功回调
                if(status == "success") {
                    var str = "提交交通路线成功";
                    alarmChange(str);
                    //触发a标签返回景点列表
                    var link= document.getElementById("trafficRoute")
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
//获取经纬度

function getLongitudeAndLatitude() {
    var geocoder = new AMap.Geocoder();
    var addr = document.getElementById('input_tile').value;
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
    var longitude = document.getElementById("longitude");//经度
    var latitude= document.getElementById("latitude");//纬度

    if (tile.value == "") {
        alarmChange("请输入目的地名称");
        return;
    }
    //经度JS校验

    if (longitude.value == "") {
        alarmChange("经度不能为空");
        return;
    }else  if(!longitude.value == ""){
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
    }else  if(!latitude.value == ""){
        var reg = /^-?((0|[1-8]?[0-9]?)((\.\d{0,6})?)|90(\.[0]{0,6}?))$/;
        if (reg.test(latitude.value) == false) {
            alarmChange("纬度整数部分为（+|-）0到90，小数部分0-6位");
            return;
        }
    }
    alarm = true;

}

