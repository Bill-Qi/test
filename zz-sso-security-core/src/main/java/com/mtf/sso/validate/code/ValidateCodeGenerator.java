package com.mtf.sso.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成接口
 * ClassName: ValidateCodeGenerator 
 * @Description: TODO
 * @author Bill
 * @date 2019年11月21日
 */
public interface ValidateCodeGenerator {

    /**
     * 图片验证码生成接口
     * @Description: TODO
     * @param @param request
     * @param @return   
     * @return ImageCode  
     * @throws
     */
    ImageCode generator(ServletWebRequest request);
}