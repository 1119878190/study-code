package com.ex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/25 22:25
 **/
@MapperScan("com.ex")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void test(){
        System.out.println("--------------------init-------------------");
    }
}
