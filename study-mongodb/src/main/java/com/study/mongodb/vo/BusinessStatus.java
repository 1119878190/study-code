package com.study.mongodb.vo;

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
    UNRESTMETHODAUTHORIZED(402, "Rest method Unauthorized"),

    /**
     * 拒绝执行
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * 用户请求资源不存在
     */
    NOT_FOUND(404, "not found"),

    /**
     * 无法获取样本数据
     */
    SAMPLE_DATA_NOT_FOUND(405, "sample data not found"),

    /**
     * 请求服务异常
     */
    SERVER_ERROR(500, "Server Error"),

    /**
     * 未设置相关数据
     */
    PRESET_DATA_ERROR(504, "Preset data error"),

    /**
     * 上传文件失败
     */
    UPLOAD_FILE_ERROR(505, "upload file error"),

    /**
     * 上传非案件文件重复
     */
    DUPLICATE_FILE(506, "duplicate file"),

    /**
     * 上传案件文件重复未关联
     */
    DUPLICATE_CASE_FILE(507, "duplicate case file"),

    /**
     * 上传案件文件重复已关联
     */
    DUPLICATE_CASE_FILE_RELATED(508, "duplicate case file");

    private final int value;

    private final String reasonPhrase;

    private BusinessStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public static BusinessStatus valueOf(int statusCode) {
        for (BusinessStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
