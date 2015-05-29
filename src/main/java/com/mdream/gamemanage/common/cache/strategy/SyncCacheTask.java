package com.mdream.gamemanage.common.cache.strategy;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientCallable;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;


public class SyncCacheTask implements Runnable{
			
	private MemcachedClient client;
	
	private Map result ;
	
	private Object value ;
	
	private int  exp = 3600;
	
	public SyncCacheTask(MemcachedClient client,Map result,Object value ){
		
		this.client =  client ;
		this.result = result;		
		this .value = value ;					
	}
	
	public void run() {		
		//client.beginWithNamespace((String) result.get("namespace"));	
		try {
			System.out.println(result.get("key"));
			client.add((String) result.get("key"), exp, value);
			//client.invalidateNamespace((String) result.get("namespace"));
			//client.invalidateNamespace((String) result.get("namespace"));
			//全部清除也是不行呐~~
			client.flushAll();
//			
//			Map map= client.getStats();
//			Set set = map.keySet();
//			Iterator it = set.iterator();
//			while(it.hasNext()){
//				Object key =  it.next();
//				
//				System.out.println(key);
//				System.out.println(map.get(key));
//			}
			
		  
			//System.out.println ("异步缓存更新 ");
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//client.endWithNamespace();
		}
		
	}


	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = value;
	}

	public MemcachedClient getClient() {
		return client;
	}


	public void setClient(MemcachedClient client) {
		this.client = client;
	}

	public Map getResult() {
		return result;
	}

	public void setResult(Map result) {
		this.result = result;
	}

}
