package com.mtf.sso.validate.code.sms;
/** 
* @author Bill
* @date 2019年11月21日
*
*/
public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		  System.err.println("向手机 :"+mobile+" 发送短信验证码 :"+code);
	}

}
