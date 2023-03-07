package com.study.mongodb.vo;

public class Response {

    private int code = BusinessStatus.SUCCESS.value();
    private String message;

    public Response() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCode(BusinessStatus businessStatus) {
        setCode(businessStatus.value());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}