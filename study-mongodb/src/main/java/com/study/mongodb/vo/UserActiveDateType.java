package com.study.mongodb.vo;

import lombok.Getter;

/**
 * @Author: lx
 * @Date: 2023/02/17
 * @Description:
 */
@Getter
public enum UserActiveDateType {


    /**
     * 近一周
     */
    lastWeek,

    /**
     * 近一个月
     */
    lastMonth,


    /**
     * 近一年
     */
    lastYear

}
