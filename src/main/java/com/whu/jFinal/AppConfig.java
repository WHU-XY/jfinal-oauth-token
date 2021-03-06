package com.whu.jFinal;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.whu.jFinal.config.Context;
import com.whu.jFinal.handler.APINotFoundHandler;
import com.whu.jFinal.handler.ContextHandler;
import com.whu.jFinal.interceptor.ErrorInterceptor;

import com.whu.jFinal.model.FeedBack;
import com.whu.jFinal.model.RegisterCode;
import com.whu.jFinal.model.User;
import com.whu.jFinal.plugin.HikariCPPlugin;
import com.whu.jFinal.router.APIRouter;
import com.whu.jFinal.router.ActionRouter;

public class AppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants arg0) {
		// TODO Auto-generated method stub
		arg0.setDevMode(true);//开启开发模式
		arg0.setEncoding("UTF-8");
		arg0.setViewType(ViewType.JSP);
	}

	@Override
	public void configHandler(Handlers arg0) {
		// TODO Auto-generated method stub
		arg0.add(new ContextHandler());
		arg0.add(new APINotFoundHandler());
	}

	@Override
	public void configInterceptor(Interceptors arg0) {
		// TODO Auto-generated method stub
		arg0.add(new ErrorInterceptor());
	}

	@Override
	public void configPlugin(Plugins arg0) {
		// TODO Auto-generated method stub
		loadPropertyFile("jdbc.properties");
        HikariCPPlugin hcp = new HikariCPPlugin(getProperty("jdbcUrl"), 
                getProperty("user"), 
                getProperty("password"), 
                getProperty("driverClass"), 
                getPropertyToInt("maxPoolSize"));
        arg0.add(hcp);
        
        ActiveRecordPlugin arp = new ActiveRecordPlugin(hcp);
        arg0.add(arp);
		
        
        
		arp.addMapping("stp_app_user", User.USER_ID, User.class);//用户表
        arp.addMapping("stp_api_register_code", RegisterCode.MOBILE, RegisterCode.class); //注册验证码对象
        arp.addMapping("stp_api_feedback", FeedBack.class); //意见反馈表
        
        
        Cron4jPlugin cp = new Cron4jPlugin(PropKit.use("task.properties"));
        arg0.add(cp);
	}

	@Override
	public void configRoute(Routes arg0) {
		// TODO Auto-generated method stub
		arg0.add(new APIRouter());//接口路由
		arg0.add(new ActionRouter()); //页面路由
	}
	@Override
    public void afterJFinalStart() {
        Context.me().init();
        System.out.println("#########################################");
		System.out.println("############和平校区系统启动完成##########");
		System.out.println("#########################################");
    }

    @Override
    public void beforeJFinalStop() {
        Context.me().destroy();
        System.out.println("#########################################");
		System.out.println("############和平校区系统停止完成##########");
		System.out.println("#########################################");
    }

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}

}
