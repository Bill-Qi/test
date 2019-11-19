package com.mtf.sso.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtf.sso.model.User;

/** 
* @author Bill
* @date 2019年11月14日
*
*/
@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping
	public List<User> query(Pageable pageable){
		
		List<User> users = new ArrayList<User>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}
	
//	@GetMapping("/me")
//	public Object getCurrentUser() {
//	    return SecurityContextHolder.getContext().getAuthentication();
//	}
	
	@GetMapping("/me")
	public Object getCurrentUser(Authentication authentication) {
	    return authentication;
	}
}
