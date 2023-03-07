package com.study.mongodb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginStatusCount {
    private String date;
    private int loginCount;
    private List<String> users;

    private Date logTime;

    private String actor;

    // getters and setters
}