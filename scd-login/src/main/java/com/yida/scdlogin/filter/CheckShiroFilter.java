package com.yida.scdlogin.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yida.common.R;
import com.yida.scdlogin.feign.AuthServiceByFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class CheckShiroFilter {
    @Resource
    AuthServiceByFeign authServiceByFeign;
    @PostMapping("/loginData")
    public String usersByPost(
            @RequestParam("name")String  name,
            @RequestParam("password")String password,HttpSession session ){
        R   r= authServiceByFeign.checkTokenByPost(name, password);
        r.put("user",name);
         return mapToJson(r);

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
}
