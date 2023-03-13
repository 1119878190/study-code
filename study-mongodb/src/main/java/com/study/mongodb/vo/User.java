package com.study.mongodb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 4273617050148274838L;
    @Id
    private String id;
    private String account;
    private String name;
    private String ssn;
    private String email;
    private String password;
    private UserState state = UserState.NORMAL;
    private Date createDate;
    private String groupPath;
    private List<String> tableRoleNames;
    /**
     *
     */
    private String expiredDate;
    private Date lastLoginDate;
    private String organizationId;
    private List<String> roles;
    private String groupId;

    private String gender; // "male" | "female"
    private String mobileNumber;
    private String nickName;
    private String header;
    private boolean active = true;

    private boolean simulated;
    /**
     * 来源标识: 1 代表的是成功系统的用户信息 2 代表的是我方用户信息
     */
    private Integer sourceType;
    /**
     * 柳州jz 修改密码需求 md5 加密
     */
    private String cloudPlatformPassword;
    /**
     * 部门
     */
    private String department;
    /**
     * 部门编号
     */
    private String departmentNo;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 政治面貌
     */
    private String politicalStatus;
    /**
     * 民族
     */
    private String nation;
    /**
     * 籍贯
     */
    private String jiGuan;
    /**
     * 职务
     */
    private String station;

    /**
     * 警号
     */
    private String policeId;


    public static enum UserState {
        NORMAL, INACTIVE, CANCELLATION, DELETED;
    }
}
