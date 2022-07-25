package com.yida.scdchangshoulvyoudemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient //开启nacos客户端
@EnableFeignClients//开启feign客户端
@MapperScan("com.yida.scdchangshoulvyoudemo.mapper")
public class ScdChangshoulvyouDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScdChangshoulvyouDemoApplication.class, args);
    }

}
