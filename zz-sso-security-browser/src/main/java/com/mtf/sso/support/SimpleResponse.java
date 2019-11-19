package com.mtf.sso.support;
/** 
* @author Bill
* @date 2019年11月15日
*
*/
public class SimpleResponse {
	
	public SimpleResponse(Object content){
		this.content = content;
	}
	
	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
}
