package com.mtf.sso.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mtf.sso.properties.SecurityProperties;

/**
 * @author Bill
 * @date 2019年11月14日
 *
 */
@Configuration // 这是一个配置
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	// 注意是org.springframework.security.crypto.password.PasswordEncoder
	@Bean
	public PasswordEncoder passwordencoder() {
		// BCryptPasswordEncoder implements PasswordEncoder
		return new BCryptPasswordEncoder();
	}

	// 注入自己写的登录成功处理器
	@Autowired
	private AuthenticationSuccessHandler mtfAuthenticationSuccessHandler;
	
	 //注入自己写的登录失败处理器
    @Autowired
    private AuthenticationFailureHandler mtfAuthencationFailHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 实现需要认证的接口跳转表单登录,安全=认证+授权
		// http.httpBasic() //这个就是默认的弹框认证
		http.formLogin() // 表单认证
				.loginPage("/authentication/require") // 登录页面
				// .loginProcessingUrl("/browser/authentication/form")
				// 配置让Spring知道让UsernamePasswordAuthenticationFilter过滤器去处理/authentication/form路径
				.loginProcessingUrl("/authentication/form")
				.successHandler(mtfAuthenticationSuccessHandler)
				.failureHandler(mtfAuthencationFailHandler)
				.and().authorizeRequests() // 下边的都是授权的配置
				.antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage()).permitAll() // 放过登录页不过滤，否则报错
				.anyRequest() // 任何请求
				.authenticated() // 都需要身份认证

				.and().csrf().disable();
	}
}