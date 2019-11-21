package com.mtf.sso.properties;

/**
 * @author Bill
 * @date 2019年11月15日
 *
 */
public class BrowserProperties {

	/**
	 * 如果用户配置了就使用用户配置的； 如果没有配，则使用/login.html
	 */
	private String loginPage = "/login.html";// 指定默认跳转

	// 配置默认返回json
	private LoginType loginType = LoginType.JSON;
	
	private Integer rememberMeSeconds= 60;

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public Integer getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(Integer rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

}
