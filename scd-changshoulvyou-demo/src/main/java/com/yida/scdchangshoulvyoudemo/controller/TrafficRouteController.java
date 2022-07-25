package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yida.scdchangshoulvyoudemo.entity.TrafficRoute;
import com.yida.scdchangshoulvyoudemo.service.TrafficRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller 控制层景点增加上传
 */
@Controller
public class TrafficRouteController {
    @Autowired
    private TrafficRouteService trafficRouteService;


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


    // 更新交通路线数据里面的信息
@ResponseBody
    @RequestMapping(value = "/updateTrafficRoute", produces = "text/html;charset=utf-8")
    public String ajaxUpload(
            @RequestParam("name") String name,
            @RequestParam("longitude") String longitude,
            @RequestParam("latitude") String latitude,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<TrafficRoute> trafficRoutes = trafficRouteService.readAll();

        if (trafficRoutes.size() == 1) {
            //将所有数据写入数据库里面
            TrafficRoute t=new  TrafficRoute();
            for (int i=0;i<trafficRoutes.size();i++){
                t=trafficRoutes.get(i);
            }
                t.setId(t.getId());
                t.setName(name);
                t.setLongitude(longitude);
                t.setLatitude(latitude);
                trafficRouteService.update(t);
                resultMap.put("code", 200);
                resultMap.put("msg", "上传成功");
                return mapToJson(resultMap);


        } else {

            resultMap.put("code", 401);
            resultMap.put("msg", "上传失败");
            return mapToJson(resultMap);
        }

    }


}