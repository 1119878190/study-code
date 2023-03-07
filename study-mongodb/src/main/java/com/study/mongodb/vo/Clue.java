package com.study.mongodb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Document(collection = "Clue")
public class Clue {
    private String id;

    //线索名称
    private String clueName;

    //录入单位 单位编码
    private String createOrg;

    // 录入单位名称
    @Transient
    private String createOrgName;

    //录入人账户
    private String createUser;

    //录入人姓名
    private String createName;

    //录入时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //线索编号
    private String clueNo;

    //涉案类型
    private String category;

    //类型名称
    private String categoryName;

    //线索来源 下拉选择：【部门移交、云端交办、警情】 //用户也可自定义填写
    private String clueSource;

    //信息来源 人/单位
    private String infoSource;

    //来源为其他来源时存的内容
    private String otherSourceContent;

    //信息来源电话 1([3][3-9]|[4][5]|[5][0-37-9]|[7][6-7]|[8][02-69])\d{8}
    @Pattern(regexp = "^$|^1[0-9]{10}$", message = "请填写正确的手机号码")
    private String infoSourcePhone;

    //简要案情
    private String briefCaseDescription;

    //线索状态
    private String clueState;

    //线索删除标记 0表示未删除   1表示已删除
    private int delFlag;


    // 嫌疑人数量
    private long suspectsNum;

    // 受害人数量
    private long victimNum;

    // 嫌疑公司数量
    private long companyNum;

    //涉案罪名
    private String caseName;

    //关联案件编号
    private String caseNo;



}