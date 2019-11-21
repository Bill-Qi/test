package com.mtf.sso.validate.code;
/** 
* @author Bill
* @date 2019年11月19日
*
*/
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.mtf.sso.properties.SecurityProperties;
import com.mtf.sso.validate.code.sms.SmsCodeSender;

@RestController
public class ValidateCodeController {
    
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";    
    
    //获取session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    
    @Autowired
    private SecurityProperties securityProperties;
    
    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;
    
    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;
    
    @Autowired
    private SmsCodeSender smsCodeSender;
    
    @GetMapping("/verifycode/image")
    public void createCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
        
    	ImageCode imageCode = (ImageCode) imageCodeGenerator.generator(new ServletWebRequest(request));
        		//createImageCode(request, response);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
    
    @GetMapping("/verifycode/sms")
    public void createSmsCode(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //调验证码生成接口方式
        ValidateCode smsCode = smsCodeGenerator.generator(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
        //获取手机号
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        //发送短信验证码
        smsCodeSender.send(mobile, smsCode.getCode());
    }

    
//    private ImageCode createImageCode(HttpServletRequest request, HttpServletResponse response) {
//        //VerifyCode verifyCode = new VerifyCode();
//    	//先从request里读取有没有长、宽、字符个数参数，有的话就用，没有用默认的
//        int width  = ServletRequestUtils.getIntParameter(request, "width",securityProperties.getCode().getImage().getWidth());
//        
//        int height = ServletRequestUtils.getIntParameter(request, "height",securityProperties.getCode().getImage().getHeight());
//        
//        int charLength = this.securityProperties.getCode().getImage().getLength();
//        VerifyCode verifyCode = new VerifyCode(width,height,charLength);
//    	
//        return new ImageCode(verifyCode.getImage(),verifyCode.getText(),this.securityProperties.getCode().getImage().getExpireIn());
//    }

}
