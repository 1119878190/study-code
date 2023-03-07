package com.study.mongodb.vo;

public class ResultResponse<T> extends Response {

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
