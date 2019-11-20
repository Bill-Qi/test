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
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ValidateCodeController {
    
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";    
    
    //获取session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    
    @GetMapping("/verifycode/image")
    public void createCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
        
        ImageCode imageCode = createImageCode(request, response);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    
    private ImageCode createImageCode(HttpServletRequest request, HttpServletResponse response) {
        VerifyCode verifyCode = new VerifyCode();
        return new ImageCode(verifyCode.getImage(),verifyCode.getText(),60);
    }

}
