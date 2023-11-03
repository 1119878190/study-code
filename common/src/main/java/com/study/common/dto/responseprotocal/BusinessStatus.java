package com.study.common.dto.responseprotocal;

/**
 * @author zYh
 */
public enum BusinessStatus {

    /**
     * 服务器成功处理用户请求
     */
    SUCCESS(200, "Success"),

    /**
     * 请求参数不对
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * 用户无资源操作权限
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * 无接口方法访问权限
     */
    UNRESTMETHODAUTHORIZED(402, "Rest Method Unauthorized"),

    /**
     * 拒绝执行
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * 用户请求资源不存在
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * 无法获取样本数据
     */
    SAMPLE_DATA_NOT_FOUND(405, "Sample Data Not Found"),

    /**
     * 请求服务异常
     */
    SERVER_ERROR(500, "Server Error"),

    /**
     * 未设置相关数据
     */
    PRESET_DATA_ERROR(504, "Preset Data Error"),

    /**
     * 请求头未携带token
     */
    NO_TOKEN(600,"header not have token"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),
    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限");


    private final int value;

    private final String reasonPhrase;

    BusinessStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

}
