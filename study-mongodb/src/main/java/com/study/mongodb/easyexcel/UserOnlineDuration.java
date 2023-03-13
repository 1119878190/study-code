package com.study.mongodb.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户在线时间
 *
 * @author dell
 * @Author: lx
 * @Date: 2023/02/20
 * @Description:  用户当天在线总时长，以天为单位记录
 * @date 2023/02/20
 */
@Document(collection = "UserOnlineDuration")
@Data
@AllArgsConstructor
@NoArgsConstructor
@HeadRowHeight(25)
@ColumnWidth(20)
@ContentRowHeight(60)
public class UserOnlineDuration {

    @Id
    @ExcelIgnore
    private String id;

    /**
     * 账户
     */
    @ExcelProperty(value = "账号", index = 0)
    private String account;

    /**
     * 在线时间  单位为秒
     */
    @ExcelProperty(value = "在线时长", index = 1)
    private Long onlineDuration;

    /**
     * 最后计算在线时长 登录 的 logTime
     */
    @ExcelIgnore
    private Date recordLoginDate;

    /**
     * 在线日期   按天记录
     */
    @ExcelIgnore
    private Date onLineDate;

    @ExcelProperty(value = "头像", index = 2)
    private byte[] img;


}