package com.mtf.sso.validate.code;

/** 
* @author Bill
* @date 2019年11月19日
*
*/
import org.springframework.security.core.AuthenticationException;

/**
 * ClassName: ValidateCodeException 
 * @Description: 验证码错误异常，继承spring security的认证异常
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
