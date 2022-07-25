package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yida.scdchangshoulvyoudemo.entity.ConsumptionClassify;
import com.yida.scdchangshoulvyoudemo.entity.RimColumn;
import com.yida.scdchangshoulvyoudemo.service.ConsumptionClassifyService;
import com.yida.scdchangshoulvyoudemo.service.RimColumnService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller 控制层景点增加上传
 */
@Controller
public class AddRimController {
    StringBuffer fileSourse = new StringBuffer(10000);
    StringBuffer fileSoursePhonto = new StringBuffer(10000);

    @Autowired
    private RimColumnService rimColumnService;
    @Autowired
    private ConsumptionClassifyService consumptionClassifyService;

    @ResponseBody
    @RequestMapping(value = "/ajaxUploadrims", produces = "text/html;charset=utf-8")

    public String ajaxUploadFilesAndFields(
            @RequestParam("yida") MultipartFile[] files,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {//判断条件,一定要加上!很重要!!
                String fname = files[i].getName();//获取上传的组件名称，即：formData.append("yida",file);里的yida
                String fileName = files[i].getOriginalFilename();//获取文件名称
                String type = files[i].getContentType();//获取文件类型

                //限定上传的文件类型,比如:只能传图片(.png,.jpg,.jpeg)
                if (type.endsWith("png") || type.endsWith("jpg") || type.endsWith("jpeg")) {
                    String strSize = getFileSize(files[i].getSize());//获取文件大小，单位：字节byte
                    System.out.println("文件组件名：" + fname + "，文件名称：" + fileName + "，类型：" + type + "，大小：" + strSize);

                    //保存文件到当前工程D目录下

                    //时间戳分类文件
                    String time = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
                    String realPath = request.getServletContext().getRealPath("/static/") + time;
                    File f = new File(realPath + File.separator + "upload" + File.separator + getFileName2(fileName));
                    String s = f.toString();
                    String str = "";
                    if (s == "") {
                        System.out.println("文件为空");
                    } else {
                        str = s.substring(43);
                    }
                    f.getParentFile().mkdirs();//创建upload目录
                    files[i].transferTo(f);//保存文件
                    //获取写完后当前文件的路径，放入StringBufeer中
                    fileSourse.append(fileName + ":" + strSize + ":" + str + ",");

                } else {
                    throw new RuntimeException("上传文件类型,必须为.png,.jpg,.jpeg");
                }
            } else {
                resultMap.put("code", 401);
                resultMap.put("msg", "上传失败");
                return mapToJson(resultMap);
            }

        }
        resultMap.put("code", 200);
        resultMap.put("msg", "上传成功");
        return mapToJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxUploadrim", produces = "text/html;charset=utf-8")
    public String ajaxUploadFilesAndField(
            @RequestParam("yida") MultipartFile[] files,
            @RequestParam("title") String title,
            @RequestParam("abodeClassify") String abodeClassify,//住所类型
            @RequestParam("trafficMessage") String trafficMessage,
            @RequestParam("consultPrice") String consultPrice,//参考价格
            @RequestParam("recommendGreens") String recommendGreens,//推荐菜
            @RequestParam("address") String address,
            @RequestParam("mapShowName") String mapShowName,
            @RequestParam("longitude") String longitude,//经度
            @RequestParam("latitude") String latitude,//纬度
            @RequestParam("telphone") String telphone,//联系电话1
            @RequestParam("intro") String explain,//简介

            HttpServletRequest request
    ) throws Exception {
        fileSoursePhonto = new StringBuffer();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取景点的value值
        String value = request.getParameter("Classify");
        Integer id = Integer.parseInt(value);
        ConsumptionClassify consumptionClassify = new ConsumptionClassify();
        List<ConsumptionClassify> consumptionClassifies= consumptionClassifyService.readAll();
        consumptionClassify = consumptionClassifies.get(id - 1);


        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = title == "" && abodeClassify == ""  && consultPrice == ""  &&  trafficMessage == "" &&
                recommendGreens == ""  && address == "" && telphone == "" && mapShowName == "" && longitude == "" &&
                latitude == "" && explain == "";
        if (bean) {
            resultMap.put("code", 405);
            resultMap.put("msg", "查看是否有需要输入的没输入");
            return mapToJson(resultMap);
        }
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {//判断条件,一定要加上!很重要!!
                String fname = files[i].getName();//获取上传的组件名称，即：formData.append("yida",file);里的yida
                String fileName = files[i].getOriginalFilename();//获取文件名称
                String type = files[i].getContentType();//获取文件类型

                //限定上传的文件类型,比如:只能传图片(.png,.jpg,.jpeg)
                if (type.endsWith("png") || type.endsWith("jpg") || type.endsWith("jpeg")) {
                    String strSize = getFileSize(files[i].getSize());//获取文件大小，单位：字节byte
                    System.out.println("文件组件名：" + fname + "，文件名称：" + fileName + "，类型：" + type + "，大小：" + strSize);

                    //保存文件到当前工程D目录下
                    //时间戳分类文件
                    String time = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
                    //  String realPath = "C:/" + time;
                    String realPath = request.getServletContext().getRealPath("/static/") + time;
                    // String realPath = ResourceUtils.getURL("classpath:").getPath()+"static/";

                    File f = new File(realPath + File.separator + "upload" + File.separator + getFileName2(fileName));
                    f.getParentFile().mkdirs();//创建upload目录
                    String s = f.toString();
                    String str = "";
                    if (s == "") {
                        System.out.println("文件为空");
                    } else {
                        str = s.substring(43);
                    }
                    files[i].transferTo(f);//保存文件
                    //获取写完后当前文件的路径，放入StringBufeer中
                    fileSoursePhonto.append(str);

                } else {
                    throw new RuntimeException("上传文件类型,必须为.png,.jpg,.jpeg");
                }
            } else {
                resultMap.put("code", 401);
                resultMap.put("msg", "上传失败");
                return mapToJson(resultMap);
            }

        }
        //将所有数据写入数据库里面
        //获取当前时间
        String updateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
        RimColumn rimColumn = new RimColumn();
        rimColumn.setTitle(title);
        rimColumn.setConsumptionClassify(consumptionClassify );
        rimColumn.setAbodeClassify(abodeClassify);//住所类型
       rimColumn.setTrafficMessage(trafficMessage);
      rimColumn.setConsultPrice(consultPrice);//设置参考价格
       rimColumn.setRecommendGreens(recommendGreens);//推荐菜
      rimColumn.setAddress(address);
       rimColumn.setMapShowName(mapShowName);
       rimColumn.setLongitude(longitude);
        rimColumn.setLatitude(latitude);
     rimColumn.setTelphone(telphone);
        rimColumn.setIntro(explain);
        rimColumn.setFileSourse(fileSourse.toString());
        fileSourse = new StringBuffer();
       rimColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        fileSoursePhonto = new StringBuffer();
        rimColumn.setUpdateTime(updateTime);
        rimColumnService.add(rimColumn);
        resultMap.put("code", 200);
        resultMap.put("msg", "上传成功");
        return mapToJson(resultMap);

    }

    //把map对象转成json字符串
    public String mapToJson(Map<String, Object> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String strJson = mapper.writeValueAsString(map);
            return strJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //捕获超出文件限定大小的异常信息
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String fileUploadException(Model model, Exception e) {
        Map<String, Object> exceptionMap = new HashMap<String, Object>();
        String error = "";
        if (e instanceof MaxUploadSizeExceededException) {//捕获超出文件限定大小的异常信息
            if (e.getCause() instanceof FileSizeLimitExceededException) {
                FileSizeLimitExceededException ex = (FileSizeLimitExceededException) e.getCause();
                String actualSize = getFileSize(ex.getActualSize());//获取实际大小(感觉有问题！)
                String permittedSize = getFileSize(ex.getPermittedSize());//获取最大允许大小
                String fileName = ex.getFileName();//获取文件名
                error = fileName + "的实际大小为" + actualSize + "，单文件大小不能超过" + permittedSize;
            } else {
                SizeLimitExceededException ex = (SizeLimitExceededException) e.getCause();
                String actualSize = getFileSize(ex.getActualSize());//获取实际总大小
                String permittedSize = getFileSize(ex.getPermittedSize());//获取最大允许总大小
                error = "上传文件实际总大小为" + actualSize + "，总大小不能超过" + permittedSize;
            }
        } else {//捕获文件类型异常信息,以及其它异常信息
            error = e.getMessage();
        }

        exceptionMap.put("code", 400);
        exceptionMap.put("msg", error);
        return mapToJson(exceptionMap);
    }


    //计算文件大小
    public String getFileSize(long size) {
        //1G=1024M, 1M=1024K, 1K=1024B
        String strSize = null;
        if (size >= 1024 * 1024 * 1024) {//G
            //%表示语法，必须写，.3表示小数点后保留3位有效数字，f表示格式化浮点数，G只是随便写的。
            strSize = String.format("%.3fG", size / (1024 * 1024 * 1024.0));
        } else if (size >= 1024 * 1024) {//M
            strSize = String.format("%.3fM", size / (1024 * 1024.0));
        } else if (size >= 1024) {//K
            strSize = String.format("%.3fK", size / 1024.0);
        } else {//B
            strSize = size + "B";
        }
        return strSize;
    }

    //方式二：根据hashCode码来管理文件（随机生成第一层目录和第二层目录） (推荐！！)
    //13/6/03ffaadsf9080987adfa.png
    public String getFileName2(String name) {
        int code = name.hashCode();//获取hashCode码
        int first = code & 0XF;//第一层目录
        int second = code & (0XF >> 1);//第二层目录
        String str1 = UUID.randomUUID().toString();//随机字符串
        String str2 = name.substring(name.indexOf("."));//文件后辍
        String str = first + "/" + second + "/" + str1 + str2;
        return str;
    }
//周边列表删除功能
//删除功能(一个)
@GetMapping("/deleteRim")
public String deleteData(@RequestParam("id") Integer id,HttpServletRequest request) {
    //去掉相同的元素
    List<RimColumn> rimColumns = rimColumnService.readAll();
    for (RimColumn v : rimColumns) {
        //比较数据库里面有没有对应的id数据 没有不让删除
        if (v.getId() == id) {
            rimColumnService.delete(id);
        }
    }
    return "redirect:/zhoubianliebiao";//重定向;
}

    //获取前端选勾选的id值
    List<Integer> list = new ArrayList<>();

    @RequestMapping(value = "/deleteAllRim")
    public void deleteAllData(HttpServletRequest req) {
        String[] ids = req.getParameterValues("array[]");
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Integer id = Integer.parseInt(ids[i]);
                list.add(id);
            }
        }

    }

    //删除功能(多删除)
    @GetMapping("/deleteAllRim")
    public String deleteData() {
        if (list.size() > 0) {
            for (Integer id : list) {
                //去掉相同的元素
                List<RimColumn> rimColumns = rimColumnService.readAll();
                for (RimColumn v : rimColumns) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (v.getId() == id) {
                        rimColumnService.delete(id);
                    }
                }
            }
        }
        list.clear();
        return "redirect:/zhoubianliebiao";//重定向;
    }
    // 周边列表修改功能
    @GetMapping("/updateRim")
    public String update(@RequestParam("id") Integer id, Model model, HttpServletRequest req) {
        RimColumn rimColumn= rimColumnService.readOne(id);
        List<ConsumptionClassify> consumptionClassifies = consumptionClassifyService.readAll();
        model.addAttribute("consumptionClassifies", consumptionClassifies);
        model.addAttribute("rimColumn", rimColumn);
        model.addAttribute("id", id);

        return "bianjifabuEatAndPlay";
    }

