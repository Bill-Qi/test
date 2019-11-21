package com.mtf.sso.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mtf.sso.properties.SecurityProperties;

/**
 * @author Bill
 * @date 2019年11月19日
 *
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	// 认证失败处理器
	private AuthenticationFailureHandler authenticationFailureHandler;

	// 获取session工具类
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	//需要拦截的url集合
    private Set<String> urls = new HashSet<>();
    //读取配置
    @Autowired
    private SecurityProperties securityProperties;
    //spring工具类
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //读取配置的拦截的urls
        String a = securityProperties.getCode().getImage().getUrl();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
        //登录的请求一定拦截
        urls.add("/authentication/form");
    }

    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 /**
         * 可配置的验证码校验
         * 判断请求的url和配置的是否有匹配的，匹配上了就过滤
         */
        boolean action = false;
        for(String url:urls){
            if(antPathMatcher.match(url, request.getRequestURI())){
                action = true;
            }
        }
        
        if(action){
        	// 如果是 登录请求 则执行
//        	if (StringUtils.equals("/authentication/form", request.getRequestURI())//TODO
//        			&& StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
        		try {
        			validate(new ServletWebRequest(request));
        		} catch (ValidateCodeException e) {
        			// 调用错误处理器，最终调用自己的
        			authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        			return;// 结束方法，不再调用过滤器链
//        		}
        	}
        }
		// 不是登录请求，调用其它过滤器链
		filterChain.doFilter(request, response);
	}

	/**
	 * 校验验证码
	 * 
	 * @Description: 校验验证码
	 * @param @param request
	 * @param @throws ServletRequestBindingException
	 * @return void
	 * @throws ValidateCodeException
	 */
	private void validate(ServletWebRequest request) throws ServletRequestBindingException {
		// 拿出session中的ImageCode对象
		ImageCode imageCodeInSession = (ImageCode) sessionStrategy.getAttribute(request,
				ValidateCodeController.SESSION_KEY);
		// 拿出请求中的验证码
		String imageCodeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		// 校验
		if (StringUtils.isBlank(imageCodeInRequest)) {
			throw new ValidateCodeException("验证码不能为空");
		}
		if (imageCodeInSession == null) {
			throw new ValidateCodeException("验证码不存在，请刷新验证码");
		}
		if (imageCodeInSession.isExpired()) {
			// 从session移除过期的验证码
			sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException("验证码已过期，请刷新验证码");
		}
		if (!StringUtils.equalsIgnoreCase(imageCodeInSession.getCode(), imageCodeInRequest)) {
			throw new ValidateCodeException("验证码错误");
		}
		// 验证通过，移除session中验证码
		sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public AntPathMatcher getAntPathMatcher() {
		return antPathMatcher;
	}

	public void setAntPathMatcher(AntPathMatcher antPathMatcher) {
		this.antPathMatcher = antPathMatcher;
	}


	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}


	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

}
