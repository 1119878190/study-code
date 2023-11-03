package com.study.common.dto.responseprotocal;


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
        return fail(businessStatus.getValue(), businessStatus.getReasonPhrase());
    }

    public static <T> ResultResponse<T> successWithMsg(BusinessStatus businessStatus){
        ResultResponse<T> response = new ResultResponse<>();
        response.setMessage(businessStatus.getReasonPhrase());
        return response;
    }

}
