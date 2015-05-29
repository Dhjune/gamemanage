package com.mdream.gamemanage.common.tools;

import org.springframework.context.ApplicationContext;

import com.mdream.gamemanage.common.utils.ProxyThreadPools;


public  class ToolsFactory {
	
	public static BeanTools beanTools = null;
	
	public static UrlTools urlTools  = null;
	
	public static ApplicationContext applicationContext = null;
	
	public static  HtmlTools htmlTools = null;
	
	public static ProxyThreadPools  pools = null;
	
	public static GsonTools gsonTools=null;
	
}
