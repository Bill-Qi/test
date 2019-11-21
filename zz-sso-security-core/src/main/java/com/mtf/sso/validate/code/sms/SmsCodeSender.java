package com.mtf.sso.validate.code.sms;
/** 
* @author Bill
* @date 2019年11月21日
*
*/
public interface SmsCodeSender {

    void send(String mobile,String code);
	
}
