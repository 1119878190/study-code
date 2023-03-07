package com.study.mongodb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lx
 * @Date: 2023/02/22
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTopVO {

    List<UserTopVO> userTop;

    List<OrgTopVO> orgTop;

}
