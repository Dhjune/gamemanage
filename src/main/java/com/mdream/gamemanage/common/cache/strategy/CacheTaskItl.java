package com.mdream.gamemanage.common.cache.strategy;

public interface CacheTaskItl {
	
	public void add(String key,String namespace,Object value,int exp);
	
	public void clear();
	
}
