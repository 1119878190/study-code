package com.study.security.config;


import com.study.security.filter.CustomerUsernamePasswordAuthenticationFilter;
import com.study.security.handler.CustomAuthenticationFailureHandler;
import com.study.security.handler.CustomerAuthenticationSuccessHandler;
import com.study.security.handler.MyAuthenticationEntryPoint;
import com.study.security.service.UserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


/**
 *
 * springsecurity 配置类
 * @Author lx
 * @Date 2023/5/4
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    UserServices userServices;

    @Resource
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Resource
    private CustomerAuthenticationSuccessHandler customerAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    /**
     * SpringSecurity5.X要求必须指定密码加密方式，否则会在请求认证的时候报错
     * 同样的，如果指定了加密方式，就必须您的密码在数据库中存储的是加密后的，才能比对成功
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //开启加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServices);
    }

    /**
     * 静态资源放行
     */
    //@Override
    //public void configure(WebSecurity web) {
    //    web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    //}


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // /test/everyOne接口允许匿名访问
                .antMatchers("/test/everyOne").anonymous()
                //除了上面的接口，所有接口都需要登录才能访问
                .anyRequest().authenticated()
                .and()
                //开启表单登录
                .formLogin()
                // 处理登录请求的接口地址
                .loginProcessingUrl("/user/login")
                //登录成功的处理方法
                .successHandler(customerAuthenticationSuccessHandler)
                //登录失败的处理方法
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                // 自定义登录验证逻辑处理
                .addFilter(new CustomerUsernamePasswordAuthenticationFilter(authenticationManagerBean()))
                //匿名用户访问无权限资源时的异常处理
                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);
        //关闭csrf
        http.csrf().disable();
        //开启过滤器，并将其置于UsernamePasswordAuthenticationFilter过滤器之前
        //http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
