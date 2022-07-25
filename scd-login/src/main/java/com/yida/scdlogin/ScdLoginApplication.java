package com.yida.scdlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient //开启nacos客户端
@EnableFeignClients//开启feign客户端
public class ScdLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScdLoginApplication.class, args);
    }

}