//修改了缩略图情况下 更新
    @ResponseBody
    @RequestMapping(value = "/ajaxUploadUpdateRims", produces = "text/html;charset=utf-8")
    public String ajaxUpload(
            @RequestParam("yida") MultipartFile[] files,
            @RequestParam("id") String ids,
            @RequestParam("title") String title,
            @RequestParam("abodeClassify") String abodeClassify,
            @RequestParam("trafficMessage") String trafficMessage,
            @RequestParam("consultPrice") String consultPrice,
            @RequestParam("recommendGreens") String recommendGreens,
            @RequestParam("address") String address,
            @RequestParam("mapShowName") String mapShowName,
            @RequestParam("longitude") String longitude,//经度
            @RequestParam("latitude") String latitude,//纬度
            @RequestParam("telphone") String telphone,//纬度
            @RequestParam("intro") String explain,//简介
            @RequestParam("hidden") String[] string,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer idd = Integer.valueOf(ids);
        //获取景点的value值
       String value = request.getParameter("Classify");
        Integer id = Integer.parseInt(value);
        ConsumptionClassify consumptionClassify = new ConsumptionClassify();
        List<ConsumptionClassify> consumptionClassifies = consumptionClassifyService.readAll();
        consumptionClassify = consumptionClassifies.get(id - 1);


        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = title == "" && abodeClassify == ""  && consultPrice == ""  &&  trafficMessage == "" &&
                recommendGreens == ""  && address == "" && telphone == "" && mapShowName == "" && longitude == "" &&
                latitude == "" && explain == "";
        if (bean) {
            resultMap.put("code", 405);
            resultMap.put("msg", "查看是否有需要输入的没输入");
            return mapToJson(resultMap);
        }
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {//判断条件,一定要加上!很重要!!
                String fname = files[i].getName();//获取上传的组件名称，即：formData.append("yida",file);里的yida
                String fileName = files[i].getOriginalFilename();//获取文件名称
                String type = files[i].getContentType();//获取文件类型

                //限定上传的文件类型,比如:只能传图片(.png,.jpg,.jpeg)
                if (type.endsWith("png") || type.endsWith("jpg") || type.endsWith("jpeg")) {
                    String strSize = getFileSize(files[i].getSize());//获取文件大小，单位：字节byte
                    System.out.println("文件组件名：" + fname + "，文件名称：" + fileName + "，类型：" + type + "，大小：" + strSize);

                    //保存文件到当前工程D目录下
                    //时间戳分类文件
                    String time = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
                    //  String realPath = "C:/" + time;
                    String realPath = request.getServletContext().getRealPath("/static/") + time;
                    // String realPath = ResourceUtils.getURL("classpath:").getPath()+"static/";

                    File f = new File(realPath + File.separator + "upload" + File.separator + getFileName2(fileName));
                    f.getParentFile().mkdirs();//创建upload目录
                    String s = f.toString();
                    String str = "";
                    if (s == "") {
                        System.out.println("文件为空");
                    } else {
                        str = s.substring(43);
                    }
                    files[i].transferTo(f);//保存文件
                    //获取写完后当前文件的路径，放入StringBufeer中
                    fileSoursePhonto.append(str);

                } else {
                    throw new RuntimeException("上传文件类型,必须为.png,.jpg,.jpeg");
                }
            } else {
                resultMap.put("code", 401);
                resultMap.put("msg", "上传失败");
                return mapToJson(resultMap);
            }

        }
        //判断景点上传照片有没有被删除 和删除的情况下
        RimColumn rimColumn = rimColumnService.readOne(idd);
        StringBuffer buff=new StringBuffer();
      for(int b=0;b<string.length;b++){
          buff.append(string[b]+",");
      }
        String str1=buff.toString();
        if (!rimColumn.getFileSourse().equals(str1) &&! str1 .equals("")) {
            //更新上传FileSouse
            String[] str = rimColumn.getFileSourse().split(",");
            String[] str2 = str1.split(",");
            for (int i = 0; i < str.length; i++) {
                String str3 = str[i];

                for (int j = 0; j < string.length; j++) {
                    if (!str3 .equals(string[j]) ) {
                        fileSourse.append(str3 + ",");
                    }
                }
            }
            rimColumn.setFileSourse(fileSourse.toString());
        }
      if (str1.equals("")&&!fileSourse.toString().equals("")) {
            StringBuffer buffer=new StringBuffer(rimColumn.getFileSourse());
            StringBuffer str = fileSourse.append(buffer);
          rimColumn.setFileSourse(str.toString());
        }

        //将所有数据写入数据库里面
        //获取当前时间
        String updateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
        rimColumn.setId(idd);
        rimColumn.setTitle(title);
        rimColumn.setConsumptionClassify(consumptionClassify );
        rimColumn.setAbodeClassify(abodeClassify);//住所类型
        rimColumn.setTrafficMessage(trafficMessage);
        rimColumn.setConsultPrice(consultPrice);//设置参考价格
        rimColumn.setRecommendGreens(recommendGreens);//推荐菜
        rimColumn.setAddress(address);
        rimColumn.setMapShowName(mapShowName);
        rimColumn.setLongitude(longitude);
        rimColumn.setLatitude(latitude);
        rimColumn.setTelphone(telphone);
        rimColumn.setIntro(explain);
        if(string.length==0&&fileSourse.toString()!=""){
            rimColumn.setFileSourse(rimColumn.getFileSourse());
        }else{
            rimColumn.setFileSourse(fileSourse.toString());
        }
        fileSourse = new StringBuffer();
        rimColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        fileSoursePhonto = new StringBuffer();
        rimColumn.setUpdateTime(updateTime);
     rimColumnService.update(rimColumn);
        resultMap.put("code", 200);
        resultMap.put("msg", "上传成功");
        return mapToJson(resultMap);

    }

    //不改变缩略图的情况下 删除或没删除 删除景点照片情况下 更新数据库信息
    @ResponseBody
    @RequestMapping(value = "/ajaxUploadUpdateRim", produces = "text/html;charset=utf-8")
    public String ajaxUploadForm(
            @RequestParam("arr") String[] arr,
            HttpServletRequest request
    ) throws Exception {
        //读取数据库里面的信息
        Integer id = Integer.valueOf(arr[0]);
        RimColumn rimColumn = rimColumnService.readOne(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //对数据库里面的字段和前端传过来的比较 组成新的ScenicSpotsColumn对象 存入数据库
          int a=0;
        if (!rimColumn.getTitle().equals(arr[1]) && !arr[1].equals("")) {
            rimColumn.setTitle(arr[1]);
            a=2;
        }
        if (!rimColumn.getConsumptionClassify().getId().equals(Integer.valueOf(arr[2])) &&Integer.valueOf(arr[2]) != null) {
            List<ConsumptionClassify> consumptionClassifies = consumptionClassifyService.readAll();
            for (int i = 0; i < consumptionClassifies.size(); i++) {
                ConsumptionClassify consumptionClassify = consumptionClassifies.get(i);
                if (consumptionClassify.getId() == Integer.valueOf(arr[2])) {
                    rimColumn.setConsumptionClassify(consumptionClassify);
                }
            }
            a=2;
        }
        if (!rimColumn.getAbodeClassify() .equals(arr[3] ) && !arr[3].equals("")) {
            rimColumn.setAbodeClassify(arr[3]);
            a=2;
        }

        if (!rimColumn.getTrafficMessage().equals(arr[4]) &&! arr[4].equals("") ) {
            rimColumn.setTrafficMessage(arr[4]);
            a=2;
        }
        if (!rimColumn.getConsultPrice().equals(arr[5]) && !arr[5].equals("")) {
           rimColumn.setConsultPrice(arr[5]);
            a=2;
        }
        if (!rimColumn.getRecommendGreens().equals(arr[6]) && !arr[6].equals("")) {
            rimColumn.setRecommendGreens(arr[6]);
            a=2;
        }
        if (!rimColumn.getAddress().equals(arr[7]) && !arr[7].equals("")) {
            rimColumn.setAddress(arr[7]);
            a=2;
        }
        if (!rimColumn.getMapShowName().equals(arr[8]) && !arr[8].equals("")) {
            rimColumn.setMapShowName(arr[8]);
            a=2;
        }
        if (!rimColumn.getLongitude().equals(arr[9]) && !arr[9].equals("")) {
            rimColumn.setLongitude(arr[9]);
            a=2;
        }
        if (!rimColumn.getLatitude().equals(arr[10]) && !arr[10].equals("")) {
            rimColumn.setLatitude(arr[10]);
            a=2;
        }
        if (!rimColumn.getTelphone().equals(arr[11]) && !arr[11].equals("")) {
            rimColumn.setTelphone(arr[11]);
            a=2;
        }
        if (!rimColumn.getIntro() .equals(arr[12]) && !arr[12].equals("")) {
            rimColumn.setIntro(arr[12]);
            a=2;
        }
        //判断景点上传照片有没有被删除 和删除的情况下
         if (!rimColumn.getFileSourse().equals( arr[13]) &&! arr[13] .equals("noDelete")) {
            //更新上传FileSouse
            String[] str = rimColumn.getFileSourse().split(",");
            String[] str1 = arr[13].split(",");
            for (int i = 0; i < str.length; i++) {
                String str2 = str[i];

                for (int j = 0; j < str1.length; j++) {
                    if (!str2 .equals(str1[j]) ) {
                        fileSourse.append(str2 + ",");
                    }
                }
            }
            rimColumn.setFileSourse(fileSourse.toString());
             a=2;
             fileSourse = new StringBuffer();
        }
         if (arr[13].equals("noDelete")&&fileSourse.toString().equals("")) {
             a++;
        }
        if (arr[13].equals("noDelete")&&!fileSourse.toString().equals("")) {
            StringBuffer buffer=new StringBuffer(  rimColumn.getFileSourse());
            StringBuffer str = buffer.append(fileSourse );
            rimColumn.setFileSourse(str.toString());
            a=2;
        }

         if(a==1){
        resultMap.put("code", 400);
        resultMap.put("msg", "检查没有修改的部分");
        return mapToJson(resultMap);
         }else {
             //缩略图就不更改了
             //更新时间
             String updateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
            rimColumn.setUpdateTime(updateTime);
             //将所有数据更新到数据库里面
             rimColumnService.update(rimColumn);

             resultMap.put("code", 200);
             resultMap.put("msg", "更新成功");
             return mapToJson(resultMap);
         }
    }
//查询周边列表信息
//查询功能searchRimColumn
@RequestMapping( value = "/searchRimColumn", method = {RequestMethod.POST, RequestMethod.GET})
public String  searchVideo (HttpServletRequest req,
                            Model model,
                            @RequestParam("search") String title,
                            @RequestParam("Classify") Integer classify_id,
                            @RequestParam("begin") String startTime,
                            @RequestParam("end") String endTime,
                            @RequestParam(value = "strCur", required = false, defaultValue = "1") Integer pageNum
) {
    List<ConsumptionClassify> consumptionClassifies = consumptionClassifyService.readAll();
    model.addAttribute("consumptionClassifies", consumptionClassifies);

    PageHelper.startPage(pageNum, 3);
    List<RimColumn> rimColumns = rimColumnService.read(title,classify_id,startTime,endTime);
    model.addAttribute("title",title);
    model.addAttribute("classify_id",classify_id);
    model.addAttribute("startTime",startTime);
    model.addAttribute("endTime",endTime);
    model.addAttribute("rimColumns", rimColumns);
    PageInfo<RimColumn> page = new PageInfo<>(rimColumns);
    model.addAttribute("page", page);
    return  "zhoubianliebiao";
}

}