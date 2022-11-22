package com.studycode.scheduletask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: lx
 * @Date: 2022/11/22
 * @Description:
 */
@SpringBootApplication
@EnableScheduling
public class ScheduleTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleTaskApplication.class, args);
    }
}
