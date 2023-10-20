package com.study.security.exception;

import com.study.common.dto.responseprotocal.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lx
 * @date 2023/10/11
 */
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse serviceExceptionHandler(HttpServletRequest req, Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setPath(req.getRequestURI());
        return response;
    }



}
