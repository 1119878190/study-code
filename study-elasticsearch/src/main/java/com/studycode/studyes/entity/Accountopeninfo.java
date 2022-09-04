package com.studycode.studyes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "tldw_accountopeninfo", shards = 3)
public class Accountopeninfo{
    /** 全局唯一id */
    @Id
    private Long id;

    // 资源id
    private String resource_id;
    /**
     * 案件编号
     */
    private String case_id;


    private String account_name;


    private String account_owner_identity_card;

    // 交易账号

    private String trade_account;


    private String trade_card;


    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  account_open_time;


    private BigDecimal account_balance;


    private BigDecimal available_balance;


    private String currency_type;

    private String account_open_network_code;


    private String account_open_network_address;


    private String account_status;


    private String chaohui_sign_name;


    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date account_cancel_date;

    // 账户类型
    private String account_type;

    // 开户联系方式
    private String account_open_contact_mode;

    // 通信地址
    private String mailing_address;

    // 联系电话
    private String contact_number;

    // 代理人
    private String agent;

    // 代理人电话
    private String agent_telephone;

    // 备注
    private String remarks;

    // 开户省份
    private String account_open_pro;

    // 开户城市
    private String account_open_city;

    // 账号开户银行
    private String account_open_bank;

    // 客户代码
    private String customer_code;

    // 法人代表
    private String legal_rep;

    // 客户工商执照号码
    private String customer_bus_lic_code;

    // 法人代表证件号码
    private String legal_rep_cert_num;

    // 住宅地址
    private String home_address;

    // 邮政编码
    private String postal_code;

    // 代办人证件号码
    private String agent_cert_num;

    // 邮箱地址
    private String email_address;

    // 关联资金账户
    private String relation_capital_account;

    // 地税纳税号
    private String local_tax_no;

    // 单位电话
    private String company_telephone;

    // 代办人证件类型
    private String agent_cert_type;

    // 住宅电话
    private String home_telephone;

    // 法人代表证件类型
    private String legal_rep_cert_type;

    // 国税纳税号
    private String state_tax_no;

    // 单位地址
    private String company_address;

    // 工作单位
    private String work_company;

    // 销户网点
    private String account_cancel_network;

    // 账户销户银行
    private String account_cancel_bank;

    // 最后交易时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date last_trade_time;



}
