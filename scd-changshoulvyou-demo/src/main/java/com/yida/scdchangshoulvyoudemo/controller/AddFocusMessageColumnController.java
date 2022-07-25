package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yida.scdchangshoulvyoudemo.entity.FocusMessageColumn;
import com.yida.scdchangshoulvyoudemo.service.FocusMessageColumnService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AddFocusMessageColumnController {
    // @RequestMapping("write")
   /* public String getWrite(){
        return "write";
    }*/

    @Autowired
    private FocusMessageColumnService focusMessageColumnService;

    StringBuffer fileSoursePhonto = new StringBuffer(10000);
    @ResponseBody
    @RequestMapping(value = "/EditServlet", produces = "text/html;charset=utf-8")
    public String EditServlet(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("yida") MultipartFile[] files,
                            @RequestParam("title") String title,
                            @RequestParam("content") String[] content
    ) throws IOException {
        //实现文件上传功能
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = title.equals("") || content[1].equals("") || fileSoursePhonto.equals("") ;
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
        FocusMessageColumn focusMessageColumn = new FocusMessageColumn();
        focusMessageColumn.setTitle(title);
        String fileSourse=content[1];
        focusMessageColumn.setFileSourse(fileSourse);
        focusMessageColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        focusMessageColumn.setUpdateTime(updateTime);
        focusMessageColumnService.add(focusMessageColumn);
        fileSoursePhonto = new StringBuffer();
        resultMap.put("code", 200);
        resultMap.put("msg", "上传成功");
        return mapToJson(resultMap);
    }

    //以下是对文件上传的重复代码
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
    //焦点信息列表删除功能
    //删除功能(一个)
    @GetMapping("/deleteFocusMessage")
    public String deleteData(@RequestParam("id") Integer id,HttpServletRequest request) {
        //去掉相同的元素
        List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAll();
        for (FocusMessageColumn f : focusMessageColumns) {
            //比较数据库里面有没有对应的id数据 没有不让删除
            if (f.getId() == id) {
                focusMessageColumnService.delete(id);
            }
        }
        return "redirect:/jiaodianxinxiliebiao";//重定向;
    }

    //获取前端选勾选的id值
    List<Integer> list = new ArrayList<>();

    @RequestMapping(value = "/deleteAllFocusMessage")
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
    @GetMapping("/deleteAllFocusMessage")
    public String deleteData() {
        if (list.size() > 0) {
            for (Integer id : list) {
                //去掉相同的元素
                List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAll();
                for (FocusMessageColumn f : focusMessageColumns) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (f.getId() == id) {
                        focusMessageColumnService.delete(id);
                    }
                }
            }
        }
        list.clear();
        return "redirect:/jiaodianxinxiliebiao";//重定向;
    }
    //焦点信息列表更新功能
    // 修改
    @GetMapping("/updateFocusMessage")
    public String update(@RequestParam("id") Integer id, Model model, HttpServletRequest req) {
        FocusMessageColumn focusMessageColumn = focusMessageColumnService.readOne(id);
        model.addAttribute("focusMessageColumn",focusMessageColumn);
        model.addAttribute("id", id);

        return "bianjifabujiaodianxiaoxi";
    }
    //修改了缩略图情况下 更新
    @ResponseBody
    @RequestMapping(value = "/updateFocusMessageColumn", produces = "text/html;charset=utf-8")
    public String updateFocusMessageColumn(
            @RequestParam("yida") MultipartFile[] files,
            @RequestParam("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("content") String[] content,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Boolean bean = title.equals("") && content[1].equals("")&& fileSoursePhonto.equals("") ;
        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
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
        FocusMessageColumn focusMessageColumn = focusMessageColumnService.readOne(id);
        String updateTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
      focusMessageColumn.setId(id);
      focusMessageColumn.setTitle(title);
        String fileSourse=content[1];
        focusMessageColumn.setFileSourse(fileSourse);
        focusMessageColumn.setFileSoursePhonto(fileSoursePhonto.toString());
        focusMessageColumn.setUpdateTime(updateTime);
       focusMessageColumnService.update(focusMessageColumn);
        fileSoursePhonto = new StringBuffer();
        resultMap.put("code", 200);
        resultMap.put("msg", "上传成功");
        return mapToJson(resultMap);

    }

    //不改变缩略图的情况下 删除或没删除 删除景点照片情况下 更新数据库信息
    @ResponseBody
    @RequestMapping(value = "/updateFocusMessageColumns", produces = "text/html;charset=utf-8")
    public String ajaxUploadForm(
            @RequestParam("arr") String[] arr,
            HttpServletRequest request
    ) throws Exception {
        //读取数据库里面的信息
        Integer id = Integer.valueOf(arr[2]);;
        FocusMessageColumn focusMessageColumn = focusMessageColumnService.readOne(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //对数据库里面的字段和前端传过来的比较 组成新的ScenicSpotsColumn对象 存入数据库
        int a=0;
        if (focusMessageColumn.getFileSourse().equals(arr[1]) && focusMessageColumn.getTitle().equals(arr[0])) {
            a=1;
        }
        if (!focusMessageColumn.getTitle().equals(arr[0]) && !arr[0].equals("")) {
            focusMessageColumn.setTitle(arr[0]);
            a=2;
        }
        if (!focusMessageColumn.getFileSourse().equals(arr[1]) && !arr[1].equals("")) {
            focusMessageColumn.setFileSourse(arr[1]);
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
            focusMessageColumn.setFileSoursePhonto(focusMessageColumn.getFileSoursePhonto());
            focusMessageColumn.setUpdateTime(updateTime);
            //将所有数据更新到数据库里面
            focusMessageColumnService.update(focusMessageColumn);
            resultMap.put("code", 200);
            resultMap.put("msg", "更新成功");
            return mapToJson(resultMap);
        }
    }
    //查询功能searchFocusMessageColumn
    @RequestMapping( value = "/searchFocusMessageColumn", method = {RequestMethod.POST, RequestMethod.GET})
    public String  search (HttpServletRequest req,
                           Model model,
                           @RequestParam("search") String title,
                           @RequestParam("begin") String startTime,
                           @RequestParam("end") String endTime,
                           @RequestParam(value = "strCur", required = false, defaultValue = "1") Integer pageNum
    ) {


        PageHelper.startPage(pageNum, 3);
        List<FocusMessageColumn> focusMessageColumns  = focusMessageColumnService.read(title,startTime,endTime);
        model.addAttribute("focusMessageColumns",focusMessageColumns);
        model.addAttribute("title",title);

        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        PageInfo<FocusMessageColumn> page = new PageInfo<>(focusMessageColumns);
        model.addAttribute("page", page);

        return  "jiaodianxinxiliebiao";
    }

//消息列表删除功能
//删除功能(一个)
@GetMapping("/deleteMessage")
public String deleteMessage(@RequestParam("id") Integer id,HttpServletRequest request) {
    //去掉相同的元素
    List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAll();
    for (FocusMessageColumn f : focusMessageColumns) {
        //比较数据库里面有没有对应的id数据 没有不让删除
        if (f.getId() == id) {
            focusMessageColumnService.delete(id);
        }
    }
    return "redirect:/xiaoxiliebiao";//重定向;
}

    //获取前端选勾选的id值
    List<Integer> list1 = new ArrayList<>();

    @RequestMapping(value = "/deleteAllMessage")
    public void deleteAllMessage(HttpServletRequest req) {
        String[] ids = req.getParameterValues("array[]");
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Integer id = Integer.parseInt(ids[i]);
                list1 .add(id);
            }
        }

    }

    //删除功能(多删除)
    @GetMapping("/deleteAllMessage")
    public String deleteAllData() {
        if (list1 .size() > 0) {
            for (Integer id : list1 ) {
                //去掉相同的元素
                List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAllMessage();
                for (FocusMessageColumn f : focusMessageColumns) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (f.getId() == id) {
                        focusMessageColumnService.delete(id);
                    }
                }
            }
        }
        list1.clear();
        return "redirect:/xiaoxiliebiao";//重定向;
    }
    //消息列表设置焦点非焦点功能
