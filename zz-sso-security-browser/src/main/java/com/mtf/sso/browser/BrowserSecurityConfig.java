package com.mtf.sso.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.mtf.sso.properties.SecurityProperties;
import com.mtf.sso.validate.code.ValidateCodeFilter;

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
	
	 /**
     * 记住我TokenRepository配置，在登录成功后执行
     * 登录成功后往数据库存token的
     * @Description: 记住我TokenRepository配置
     * @param @return   JdbcTokenRepositoryImpl
     * @return PersistentTokenRepository  
     * @throws
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //启动时自动生成相应表，可以在JdbcTokenRepositoryImpl里自己执行CREATE_TABLE_SQL脚本生成表
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

	// 注入自己写的登录成功处理器
	@Autowired
	private AuthenticationSuccessHandler mtfAuthenticationSuccessHandler;

	// 注入自己写的登录失败处理器
	@Autowired
	private AuthenticationFailureHandler mtfAuthencationFailureHandler;

	// 数据源
	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 实现需要认证的接口跳转表单登录,安全=认证+授权
		// http.httpBasic() //这个就是默认的弹框认证
		// 验证码过滤器
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		// 验证码过滤器中使用自己的错误处理
		validateCodeFilter.setAuthenticationFailureHandler(mtfAuthencationFailureHandler);
		// 配置验证码过滤器
		validateCodeFilter.setSecurityProperties(securityProperties);
		validateCodeFilter.afterPropertiesSet();

		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)// 把验证码过滤器加载登录过滤器前边
				.formLogin() // 表单认证
				.loginPage("/authentication/require") // 登录页面
				// .loginProcessingUrl("/browser/authentication/form")
				// 配置让Spring知道让UsernamePasswordAuthenticationFilter过滤器去处理/authentication/form路径
				.loginProcessingUrl("/authentication/form").successHandler(mtfAuthenticationSuccessHandler)
				.failureHandler(mtfAuthencationFailureHandler)
				
				.and()
	            //记住我相关配置    
	            .rememberMe()
	                .tokenRepository(persistentTokenRepository())//TokenRepository，登录成功后往数据库存token的
	                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//记住我秒数
	                .userDetailsService(userDetailsService) //记住我成功后，调用userDetailsService查询用户信息
				
				.and().authorizeRequests() // 下边的都是授权的配置
				.antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(),
						"/verifycode/image", "/verifycode/sms")
				.permitAll() // 验证码// 放过登录页不过滤，否则报错
				.anyRequest() // 任何请求
				.authenticated() // 都需要身份认证

				.and().csrf().disable();
	}
}
