package com.whu.jFinal.router;

import com.jfinal.config.Routes;
import com.whu.jFinal.action.IndexActionController;


public class ActionRouter extends Routes {

	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/", IndexActionController.class, "/");
	}

}
