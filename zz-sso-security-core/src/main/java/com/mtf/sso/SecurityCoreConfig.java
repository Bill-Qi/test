/**
 * 
 */
package com.mtf.sso;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.mtf.sso.properties.SecurityProperties;


/**
 * @author Bill
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
