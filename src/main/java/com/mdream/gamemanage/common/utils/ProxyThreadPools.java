package com.mdream.gamemanage.common.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Component;

//用于执行非同步任务 
//@Component
public class ProxyThreadPools {
	
	//单例线程池
	private  ThreadPoolExecutor executor =null;
	
	//初始化AtomicBoolean原子操作控制init,貌似和synchronized重叠了
	private static AtomicBoolean inited = new AtomicBoolean(false);
	private  ProxyThreadPools proxyThreadPools = null;
	//private static ProxyThreadPools proxyThreadPools = null;
	
	private  ProxyThreadPools(){
		init();
	}
	
//	public static ProxyThreadPools getInstance() {
//		//此处不加synchronized也可以，因为使用了init使用了AtomicBoolean原子类型操作。
//        if (proxyThreadPools == null) {  
//            synchronized (proxyThreadPools) {  
//                if (proxyThreadPools == null) {  
//                	proxyThreadPools = new ProxyThreadPools();  
//                }  
//            }  
//        }  
//        return proxyThreadPools; 
//        
//    }  
	
	private void init(){
																									
		if(inited.compareAndSet(false , true)){
			System.out.println("***************************************");
			executor = new ThreadPoolExecutor(2, 6, 4, TimeUnit.SECONDS,  
	                new LinkedBlockingQueue());  			
		}
		
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public ProxyThreadPools getProxyThreadPools() {
		return proxyThreadPools;
	}

	public void setProxyThreadPools(ProxyThreadPools proxyThreadPools) {
		this.proxyThreadPools = proxyThreadPools;
	}
	
	
	
	
}
