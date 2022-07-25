package com.yida.scdlogin.feign;

import com.yida.common.R;
import com.yida.common.Service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= Service.SERVICE_GATEWAY)
public interface AuthServiceByFeign {
@PostMapping("/gateway/loginData")
    public R checkTokenByPost(@RequestParam("name")String  name,
                              @RequestParam("password")String password);

}


