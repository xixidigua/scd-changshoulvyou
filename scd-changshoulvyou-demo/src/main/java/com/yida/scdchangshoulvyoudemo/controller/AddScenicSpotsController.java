package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yida.scdchangshoulvyoudemo.entity.Scenic;
import com.yida.scdchangshoulvyoudemo.entity.ScenicSpotsColumn;
import com.yida.scdchangshoulvyoudemo.service.ScenicSpotsColumnService;
import com.yida.scdchangshoulvyoudemo.service.SenicService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller 控制层景点增加上传
 */
@Controller
public class AddScenicSpotsController {
    StringBuffer fileSourse = new StringBuffer(10000);
    StringBuffer fileSoursePhonto = new StringBuffer(10000);

    @Resource
    private ScenicSpotsColumnService scenicSpotsColumnService;
    @Resource
    private SenicService senicService;

    @ResponseBody
    @RequestMapping(value = "/ajaxUploadFilesAndFields", produces = "text/html;charset=utf-8")

    public String ajaxUploadFilesAndFields(
            @RequestParam("yida") MultipartFile[] files,
            HttpServletRequest request
    ) throws Exception {
        fileSourse = new StringBuffer(10000);
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
    @RequestMapping(value = "/ajaxUploadFilesAndField", produces = "text/html;charset=utf-8")
    public String ajaxUploadFilesAndField(
            @RequestParam("yida") MultipartFile[] files,
            @RequestParam("title") String title,
            @RequestParam("playTime") String playTime,
            @RequestParam("openTime") String openTime,
            @RequestParam("trafficMessage") String trafficMessage,
            @RequestParam("personPrice") String personPrice,
            @RequestParam("childrenPrice") String childrenPrice,
            @RequestParam("address") String address,
            @RequestParam("ticketShow") String ticketShow,
            @RequestParam("mapShowName") String mapShowName,
            @RequestParam("longitude") String longitude,//经度
            @RequestParam("latitude") String latitude,//纬度
            @RequestParam("intro") String explain,//简介

            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取景点的value值
        String value = request.getParameter("Scenic");
        String value1 = request.getParameter("strCur");
        Integer id = Integer.parseInt(value);
        Scenic scenic = new Scenic();
        List<Scenic> scenics = senicService.readAll();
        scenic = scenics.get(id - 1);


        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = title == "" || playTime == "" || openTime == "" || trafficMessage == "" ||
                personPrice == "" || childrenPrice == "" || address == "" ||
                ticketShow == "" || mapShowName == "" || longitude == "" ||
                latitude == "" || explain == "";
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
        ScenicSpotsColumn scenicSpotsColumn = new ScenicSpotsColumn();
        scenicSpotsColumn.setTitle(title);
        scenicSpotsColumn.setScenic(scenic);
        scenicSpotsColumn.setPlayTime(playTime);
        scenicSpotsColumn.setOpenTime(openTime);
        scenicSpotsColumn.setTrafficMessage(trafficMessage);
        scenicSpotsColumn.setPersonPrice(personPrice);
        scenicSpotsColumn.setChildrenPrice(childrenPrice);
        scenicSpotsColumn.setAddress(address);
        scenicSpotsColumn.setTicketShow(ticketShow);
        scenicSpotsColumn.setMapShowName(mapShowName);
        scenicSpotsColumn.setLongitude(longitude);
        scenicSpotsColumn.setLatitude(latitude);
        scenicSpotsColumn.setIntro(explain);
        scenicSpotsColumn.setFileSourse(fileSourse.toString());
        scenicSpotsColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        scenicSpotsColumn.setUpdatetime(updateTime);
        scenicSpotsColumnService.add(scenicSpotsColumn);
        fileSoursePhonto = new StringBuffer();
        fileSourse = new StringBuffer();
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

    //修改了缩略图情况下 更新
    @ResponseBody
    @RequestMapping(value = "/ajaxUpload", produces = "text/html;charset=utf-8")
    public String ajaxUpload(
            @RequestParam("yida") MultipartFile[] files,
            @RequestParam("id") String ids,
            @RequestParam("title") String title,
            @RequestParam("playTime") String playTime,
            @RequestParam("openTime") String openTime,
            @RequestParam("trafficMessage") String trafficMessage,
            @RequestParam("personPrice") String personPrice,
            @RequestParam("childrenPrice") String childrenPrice,
            @RequestParam("address") String address,
            @RequestParam("ticketShow") String ticketShow,
            @RequestParam("mapShowName") String mapShowName,
            @RequestParam("longitude") String longitude,//经度
            @RequestParam("latitude") String latitude,//纬度
            @RequestParam("intro") String explain,//简介
            @RequestParam("hidden") String[] string,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer idd = Integer.valueOf(ids);
        //获取景点的value值
        String value = request.getParameter("Scenic");
        Integer id = Integer.parseInt(value);
        Scenic scenic = new Scenic();
        List<Scenic> scenics = senicService.readAll();
        scenic = scenics.get(id - 1);


        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = title.equals("") || playTime.equals("") || openTime.equals("") || trafficMessage.equals("") ||
                personPrice.equals("") || childrenPrice.equals("") || address.equals("") ||
                ticketShow.equals("") || mapShowName.equals("") || longitude.equals("") ||
                latitude.equals("") || explain.equals("");
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
        ScenicSpotsColumn scenicSpotsColumn = scenicSpotsColumnService.readOne(idd);
        StringBuffer buff = new StringBuffer();
        for (int b = 0; b < string.length; b++) {
            buff.append(string[b] + ",");
        }
        String str1 = buff.toString();
        if (!scenicSpotsColumn.getFileSourse().equals(str1) && !str1.equals("")) {
            //更新上传FileSouse
            String[] str = scenicSpotsColumn.getFileSourse().split(",");
            String[] str2 = str1.split(",");
            for (int i = 0; i < str.length; i++) {
                String str3 = str[i];

                for (int j = 0; j < string.length; j++) {
                    if (!str3.equals(string[j])) {
                        fileSourse.append(str3 + ",");
                    }
                }
            }
            scenicSpotsColumn.setFileSourse(fileSourse.toString());
        }
        if (str1.equals("") && !fileSourse.toString().equals("")) {
            StringBuffer buffer = new StringBuffer(scenicSpotsColumn.getFileSourse());
            StringBuffer str = fileSourse.append(buffer);
            scenicSpotsColumn.setFileSourse(str.toString());
        }

        //将所有数据写入数据库里面
        //获取当前时间
        String updateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
        scenicSpotsColumn.setId(idd);
        scenicSpotsColumn.setTitle(title);
        scenicSpotsColumn.setScenic(scenic);
        scenicSpotsColumn.setPlayTime(playTime);
        scenicSpotsColumn.setOpenTime(openTime);
        scenicSpotsColumn.setTrafficMessage(trafficMessage);
        scenicSpotsColumn.setPersonPrice(personPrice);
        scenicSpotsColumn.setChildrenPrice(childrenPrice);
        scenicSpotsColumn.setAddress(address);
        scenicSpotsColumn.setTicketShow(ticketShow);
        scenicSpotsColumn.setMapShowName(mapShowName);
        scenicSpotsColumn.setLongitude(longitude);
        scenicSpotsColumn.setLatitude(latitude);
        scenicSpotsColumn.setIntro(explain);
        scenicSpotsColumn.setFileSourse(fileSourse.toString());
        scenicSpotsColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        scenicSpotsColumn.setUpdatetime(updateTime);
        scenicSpotsColumnService.update(scenicSpotsColumn);
        fileSoursePhonto = new StringBuffer();
        fileSourse = new StringBuffer();
        resultMap.put("code", 200);
        resultMap.put("msg", "上传成功");

        return mapToJson(resultMap);

    }

    //不改变缩略图的情况下 删除或没删除 删除景点照片情况下 更新数据库信息
    @ResponseBody
    @RequestMapping(value = "/ajaxUploadForm", produces = "text/html;charset=utf-8")
    public String ajaxUploadForm(
            @RequestParam("arr") String[] arr,
            HttpServletRequest request
    ) throws Exception {
        //读取数据库里面的信息
        Integer id = Integer.valueOf(arr[0]);
        ScenicSpotsColumn scenicSpotsColumn = scenicSpotsColumnService.readOne(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //对数据库里面的字段和前端传过来的比较 组成新的ScenicSpotsColumn对象 存入数据库
        int a = 0;
        if (!scenicSpotsColumn.getTitle().equals(arr[1]) && !arr[1].equals("")) {
            scenicSpotsColumn.setTitle(arr[1]);
            a = 2;
        }
        if (!scenicSpotsColumn.getScenic().getId().equals(Integer.valueOf(arr[2])) && Integer.valueOf(arr[2]) != null) {
            List<Scenic> scenics = senicService.readAll();
            for (int i = 0; i < scenics.size(); i++) {
                Scenic scenic = scenics.get(i);
                if (scenic.getId() == Integer.valueOf(arr[2])) {
                    scenicSpotsColumn.setScenic(scenic);
                }
            }
            a = 2;
        }
        if (!scenicSpotsColumn.getPlayTime().equals(arr[3]) && !arr[3].equals("")) {
            scenicSpotsColumn.setPlayTime(arr[3]);
            a = 2;
        }
        if (!scenicSpotsColumn.getOpenTime().equals(arr[4]) && !arr[4].equals("")) {
            scenicSpotsColumn.setOpenTime(arr[4]);
            a = 2;
        }
        if (!scenicSpotsColumn.getTrafficMessage().equals(arr[5]) && !arr[5].equals("")) {
            scenicSpotsColumn.setTrafficMessage(arr[5]);
            a = 2;
        }
        if (!scenicSpotsColumn.getPersonPrice().equals(arr[6]) && !arr[6].equals("")) {
            scenicSpotsColumn.setPersonPrice(arr[6]);
            a = 2;
        }
        if (!scenicSpotsColumn.getChildrenPrice().equals(arr[7]) && !arr[7].equals("")) {
            scenicSpotsColumn.setChildrenPrice(arr[7]);
            a = 2;
        }
        if (!scenicSpotsColumn.getAddress().equals(arr[8]) && !arr[8].equals("")) {
            scenicSpotsColumn.setAddress(arr[8]);
            a = 2;
        }
        if (!scenicSpotsColumn.getTicketShow().equals(arr[9]) && !arr[9].equals("")) {
            scenicSpotsColumn.setTicketShow(arr[9]);
            a = 2;
        }
        if (!scenicSpotsColumn.getMapShowName().equals(arr[10]) && !arr[10].equals("")) {
            scenicSpotsColumn.setMapShowName(arr[10]);
            a = 2;
        }
        if (!scenicSpotsColumn.getLongitude().equals(arr[11]) && !arr[11].equals("")) {
            scenicSpotsColumn.setLongitude(arr[11]);
            a = 2;
        }
        if (!scenicSpotsColumn.getLatitude().equals(arr[12]) && !arr[12].equals("")) {
            scenicSpotsColumn.setLatitude(arr[12]);
            a = 2;
        }
        if (!scenicSpotsColumn.getIntro().equals(arr[13]) && !arr[13].equals("")) {
            scenicSpotsColumn.setIntro(arr[13]);
            a = 2;
        }
        //判断景点上传照片有没有被删除 和删除的情况下
        if (!scenicSpotsColumn.getFileSourse().equals(arr[14]) && !arr[14].equals("noDelete")) {
            //更新上传FileSouse
            String[] str = scenicSpotsColumn.getFileSourse().split(",");
            String[] str1 = arr[14].split(",");
            for (int i = 0; i < str.length; i++) {
                String str2 = str[i];

                for (int j = 0; j < str1.length; j++) {
                    if (!str2.equals(str1[j])) {
                        fileSourse.append(str2 + ",");
                    }
                }
            }
            scenicSpotsColumn.setFileSourse(fileSourse.toString());
            a = 2;
            fileSourse = new StringBuffer();
        }
        if (arr[14].equals("noDelete") && fileSourse.toString().equals("")) {
            a++;
        }
        if (arr[14].equals("noDelete") && !fileSourse.toString().equals("")) {
            StringBuffer buffer = new StringBuffer(scenicSpotsColumn.getFileSourse());
            StringBuffer str = buffer.append(fileSourse);
            scenicSpotsColumn.setFileSourse(str.toString());
            a = 2;
        }

        if (a == 1) {
            resultMap.put("code", 400);
            resultMap.put("msg", "检查没有修改的部分");
            return mapToJson(resultMap);
        } else {
            //缩略图就不更改了
            //更新时间
            String updateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
            scenicSpotsColumn.setUpdatetime(updateTime);
            //将所有数据更新到数据库里面
            scenicSpotsColumnService.update(scenicSpotsColumn);

            resultMap.put("code", 200);
            resultMap.put("msg", "更新成功");
            return mapToJson(resultMap);
        }
    }

    //发布景点
    @GetMapping("/fabujingdian")
    public String faBuJingDian(Model model) {
        if(UserLoginController.id.equals("admin")){
            List<Scenic> scenics = senicService.readAll();
            model.addAttribute("scenics", scenics);
            return "fabujingdian";
        }else{
            UserLoginController.error="请用管理员权限操作";
            return  "redirect:/unauthorized";
        }

    }
    //景点列表
    int b=0;
    @RequestMapping("/jingdianliebiao")
    public String jingDianLieBiao(Model model,
                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<Scenic> scenics = senicService.readAll();
        model.addAttribute("scenics", scenics);
        PageHelper.startPage(pageNum, 3);
        List<ScenicSpotsColumn> scenicSpotsColumns = scenicSpotsColumnService.readAll();
        model.addAttribute("ScenicSpotsColumns", scenicSpotsColumns);
        PageInfo<ScenicSpotsColumn> page = new PageInfo<>(scenicSpotsColumns);
        model.addAttribute("page", page);
        model.addAttribute("pageNum", pageNum);
        //后端判断
        b=pageNum;//给后来的页面传递参数
        // pageNum：当前页，pages：末页(总页数)
        int pages = page.getPages();
        if( pages <= 3) {
                List<Integer> arr=new ArrayList<Integer>();
               for(int i=1;i<=pages;i++){
                   arr.add(i);
                model.addAttribute("arr",arr);
            }
        }
        if(pages>3){
            if(pageNum<= pages-2) {
                if ( pageNum < 3){
                    int [] arr={1,2,3};
                    model.addAttribute("arr",arr);
                }

            }
            if(pageNum>=3){
                List<Integer> arr=new ArrayList<Integer>();
                for(int i=pageNum-2; i <= pageNum+2; i++){
                    if(i<=pages) {
                        arr.add(i);
                    }
                }
                model.addAttribute("arr",arr);
            }
        }

        return "jingdianliebiao";
    }
    //发布景点返回

    //返回景点列表
    @GetMapping("/fanhuijingdianliebiao")
    public String comeBack(Model model) {
        List<Scenic> scenics = senicService.readAll();
        model.addAttribute("scenics", scenics);
        PageHelper.startPage(b, 3);
        List<ScenicSpotsColumn> scenicSpotsColumns = scenicSpotsColumnService.readAll();
        model.addAttribute("ScenicSpotsColumns", scenicSpotsColumns);
        PageInfo<ScenicSpotsColumn> page = new PageInfo<>(scenicSpotsColumns);
        model.addAttribute("page", page);
        model.addAttribute("pageNum", b);
        //后端判断

        // pageNum：当前页，pages：末页(总页数)
        int pages = page.getPages();
        if( pages <= 3) {
            int [] arr={1,2,3};
            model.addAttribute("arr",arr);
        }
        if(pages>3){
            if(b<= pages-2) {
                if ( b < 3){
                    int [] arr={1,2,3};
                    model.addAttribute("arr",arr);
                }

            }
            if(b>=3){
                List<Integer> arr=new ArrayList<Integer>();
                for(int i=b-2; i <= b+2; i++){
                    if(i<=pages) {
                        arr.add(i);
                    }
                }
                model.addAttribute("arr",arr);
            }
        }
        return "jingdianliebiao";
    }

    //查询功能search
    @RequestMapping(value = "/search", method = {RequestMethod.POST, RequestMethod.GET})
    public String search(HttpServletRequest req,
                         Model model,
                         @RequestParam("search") String title,
                         @RequestParam("Scenic") Integer scenic_id,
                         @RequestParam("begin") String startTime,
                         @RequestParam("end") String endTime,
                         @RequestParam(value = "strCur", required = false, defaultValue = "1") Integer pageNum
    ) {
        List<Scenic> scenics = senicService.readAll();
        model.addAttribute("scenics", scenics);


        PageHelper.startPage(pageNum, 3);
        if( startTime.equals(endTime)&&startTime!=""&&endTime!=""){
            List<ScenicSpotsColumn> ScenicSpotsColumns = scenicSpotsColumnService.readTime(title, scenic_id,startTime, endTime);
            model.addAttribute("ScenicSpotsColumns", ScenicSpotsColumns);
            model.addAttribute("title", title);
            model.addAttribute("scenic_id", scenic_id);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
            PageInfo<ScenicSpotsColumn> page = new PageInfo<>(ScenicSpotsColumns);
            model.addAttribute("page", page);
            //后端判断
            b=pageNum;//给后来的页面传递参数
            // pageNum：当前页，pages：末页(总页数)
            int pages = page.getPages();
            if( pages <= 3) {
                List<Integer> arr=new ArrayList<Integer>();
                for(int i=1;i<=pages;i++){
                    arr.add(i);
                    model.addAttribute("arr",arr);
                }
            }
            if(pages>3){
                if(pageNum<= pages-2) {
                    List<Integer> arr=new ArrayList<Integer>();
                    for(int i=1;i<=pages;i++){
                        arr.add(i);
                        model.addAttribute("arr",arr);
                    }

                }
                if(pageNum>=3){
                    List<Integer> arr=new ArrayList<Integer>();
                    for(int i=pageNum-2; i <= pageNum+2; i++){
                        if(i<=pages) {
                            arr.add(i);
                        }
                    }
                    model.addAttribute("arr",arr);
                }
            }
        }else{
            List<ScenicSpotsColumn> ScenicSpotsColumns = scenicSpotsColumnService.read(title, scenic_id, startTime, endTime);
            model.addAttribute("ScenicSpotsColumns", ScenicSpotsColumns);
            model.addAttribute("title", title);
            model.addAttribute("scenic_id", scenic_id);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
            PageInfo<ScenicSpotsColumn> page = new PageInfo<>(ScenicSpotsColumns);
            model.addAttribute("page", page);
            //后端判断
            b=pageNum;//给后来的页面传递参数
            // pageNum：当前页，pages：末页(总页数)
            int pages = page.getPages();
            if( pages <= 3) {
                List<Integer> arr=new ArrayList<Integer>();
                for(int i=1;i<=pages;i++){
                    arr.add(i);
                    model.addAttribute("arr",arr);
                }
            }
            if(pages>3){
                if(pageNum<= pages-2) {
                    List<Integer> arr=new ArrayList<Integer>();
                    for(int i=1;i<=pages;i++){
                        arr.add(i);
                        model.addAttribute("arr",arr);
                    }

                }
                if(pageNum>=3){
                    List<Integer> arr=new ArrayList<Integer>();
                    for(int i=pageNum-2; i <= pageNum+2; i++){
                        if(i<=pages) {
                            arr.add(i);
                        }
                    }
                    model.addAttribute("arr",arr);
                }
            }
        }


        return "jingdianliebiao";
    }


    //删除功能(一个)
    @GetMapping("/delete")
    public String deleteData(@RequestParam("id") Integer id) {
        //去掉相同的元素
        List<ScenicSpotsColumn> scenicSpotsColumns = scenicSpotsColumnService.readAll();
        for (ScenicSpotsColumn s : scenicSpotsColumns) {
            //比较数据库里面有没有对应的id数据 没有不让删除
            if (s.getId().equals(id)) {
                scenicSpotsColumnService.delete(id);
            }
        }

        return "redirect:/jingdianliebiao";//重定向;
    }

    //获取前端选勾选的id值
    List<Integer> list = new ArrayList<>();

    @RequestMapping(value = "/deleteAll")
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
    @GetMapping("/deleteData")
    public String deleteData() {
        if (list.size() > 0) {
            for (Integer id : list) {
                //去掉相同的元素
                List<ScenicSpotsColumn> scenicSpotsColumns = scenicSpotsColumnService.readAll();
                for (ScenicSpotsColumn s : scenicSpotsColumns) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (s.getId() == id) {
                        scenicSpotsColumnService.delete(id);
                    }
                }
            }
        }
        list.isEmpty();
        return "redirect:/jingdianliebiao";//重定向;
    }

    // 景点列表修改功能
    @GetMapping("/update")
    public String update(@RequestParam("id") Integer id, Model model, HttpServletRequest req) {
        ScenicSpotsColumn scenicSpotsColumn = scenicSpotsColumnService.readOne(id);
        List<Scenic> scenics = senicService.readAll();
        model.addAttribute("scenics", scenics);
        model.addAttribute("scenicSpotsColumn", scenicSpotsColumn);
        model.addAttribute("id", id);
        //获取上传照片的路径
        String sourse = scenicSpotsColumn.getFileSourse();
        //根据路径转换成数组
        String url = scenicSpotsColumn.getFileSoursePhonto();

        String[] split = sourse.split(",");
        //遍历数组里面的内容 对应查到图片的名称 大小等
        for (int i = 0; i < split.length; i++) {
            //根据地址得到文件的名称和大小
            System.out.println(split[i]);
        }

        String bbb = scenicSpotsColumn.getFileSoursePhonto();
        return "bianjifabujingdian";//重定向;
    }

}