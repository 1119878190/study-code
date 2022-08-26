package com.studycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @Author: lx
 * @Date: 2022/08/26
 * @Description:
 */
@SpringBootApplication
public class FileReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileReadApplication.class, args);
    }

    @PostConstruct
    public void init(){
        System.out.println("=================hello world=====================");
    }
}
