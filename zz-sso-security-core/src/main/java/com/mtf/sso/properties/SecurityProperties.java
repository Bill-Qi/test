package com.mtf.sso.properties;
/** 
* @author Bill
* @date 2019年11月15日
*
*/
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mtf.security")//该类会读取配置文件中所有以hcx.security开头的配置项
public class SecurityProperties {
    
    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}