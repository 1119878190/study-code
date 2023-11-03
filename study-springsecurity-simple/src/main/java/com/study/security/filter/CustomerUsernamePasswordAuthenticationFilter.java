package com.study.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lx
 * @date 2023/10/10
 * <p>
 * 这个类主要用于 登录时用户名密码校验或一些其它信息的校验，
 * 如果只是用户名密码校验，可以不用写这个类
 */
public class CustomerUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomerUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        // 从请求中获取用户名、密码和自定义属性
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String customAttribute = request.getParameter("customAttribute");


        // 可以在校验用户名密码之前 校验其它的信息 如请求头中是否包含xxx

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

        // 返回认证对象
        return getAuthenticationManager().authenticate(auth);


    }

    // 自定义属性校验逻辑，根据你的需求实现
    private boolean customAttributeIsValid(String customAttribute) {
        // 在这里执行你的自定义属性校验逻辑，返回true表示校验成功，false表示校验失败
        // 你可以根据需要编写逻辑来判断customAttribute是否有效
        return customAttribute != null && customAttribute.equals("validValue");
    }


}
