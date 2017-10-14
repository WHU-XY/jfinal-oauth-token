package com.whu.jFinal.response;

public class AuthResponse extends BaseResponse {

	private String token;
	public AuthResponse() {
        super();
    }
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
