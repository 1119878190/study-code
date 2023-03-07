package com.study.mongodb.vo;


/**
 * @author zYh
 */
public final class ResultResponseUtil {

    private ResultResponseUtil() {
    }

    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setResult(data);
        return response;
    }

    public static <T> ResultResponse<T> fail(int code, String message) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static <T> ResultResponse<T> success() {
        return new ResultResponse<>();
    }


    public static <T> ResultResponse<T> fail(BusinessStatus businessStatus) {
        return fail(businessStatus.value(), businessStatus.getReasonPhrase());
    }

}
