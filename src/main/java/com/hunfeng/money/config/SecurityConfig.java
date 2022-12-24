package com.hunfeng.money.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用 csrf 防御
                .csrf().disable()
                //开启跨域支持
                .cors().and()
                //基于Token，不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //任何 /user 开头的路径下的请求都需要经过JWT验证
//                .antMatchers("/user/**").hasRole("admin")
                //其它路径全部放行
                .anyRequest().permitAll();
//                .and()
//                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
//                //未登录时，返回json，在前端执行重定向
//                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);
    }
}
