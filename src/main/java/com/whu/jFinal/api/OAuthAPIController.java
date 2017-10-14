package com.whu.jFinal.api;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.whu.jFinal.bean.Code;
import com.whu.jFinal.bean.OAuthTokenCode;
import com.whu.jFinal.common.Require;
import com.whu.jFinal.common.token.TokenManager;
import com.whu.jFinal.model.User;
import com.whu.jFinal.response.AuthResponse;
import com.whu.jFinal.response.BaseResponse;

public class OAuthAPIController extends BaseAPIController {

	public void token() {
		if (!"post".equalsIgnoreCase(getRequest().getMethod())) {
			renderJson(new BaseResponse(Code.NOT_FOUND,"请求方式出错"));
			return;
		}
		String grant_type = getPara("grant_type");
		String client_id = getPara("client_id");
		String client_secret = getPara("client_secret");
		String username = getPara("username");
		String password = getPara("password");
		// 校验参数, 确保不能为空
		if (!notNull(Require.me()
				.put(grant_type, "授权类型为空")
				.put(client_id, "appid为空")
				.put(client_secret, "appkey为空")
				.put(username, "用户名为空")
				.put(password, "密码为空")
				)) {
			return;
		}
		if(!grant_type.equals(OAuthTokenCode.GRANT_TYPE)) {
			renderJson(new BaseResponse(Code.ERROR,"授权类型出错"));
			return;
		}
		if(!client_id.equals(OAuthTokenCode.CLIENT_ID)) {
			renderJson(new BaseResponse(Code.ERROR,"appid出错"));
			return;
		}
		if(!client_secret.equals(OAuthTokenCode.CLIENT_SECRET)) {
			renderJson(new BaseResponse(Code.ERROR,"appkey出错"));
			return;
		}
		String sql = "SELECT * FROM stp_app_user WHERE username=?";
		User nowUser = User.user.findFirst(sql, username);
		AuthResponse response = new AuthResponse();
		if (nowUser == null) {
			renderJson(new BaseResponse(Code.ERROR,"用户名出错"));
			return;
		} else if (!password.equals(nowUser.getStr("password"))) {
			renderJson(new BaseResponse(Code.ERROR,"密码出错"));
			return;
		}

		String token = TokenManager.getMe().generateToken(nowUser);

		if (Db.find("SELECT *FROM stp_api_user_token WHERE username=?", username).isEmpty()) {
			Record usertoken = new Record().set("username", username).set("token", token);
			Db.save("stp_api_user_token", usertoken);
		} else {
			Db.update("UPDATE stp_api_user_token set token=? where username=?", token, username);
		}

		/*
		 * Map<String, Object> userInfo = new HashMap<String,
		 * Object>(nowUser.getAttrs());
		 * System.out.println(nowUser.get("role_id").toString());
		 * if(nowUser.get("role_id").toString().equals("4")) {
		 * userInfo.replace("role_id", 4); }else { userInfo.replace("role_id", 1); }
		 * userInfo.remove(PASSWORD); userInfo.remove("salt");
		 */
		response.setCode(1);
		response.setMessage("认证成功");
		response.setToken(token);
		renderJson(response);
	}
}
