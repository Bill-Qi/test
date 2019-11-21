package com.mtf.sso.properties;

/** 
* @author Bill
* @date 2019年11月20日
*
*/
/**
 * 验证码配置
 * ClassName: ValidateCodeProperties 
 * @Description: 验证码配置,验证码有图片验证码、短信验证码等，所以再包一层
 */
public class ValidateCodeProperties {
    
    //默认配置
    private ImageCodeProperties image = new ImageCodeProperties();
    
    private SmsCodeProperties sms = new SmsCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
    
    public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}

    
    
}