//设置焦点功能(一个)
    @GetMapping("/setFocusMessage")
    public String setFocusMessage(@RequestParam("id") Integer id,HttpServletRequest request) {
        //去掉相同的元素
        List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAllMessage();
        for (FocusMessageColumn f : focusMessageColumns) {
            //比较数据库里面有没有对应的id数据 没有不让修改
            if (f.getId() == id) {
                if(f.getLogo()==1){
                   f.setLogo(0);
                    focusMessageColumnService.setFocus(f);
                }else {
                    f.setLogo(1);
                    focusMessageColumnService.setFocus(f);
                }

            }
        }
        return "redirect:/xiaoxiliebiao";//重定向;
    }

    //获取前端选勾选的id值
    List<Integer> list2 = new ArrayList<>();
    @RequestMapping(value = "/setFocusAllMessage")
    public void setFocusAllMessage(HttpServletRequest req) {
        String[] ids = req.getParameterValues("array[]");
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Integer id = Integer.parseInt(ids[i]);
              //过滤掉重复选择
            list2.add(id);
        }
        }

    }

    //焦点修改功能(多修改)
    @GetMapping("/setFocusAllMessage")
    public String setAllData() {
        if (list2 .size() > 0) {
            for (Integer id : list2 ) {
                //去掉相同的元素
                List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAllMessage();
                for (FocusMessageColumn f : focusMessageColumns) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (f.getId() == id) {

                            if (f.getLogo() == 1) {
                                f.setLogo(0);
                                focusMessageColumnService.setFocus(f);
                            } else {
                                f.setLogo(1);
                                focusMessageColumnService.setFocus(f);
                            }
                    }
                }
            }
        }
      list2.clear();
        return "redirect:/xiaoxiliebiao";//重定向;
    }
    //更新已发布消息
    // 修改
    @GetMapping("/updateMessage")
    public String updateMessage(@RequestParam("id") Integer id, Model model, HttpServletRequest req) {
        FocusMessageColumn focusMessageColumn = focusMessageColumnService.readOne(id);
        model.addAttribute("focusMessageColumn",focusMessageColumn);
        model.addAttribute("id", id);

        return "bianjifabuxiaoxi";
    }
    //查询消息
    //查询功能searchMessageColumn
    @RequestMapping( value = "/searchMessageColumn", method = {RequestMethod.POST, RequestMethod.GET})
    public String  searchMessage (HttpServletRequest req,
                           Model model,
                           @RequestParam("search") String title,
                           @RequestParam("begin") String startTime,
                           @RequestParam("end") String endTime,
                           @RequestParam(value = "strCur", required = false, defaultValue = "1") Integer pageNum
    ) {


        PageHelper.startPage(pageNum, 3);
        List<FocusMessageColumn> focusMessageColumns  = focusMessageColumnService.readMessage(title,startTime,endTime);
        model.addAttribute("focusMessageColumns",focusMessageColumns);
        model.addAttribute("title",title);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        PageInfo<FocusMessageColumn> page = new PageInfo<>(focusMessageColumns);
        model.addAttribute("page", page);
        return  "xiaoxiliebiao";
    }
}
