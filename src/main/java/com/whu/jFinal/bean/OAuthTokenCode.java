package com.whu.jFinal.bean;

public class OAuthTokenCode {
	
	/*获取token 传入授权类型
     * */
	 public static final String GRANT_TYPE = "password";
	 
	 /*获取token 传入appid
	     * */
	 public static final String CLIENT_ID = "suntrans";
	 
	 /*获取token 传入appkey
	     * */
	 public static final String CLIENT_SECRET = "suntrans";
	
	 /*Authorization token 头
     * */
	 public static final String TOKEN_HEADER = "Authorization";
     /*Authorization token 格式
      * */
	 public static final String TOKEN_FORMAT = "Bearer";
	 
}
