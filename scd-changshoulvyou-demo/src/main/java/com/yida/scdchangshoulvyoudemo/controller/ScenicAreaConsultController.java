package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yida.scdchangshoulvyoudemo.entity.ScenicAreaConsult;
import com.yida.scdchangshoulvyoudemo.service.ScenicAreaConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller 控制层景点增加上传
 */
@Controller
public class ScenicAreaConsultController {
    @Autowired
    private ScenicAreaConsultService scenicAreaConsultService;
    @ResponseBody
    @RequestMapping(value = "/addScenicAreaConsult", produces = "text/html;charset=utf-8")
    public String ajaxUploadVideoField(
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //判断前端传到服务器的数据是否为空，为空不能执行写入数据库
        Boolean bean = name == ""&&phoneNumber =="" ;
        if (bean) {
            resultMap.put("code", 401);
            resultMap.put("msg", "上传失败");
            return mapToJson(resultMap);
        }

        //将所有数据写入数据库里面
        ScenicAreaConsult scenicAreaConsult = new ScenicAreaConsult();
        scenicAreaConsult.setName(name);
        scenicAreaConsult.setPhoneNumber(phoneNumber);
       scenicAreaConsultService.add(scenicAreaConsult);
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



   //景区咨询删除功能
    //删除功能(一个)
    @GetMapping("/deleteScenicAreaConsult")
    public String deleteData(@RequestParam("id") Integer id,HttpServletRequest request) {
        //去掉相同的元素
        List<ScenicAreaConsult> scenicAreaConsults = scenicAreaConsultService.readAll();
        for (ScenicAreaConsult s : scenicAreaConsults) {
            //比较数据库里面有没有对应的id数据 没有不让删除
            if (s.getId() == id) {
                scenicAreaConsultService.delete(id);
            }
        }
        return "redirect:/jingquzixun";//重定向;
    }

   //获取前端选勾选的id值
    List<Integer> list = new ArrayList<>();

    @RequestMapping(value = "/deleteAllScenicAreaConsult")
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
    @GetMapping("/deleteAllScenicAreaConsult")
    public String deleteData() {
        if (list.size() > 0) {
            for (Integer id : list) {
                //去掉相同的元素
                List<ScenicAreaConsult> scenicAreaConsults = scenicAreaConsultService.readAll();
                for (ScenicAreaConsult s : scenicAreaConsults) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (s.getId() == id) {
                        scenicAreaConsultService.delete(id);
                    }
                }
            }
        }
        list.clear();
        return "redirect:/jingquzixun";//重定向;
    }


}