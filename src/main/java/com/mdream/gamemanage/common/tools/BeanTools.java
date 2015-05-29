package com.mdream.gamemanage.common.tools;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class BeanTools implements ApplicationContextAware{
	
	
	private  ApplicationContext applicationContext;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.applicationContext =  applicationContext;
		UrlTools urlTools = (UrlTools) applicationContext.getBean("urlTools");
		GsonTools gsonTools = (GsonTools) applicationContext.getBean("gsonTools");
//		System.out.println("$%^$&^#$^&^%&^#$%^&*(^%^&*");
//		MemcachedClient  client=  (MemcachedClient) applicationContext.getBean("memcachedClient");
//		System.out.println(client);
//		
//		client =  (MemcachedClient) applicationContext.getBean(MemcachedClient.class);
//		System.out.println(client);
		
		//初始化非同步任务的线程池 
		//ToolsFactory.pools = ProxyThreadPools.getInstance();
		
		ToolsFactory.beanTools = this;
		ToolsFactory.urlTools = urlTools;
		ToolsFactory.applicationContext = applicationContext;
		ToolsFactory.gsonTools =  gsonTools;
	}

}
