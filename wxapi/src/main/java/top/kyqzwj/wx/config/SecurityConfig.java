package top.kyqzwj.wx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.kyqzwj.wx.modules.security.handler.AjaxAuthenticationEntryPoint;
import top.kyqzwj.wx.modules.security.handler.AjaxLogoutSuccessHandler;
import top.kyqzwj.wx.modules.security.filter.JwtAuthenticationTokenFilter;
import top.kyqzwj.wx.modules.security.handler.AjaxAccessDeniedHandler;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/24 17:27
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 未登陆时返回 JSON 格式的数据给前端（否则为 html）
     * */
    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
     * */
    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;

    /**
     * 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）
     * */
    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;

    /**JWT 拦截器*/
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("*.css","*.js","*.html","*.png");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.放开wxLogin接口的访问权限，任何用户都可以访问
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and().authorizeRequests().antMatchers("/**/user/wxLogin").permitAll()
                .and().authorizeRequests().antMatchers("/v1/**").authenticated()
        ;

        // 无权访问 JSON 格式的数据
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        // JWT Filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
