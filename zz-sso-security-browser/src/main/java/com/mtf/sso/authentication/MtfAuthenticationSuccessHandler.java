package com.mtf.sso.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtf.sso.properties.LoginType;
import com.mtf.sso.properties.SecurityProperties;

/**
 * @author Bill
 * @date 2019年11月15日
 *
 */
@Component("mtfAuthenticationSuccessHandler")
//public class MtfAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class MtfAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private SecurityProperties securityProperties;

	// 登录成功后会被调用
	/**
	 * Authentication：封装认证信息：包括发起的认证请求的信息，比如IP session和用户信息等
	 */

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("登录成功");

		// response.setContentType("application/json;charset=UTF-8");
		// response.getWriter().write(objectMapper.writeValueAsString(authentication));
		
		if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			// 是json，调用自己的
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		} else {
			// 不是json，则调用父类的，父类为跳转
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

}
