package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yida.scdchangshoulvyoudemo.entity.Classify;
import com.yida.scdchangshoulvyoudemo.entity.VideoColumn;
import com.yida.scdchangshoulvyoudemo.service.ClassifyService;
import com.yida.scdchangshoulvyoudemo.service.VideoColumnService;
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
public class AddVideoController {
    StringBuffer fileVideoSourse = new StringBuffer(10000);
    StringBuffer fileSoursePhonto = new StringBuffer(10000);

    @Autowired
    private VideoColumnService videoColumnService;
    @Autowired
    private ClassifyService classifyService;

    @ResponseBody
    @RequestMapping(value = "/ajaxUploadVideoFields", produces = "text/html;charset=utf-8")

    public String ajaxUploadVideoFields(
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
                if (type.endsWith("mp4") || type.endsWith("3GP") || type.endsWith("rmvb")) {
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
                    fileVideoSourse.append(fileName + ":" + strSize + ":" + str + ",");
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
    @RequestMapping(value = "/ajaxUploadVideoField", produces = "text/html;charset=utf-8")
    public String ajaxUploadVideoField(
            @RequestParam("yida") MultipartFile[] files,
            @RequestParam("size") String[] size,
            HttpServletRequest request
    ) throws Exception {
      String title = request.getParameter("title");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取景点的value值
        String value = request.getParameter("Classify");
        Integer id = Integer.parseInt(value);
        Classify classify = new Classify();
        List<Classify> classifies= classifyService.readAll();
       classify = classifies.get(id - 1);


        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = title == "" ;
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
        VideoColumn videoColumn = new VideoColumn();
        videoColumn.setTitle(title);
        videoColumn.setClassify(classify);
        videoColumn.setFileVideoSourse(fileVideoSourse.toString());
        //根据fileVideoSourse获取总的文件大小
        fileVideoSourse= new StringBuffer();
        String videoTotalSize=size[0];
        videoColumn.setVideoTotalSize(videoTotalSize);
       videoColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        fileSoursePhonto = new StringBuffer();
        videoColumn.setUpdateTime(updateTime);
       videoColumnService.add(videoColumn);


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

    //视频列表删除功能
    //删除功能(一个)
    @GetMapping("/deleteVideo")
    public String deleteData(@RequestParam("id") Integer id,HttpServletRequest request) {
        //去掉相同的元素
        List<VideoColumn> videoColumns = videoColumnService.readAll();
        for (VideoColumn v : videoColumns) {
            //比较数据库里面有没有对应的id数据 没有不让删除
            if (v.getId() == id) {
                videoColumnService.delete(id);
            }
        }
        return "redirect:/shipinliebiao";//重定向;
    }

   //获取前端选勾选的id值
    List<Integer> list = new ArrayList<>();

    @RequestMapping(value = "/deleteAllVideo")
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
    @GetMapping("/deleteAllVideo")
    public String deleteData() {
        if (list.size() > 0) {
            for (Integer id : list) {
                //去掉相同的元素
                List<VideoColumn> videoColumns = videoColumnService.readAll();
                for (VideoColumn v : videoColumns) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (v.getId() == id) {
                        videoColumnService.delete(id);
                    }
                }
            }
        }
        list.clear();
        return "redirect:/shipinliebiao";//重定向;
    }
    // 视频列表修改功能
    @GetMapping("/updateVideo")
    public String update(@RequestParam("id") Integer id, Model model, HttpServletRequest req) {
        VideoColumn videoColumn = videoColumnService.readOne(id);
        List<Classify> classifies = classifyService.readAll();
        model.addAttribute("classifies", classifies);
        model.addAttribute("videoColumn", videoColumn);
        model.addAttribute("id", id);

        return "bianjifabushipin";//重定向;
    }


    //修改了缩略图情况下 更新
    @ResponseBody
    @RequestMapping(value = "/ajaxUploadVideos", produces = "text/html;charset=utf-8")
    public String ajaxUpload(
            @RequestParam("yida") MultipartFile[] files,
            @RequestParam("id") String ids,
            @RequestParam("size") String[] size,
            @RequestParam("hidden") String[] string,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String title = request.getParameter("title");

        Integer idd = Integer.valueOf(ids);
        //获取景点的value值
        String value = request.getParameter("Classify");
        Integer id = Integer.parseInt(value);
        Classify classify= new Classify();
        List<Classify> classifies = classifyService.readAll();
        classify = classifies.get(id - 1);


        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = title.equals("") ;
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
       VideoColumn videoColumn = videoColumnService.readOne(idd);
        if(string.length>0) {
            StringBuffer buff = new StringBuffer();
            for (int b = 0; b < string.length; b++) {
                buff.append(string[b] + ",");
            }
            String str1 = buff.toString();
            if (!videoColumn.getFileVideoSourse().equals(str1) && !str1.equals("")) {
                //更新上传FileSouse
                String[] str = videoColumn.getFileVideoSourse().split(",");
                String[] str2 = str1.split(",");
                for (int i = 0; i < str.length; i++) {
                    String str3 = str[i];

                    for (int j = 0; j < string.length; j++) {
                        if (!str3.equals(string[j])) {
                            fileVideoSourse.append(str3 + ",");
                        }
                    }
                }
                videoColumn.setFileVideoSourse(fileVideoSourse.toString());
            }
            if (str1.equals("") && !fileVideoSourse.toString().equals("")) {
                StringBuffer buffer = new StringBuffer(videoColumn.getFileVideoSourse());
                StringBuffer str = fileVideoSourse.append(buffer);
                videoColumn.setFileVideoSourse(str.toString());
            }
        }else {
            fileVideoSourse.append(videoColumn.getFileVideoSourse());
        }
        //将所有数据写入数据库里面
        //获取当前时间
        String updateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
        videoColumn.setId(idd);
        videoColumn.setTitle(title);
        videoColumn.setClassify(classify);
       videoColumn.setFileVideoSourse(fileVideoSourse.toString());
        //根据fileVideoSourse获取总的文件大小
        String videoTotalSize=size[0];
        videoColumn.setVideoTotalSize(videoTotalSize);
        videoColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        videoColumn.setUpdateTime(updateTime);
        videoColumnService.update(videoColumn);
        fileSoursePhonto = new StringBuffer();
        fileVideoSourse= new StringBuffer();
        resultMap.put("code", 200);
        resultMap.put("msg", "上传成功");
        return mapToJson(resultMap);

    }

    //不改变缩略图的情况下 删除或没删除 删除景点照片情况下 更新数据库信息
    @ResponseBody
    @RequestMapping(value = "/ajaxUploadVideo", produces = "text/html;charset=utf-8")
    public String ajaxUploadForm(
            @RequestParam("arr") String[] arr,
            HttpServletRequest request
    ) throws Exception {
        //读取数据库里面的信息
        Integer id = Integer.valueOf(arr[0]);
       VideoColumn videoColumn = videoColumnService.readOne(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //对数据库里面的字段和前端传过来的比较 组成新的ScenicSpotsColumn对象 存入数据库
        int a=0;
        if (!videoColumn.getTitle().equals(arr[1]) && !arr[1].equals("")) {
            videoColumn.setTitle(arr[1]);
            a=2;
        }
        if (!videoColumn.getClassify().getId().equals(Integer.valueOf(arr[2])) &&Integer.valueOf(arr[2]) != null) {
            List<Classify> classifies= classifyService.readAll();
            for (int i = 0; i < classifies.size(); i++) {
                Classify classify = classifies.get(i);
                if (classify.getId() == Integer.valueOf(arr[2])) {
                    videoColumn.setClassify(classify);
                }
            }
            a=2;
        }

        //判断景点上传照片有没有被删除 和删除的情况下
        if (!videoColumn.getFileVideoSourse().equals( arr[3]) &&! arr[3] .equals("noDelete")) {
            //更新上传FileSouse
            String[] str = videoColumn.getFileVideoSourse().split(",");
            String[] str1 = arr[3].split(",");
            for (int i = 0; i < str.length; i++) {
                String str2 = str[i];

                for (int j = 0; j < str1.length; j++) {
                    if (!str2 .equals(str1[j]) ) {
                      fileVideoSourse.append(str2 + ",");
                    }
                }
            }
            videoColumn.setFileVideoSourse(fileVideoSourse.toString());
            a=2;
            fileVideoSourse = new StringBuffer();
        }
        if (arr[3].equals("noDelete")&&fileVideoSourse.toString().equals("")) {
            a++;
        }
        if (arr[3].equals("noDelete")&&!fileVideoSourse.toString().equals("")) {
            StringBuffer buffer=new StringBuffer(  videoColumn.getFileVideoSourse());
            StringBuffer str = buffer.append(fileVideoSourse );
            videoColumn.setFileVideoSourse(str.toString());
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
           videoColumn.setUpdateTime(updateTime);
            //将所有数据更新到数据库里面
            videoColumnService.update(videoColumn);

            resultMap.put("code", 200);
            resultMap.put("msg", "更新成功");
            return mapToJson(resultMap);
        }
    }
  //查询视频
  //查询功能searchVideoColumn
  @RequestMapping( value = "/searchVideoColumn", method = {RequestMethod.POST, RequestMethod.GET})
  public String  searchVideo (HttpServletRequest req,
                                Model model,
                                @RequestParam("search") String title,
                              @RequestParam("Classify") Integer classify_id,
                                @RequestParam("begin") String startTime,
                                @RequestParam("end") String endTime,
                                @RequestParam(value = "strCur", required = false, defaultValue = "1") Integer pageNum
  ) {
      List<Classify> classifies = classifyService.readAll();
      model.addAttribute("classifies", classifies);

      PageHelper.startPage(pageNum, 3);
     List<VideoColumn> videoColumns  = videoColumnService.read(title,classify_id,startTime,endTime);
     //List<VideoColumn> videoColumns = videoColumnService.readAll();
      model.addAttribute("title",title);
      model.addAttribute("classify_id",classify_id);
      model.addAttribute("startTime",startTime);
      model.addAttribute("endTime",endTime);
      model.addAttribute("videoColumns", videoColumns);
      PageInfo<VideoColumn> page = new PageInfo<>(videoColumns);
      model.addAttribute("page", page);
      return  "shipinliebiao";
  }

}