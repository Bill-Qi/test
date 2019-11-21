package com.mtf.sso.code;

import org.springframework.web.context.request.ServletWebRequest;
import com.mtf.sso.validate.code.ImageCode;
import com.mtf.sso.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;

/** 
* @author Bill
* @date 2019年11月21日
*
*/
@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode generator(ServletWebRequest request) {
        System.err.println("demo项目实现的生成验证码，，，");
        
        return null;
    }

}
