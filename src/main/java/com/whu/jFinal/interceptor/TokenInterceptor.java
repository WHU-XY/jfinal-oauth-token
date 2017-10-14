package com.whu.jFinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.whu.jFinal.bean.OAuthTokenCode;
import com.whu.jFinal.bean.Code;
import com.whu.jFinal.common.token.TokenManager;
import com.whu.jFinal.model.User;
import com.whu.jFinal.response.BaseResponse;

/**
 * Token拦截器
 * @author malongbo
 * @date 15-1-18
 * @package com.pet.project.interceptor
 */
public class TokenInterceptor implements Interceptor {
	private String header=null;
    public void intercept(Invocation ai) {
        Controller controller = ai.getController();
        header = controller.getHeader(OAuthTokenCode.TOKEN_HEADER);
        if(header==null) {
        	controller.renderJson(new BaseResponse(Code.ARGUMENT_ERROR, "令牌为空"));
        	return;
        }
        header = header.replace(" ", "");
        String TokenFormat = header.substring(0, 6);
        if(!TokenFormat.equals(OAuthTokenCode.TOKEN_FORMAT)) {
        	controller.renderJson(new BaseResponse(Code.ARGUMENT_ERROR, "token格式出错"));
        	return;
        }
        String token = header.substring(6);
        
        if(Db.findFirst("SELECT *FROM stp_api_user_token WHERE token=? ", token)==null) {
        	 controller.renderJson(new BaseResponse(Code.TOKEN_INVALID, "账号已登录"));
             return;
        }        
        User user = TokenManager.getMe().validate(token);
        if(user!=null) {
        	 controller.setAttr("user", user);
        }
       
        ai.invoke();
    }
}
