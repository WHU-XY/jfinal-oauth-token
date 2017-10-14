package com.whu.jFinal.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.Map;

/**
 * @author malongbo
 * @date 2015/2/13
 */
public class User extends Model<User> {
	public static String USER_ID = "id";
	public static String USERNAME = "username";
	public static String PASSWORD = "password";
	private int role_id;

	private static final long serialVersionUID = 1L;
	public static final User user = new User();

    /**
     * 获取用户id*
     * @return 用户id
     */
    public String userId() {
        return getInt(USER_ID).toString();
        
    }
    
	@Override
	public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	
	
}
