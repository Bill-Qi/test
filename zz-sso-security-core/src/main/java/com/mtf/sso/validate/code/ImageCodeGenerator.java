package com.mtf.sso.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.mtf.sso.properties.SecurityProperties;

/**
 * @author Bill
 * @date 2019年11月21日
 *
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public ImageCode generator(ServletWebRequest request) {
		// 先从request里读取有没有长、宽、字符个数参数，有的话就用，没有用默认的
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
				securityProperties.getCode().getImage().getWidth());

		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
				securityProperties.getCode().getImage().getHeight());

		int charLength = this.securityProperties.getCode().getImage().getLength();
		VerifyCode verifyCode = new VerifyCode(width, height, charLength);
		return new ImageCode(verifyCode.getImage(), verifyCode.getText(),
				this.securityProperties.getCode().getImage().getExpireIn());
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

}
