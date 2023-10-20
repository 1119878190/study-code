package com.study.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.study.common.dto.responseprotocal.BusinessStatus;
import com.study.common.dto.responseprotocal.ResultResponse;
import com.study.common.dto.responseprotocal.ResultResponseUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Lx
 * @Date 2023/7/15
 * <p>
 * 用于处理登录失败的逻辑
 */
@Component
public class CustomAuthenticationFailureHandler implements  AuthenticationFailureHandler {



    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResultResponse<Object> result;
        if (e instanceof AccountExpiredException) {
            //账号过期
             result = ResultResponseUtil.fail(BusinessStatus.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result = ResultResponseUtil.fail(BusinessStatus.USER_CREDENTIALS_ERROR);
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result = ResultResponseUtil.fail(BusinessStatus.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof DisabledException) {
            //账号不可用
            result = ResultResponseUtil.fail(BusinessStatus.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException) {
            //账号锁定
            result = ResultResponseUtil.fail(BusinessStatus.USER_ACCOUNT_LOCKED);
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = ResultResponseUtil.fail(BusinessStatus.USER_ACCOUNT_NOT_EXIST);
        }else{
            //其他错误
            result = ResultResponseUtil.fail(BusinessStatus.COMMON_FAIL);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
