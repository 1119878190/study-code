package com.studycode.studyes.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Transactions
 * @Description 银行交易流水
 * @Author zhangkehou、machengjun
 * @Date 2021/8/20 13:50
 */
@Data
@Document(indexName = "tldw_banktransactionflow", shards = 3)
public class BankTransactionFlow {


    /**
     * 全局唯一id
     */
    @Id
    private Long id;

    /**
     * 案件编号
     */
    private String case_id;

    /**
     * 案件编号（长整型）
     */
    private Long case_key_id;

    /**
     * 资源id
     */
    private String resource_id;

    /**
     * 资源id(长整型)
     */
    private String resource_key_id;

    /**
     * 银行名称
     * fields 多字段类型在 mapping 创建之后, 是可以继续更新的(另外Object 对象也可以添加新的属性, 字段还可以添加 ignore_above属性)
     * 以上这种三种情况 , 不用着急reindex更新索引，直接更新Mapping也是可以的
     */
    private String bank;

    /**
     * 本方姓名(客户名称)
     */
    private String customer_name;

    /**
     * 本方开户人证件号码(客户证件号码)
     */
    private String customer_identity_card;

    /**
     * 查询账号
     */
    private String query_account;

    /**
     * 查询卡号
     */
    private String query_card;

    /**
     * 交易对方姓名(交易对方名称)
     */
    private String transaction_opposite_name;

    /**
     * 交易对方证件号码
     */
    private String transaction_opposite_certificate_number;

    /**
     * 交易对方账号
     */
    private String transaction_opposite_account;

    /**
     * 交易对方卡号
     */
    private String transaction_opposite_card;

    /**
     * 交易类型
     */
    private String transaction_type;

    /**
     * 借贷标志
     */
    private String loan_flag;

    /**
     * 币种
     */
    private String currency;

    /**
     * 交易金额
     */
    private BigDecimal transaction_money;

    /**
     * 交易余额
     */
    private BigDecimal transaction_balance;

    /**
     * 交易时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date trading_time;

    /**
     * 交易流水号
     */
    private String transaction_serial_number;

    /**
     * 交易对方余额
     */
    private BigDecimal transaction_opposite_balance;

    /**
     * 交易对方账号开户行
     */
    private String transaction_opposite_account_open_bank;

    /**
     * 交易摘要
     */
    private String transaction_summary;

    /**
     * 交易网点名称
     */
    private String transaction_network_name;

    /**
     * 交易网点代码
     */
    private String transaction_network_code;

    /**
     * 日志号
     */
    private String log_number;

    /**
     * 传票号
     */
    private String summons_number;

    /**
     * 凭证种类
     */
    private String certificate_type;

    /**
     * 凭证号
     */
    private String certificate_number;

    /**
     * 现金标志
     */
    private String cash_flag;

    /**
     * 终端号
     */
    private String terminal_number;

    /**
     * 交易是否成功
     */
    private String transaction_success_flag;

    /**
     * 交易发生地
     */
    private String transaction_place;

    /**
     * 商户名称
     */
    private String merchant_name;

    /**
     * 商户号
     */
    private String merchant_number;

    /**
     * 本方IP地址
     */
    private String ip_address;

    /**
     * 本方MAC地址
     */
    private String mac_address;

    /**
     * 对方IP地址
     */
    private String opposite_ip_address;

    /**
     * 对方MAC地址
     */
    private String opposite_mac_address;

    /**
     * 交易柜员号
     */
    private String transaction_teller_number;

    /**
     * 交易渠道
     */
    private String transaction_channel;

    /**
     * 备注
     */
    private String note;

    /**
     * 摘要备注
     */
    private String summary_notes;

    /**
     * 交易场所
     */
    private String transaction_places;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 数据类型
     */
    private String data_type;

    /**
     * 查询反馈结果
     */
    private String query_feedback;

    /**
     * 查询反馈结果原因
     */
    private String query_feedback_reasons;

    /**
     * data schema id
     */
    private String data_schema_id;


    /**
     * 数据补全标志位(内容是一个 以为 , 分隔开开的字段, 有哪个字段就是代表 哪个字段被补全过了)
     */
    private String completion_flag;

    /**
     * 组合卡号 (查询卡号-对方卡号)
     */
    private String merge_card;

    /**
     * 组合证件号码 (本方证件号码-对方证件号码)
     */
    private String merge_identity_card;

}
