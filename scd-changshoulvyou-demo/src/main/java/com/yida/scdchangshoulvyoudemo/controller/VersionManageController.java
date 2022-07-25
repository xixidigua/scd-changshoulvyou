package com.yida.scdchangshoulvyoudemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yida.scdchangshoulvyoudemo.entity.Client;
import com.yida.scdchangshoulvyoudemo.entity.VersionManage;
import com.yida.scdchangshoulvyoudemo.service.ClientService;
import com.yida.scdchangshoulvyoudemo.service.VersionManageService;
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
public class VersionManageController {
    @Autowired
    private VersionManageService versionManageService;
    @Autowired
    private ClientService clientService;

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
    @RequestMapping(value = "/updateVersionManage", produces = "text/html;charset=utf-8")
    public String ajaxUpload(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("content") String content,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取景点的value值
        String value = request.getParameter("Classify");
        Integer id = Integer.parseInt(value);
        Client client = new Client();
        List<Client> clients = clientService.readAll();
        client = clients.get(id - 1);

        List<VersionManage> versionManages = versionManageService.readAll();

        if (versionManages.size() == 1) {
            //将所有数据写入数据库里面
            VersionManage v = new VersionManage();
            for (int i = 0; i < versionManages.size(); i++) {
                v = versionManages.get(i);
            }
            v.setId(v.getId());
            v.setClient(client);
            v.setName(name);
            v.setAddress(address);
            v.setContent(content);
            versionManageService.update(v);
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