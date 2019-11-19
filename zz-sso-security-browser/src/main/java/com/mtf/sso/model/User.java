package com.mtf.sso.model;
/** 
* @author Bill
* @date 2019年11月14日
*
*/
import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class User {
	
	private String id;
	
	@NotBlank(message="用户名不能为空")
	private String username;
	
	@NotBlank(message="密码不能为空")
	private String password;
	@Past(message="生日只能是过去的日期")
	private Date birthday;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	//UserSimpleView视图有
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", birthday=" + birthday + "]";
	}
	
}

