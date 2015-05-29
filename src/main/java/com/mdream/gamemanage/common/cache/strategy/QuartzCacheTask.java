package com.mdream.gamemanage.common.cache.strategy;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// spring task ,quartz

//@Component
public class QuartzCacheTask implements CacheTaskItl{
	
	@Autowired
	private MemcachedClient client;
	
	public void  add(String key,Object value,int exp){
		
		//client.beginWithNamespace();
		
	}
	
	public void clear(){
		
	}

	public void add(String key, String namespace, Object value, int exp) {
		// TODO Auto-generated method stub
		
	}
	
	

}
