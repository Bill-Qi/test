package com.mtf.sso.properties;
/** 
* @author Bill
* @date 2019年11月15日
*
*/
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mtf.security")//该类会读取配置文件中所有以mtf.security开头的配置项
public class SecurityProperties {
    
    private BrowserProperties browser = new BrowserProperties();
    
    private ValidateCodeProperties code = new ValidateCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}
}