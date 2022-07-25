package com.yida.scdchangshoulvyoudemo.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class KindEditor {
    //配置上传文件的目录
    public String uploadFolder = "upload/";
    //配置文件的访问url
    public String baseUrl = "http://localhost/TravelSystem/"+uploadFolder;

    //图片空间
    @RequestMapping("browser")
    public Map<String,Object> browser(HttpServletRequest request) {
        //获取上传的图片路径
        String folder = request.getServletContext().getRealPath("/")+uploadFolder;
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println("图片空间路径："+file.getAbsolutePath());

        //获取文件夹中的所有文件
        File[] files = file.listFiles();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("is_dir",false);
            map.put("has_file",false);
            map.put("filesize",files[i].length());
            map.put("is_photo",true);
            map.put("filetype", FilenameUtils.getExtension(files[i].getName()));
            map.put("filename",files[i].getName());
            map.put("datetime",new Date());
            list.add(map);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("current_url",baseUrl);
        map.put("total_count",files.length);
        map.put("file_list",list);

        return map;
    }

    //注意：我们要在根目录下手工创建一个public文件夹！！！
    //本地上传
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile aa, HttpServletRequest request) throws IOException {
        //获取上传的图片名称
        String fileName = aa.getOriginalFilename();
        System.out.println("上传的图片名：" + fileName);

        //创建文件上传目录
        String folder = request.getServletContext().getRealPath("/")+uploadFolder;
        File uploadDir = new File(folder);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        System.out.println("图片上传路径："+uploadDir.getAbsolutePath());

        //图片url
        String url = baseUrl + fileName;
        System.out.println("图片url："+url);

        //文件上传
        File file = new File(uploadDir.getAbsolutePath(), fileName);
        aa.transferTo(file);

        //返回 需要的返回值
        Map<String, Object> map = new HashMap<>();
        map.put("error",0);
        map.put("url",url);

        return map;
    }
}
