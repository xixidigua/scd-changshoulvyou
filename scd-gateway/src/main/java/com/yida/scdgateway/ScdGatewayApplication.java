package com.yida.scdgateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableDiscoveryClient //开启nacos客户端
@EnableCircuitBreaker//开启熔断器
@EnableFeignClients//开启feign客户端
@MapperScan("com.yida.scdgateway.dao")//注意：必须使用通用mapper的！！
public class ScdGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScdGatewayApplication.class, args);
    }

}
