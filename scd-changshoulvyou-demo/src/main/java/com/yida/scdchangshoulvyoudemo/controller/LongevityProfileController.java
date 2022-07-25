package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yida.scdchangshoulvyoudemo.entity.LongevityProfile;
import com.yida.scdchangshoulvyoudemo.service.LongevityProfileService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller 控制层长寿上传
 */
@Controller
public class LongevityProfileController {
    StringBuffer fileSoursePhonto = new StringBuffer(10000);
    @Autowired
    private LongevityProfileService longevityProfileService;

    @ResponseBody
    @RequestMapping(value = "/ajaxUploadLongevityProfiles", produces = "text/html;charset=utf-8")
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
                    fileSoursePhonto.append(fileName + ":" + strSize + ":" + str + ",");

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
    @RequestMapping(value = "/ajaxUploadLongevityProfile", produces = "text/html;charset=utf-8")
    public String ajaxUploadFilesAndField(
            @RequestParam("name") String name,
            @RequestParam("content") String[] content,
            @RequestParam("hidden") String[] string,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = name == "" && content[1].equals("")&& fileSoursePhonto.equals("") ;
        if (bean) {
            resultMap.put("code", 405);
            resultMap.put("msg", "查看是否有需要输入的没输入");
            return mapToJson(resultMap);
        }

        //将所有数据更新数据库里面
        List<LongevityProfile> longevityProfiles = longevityProfileService.readAll();

        if (longevityProfiles.size() == 1) {
            //将所有数据写入数据库里面
            LongevityProfile L=new  LongevityProfile();
            for (int i=0;i<longevityProfiles.size();i++){
                L=longevityProfiles.get(i);
            }
            L.setId(L.getId());
            L.setName(name);
            L.setFileSourse(content[1]);
            // 对前端传过来的数据进行比较
            StringBuffer buff=new StringBuffer();
            for(int b=0;b<string.length;b++){
                buff.append(string[b]+",");
            }
            String str1=buff.toString();
            if (!L.getFileSoursePhonto().equals(str1) &&! str1 .equals("")) {
                //更新上传FileSouse
                String[] str = L.getFileSoursePhonto().split(",");
                String[] str2 = str1.split(",");
                for (int i = 0; i < str.length; i++) {
                    String str3 = str[i];

                    for (int j = 0; j < string.length; j++) {
                        if (!str3 .equals(string[j]) ) {
                            if(str.length-str2.length==1){
                                fileSoursePhonto.append(str3+ ",");
                                j=string.length;
                                i=str.length;
                            }else {
                                fileSoursePhonto.append(str3 + ",");
                            }
                        }
                    }
                }
               L.setFileSoursePhonto(fileSoursePhonto.toString());
            }
            if (str1.equals("")&&!fileSoursePhonto.toString().equals("")) {
                StringBuffer buffer=new StringBuffer(L.getFileSoursePhonto());
                StringBuffer str = fileSoursePhonto.append(buffer);
                L.setFileSoursePhonto(str.toString());
            }

            fileSoursePhonto = new StringBuffer();
            longevityProfileService.update(L);
            resultMap.put("code", 200);
            resultMap.put("msg", "上传成功");
            return mapToJson(resultMap);


        } else {

            resultMap.put("code", 401);
            resultMap.put("msg", "上传失败");
            return mapToJson(resultMap);
        }


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

}