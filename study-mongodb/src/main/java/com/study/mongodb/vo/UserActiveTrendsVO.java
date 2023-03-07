package com.study.mongodb.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: lx
 * @Date: 2023/02/17
 */
@Data
@Builder
public class UserActiveTrendsVO {


    private List<String> dates;

    private List<Integer> counts;
}
