package com.mdream.gamemanage.common.cache;

import java.lang.reflect.Method;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.mdream.gamemanage.common.cache.strategy.SyncCacheTask;
import com.mdream.gamemanage.common.utils.ProxyThreadPools;



@Aspect
//@Component
public class DynamicCacheAspect implements Ordered{
	
	@Autowired
	private  CusKeyGenerator cusKeyGenerator;
	
	@Autowired
	private MemcachedClient memcachedClient;
	
	@Autowired
	private ProxyThreadPools proxyPools;
	
	@Pointcut(value="@annotation(com.mdream.gamemanage.common.cache.DynamicCache)")  
    public  void cacheCut() {  
		
    }
	
	@Before(value="cacheCut()")
	public void exectPoint(JoinPoint jp){
		
		//jp.getArgs();
		System.out.println("sssssssssssssssssss");
		
	}
	
	@Around("cacheCut()")  
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{  
        System.out.println("进入环绕通知"); 
        
      
        Map result =  cusKeyGenerator.assemble(pjp.getTarget(), ((MethodSignature) pjp.getSignature()).getMethod(), pjp.getArgs());
        Object object =null;
       
        if(result!=null){
        	memcachedClient.beginWithNamespace((String) result.get("namespace"));
        	
        	object = memcachedClient.get((String) result.get("key"));
        	
        	memcachedClient.endWithNamespace();
        	
        	if(object!=null){
        		return object;
        	}else{
            	object = pjp.proceed();//执行该方法  
            	//多线程插入缓存,sync
            	System.out.println(proxyPools);
            	System.out.println(proxyPools.getExecutor());
            	proxyPools.getExecutor().execute(new SyncCacheTask(memcachedClient, result, object));
            //同步插入缓存制度,舍弃 
            //	memcachedClient.add(cacheKey, 3600, object);
            }
        	
        }else{
        	object = pjp.proceed();//执行该方法  
        	       
        }
//        
//        object =  targetMethod.invoke(pjp.getTarget(), pjp.getArgs());
        System.out.println(pjp.getTarget());
        
      
       // Object object =null;
        System.out.println("退出方法");  
        return object;  
    }
	
	//@AfterReturning("cacheCut()")
	public void storageCache(){
		
	}
	

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public CusKeyGenerator getCusKeyGenerator() {
		return cusKeyGenerator;
	}

	public void setCusKeyGenerator(CusKeyGenerator cusKeyGenerator) {
		this.cusKeyGenerator = cusKeyGenerator;
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public ProxyThreadPools getProxyPools() {
		return proxyPools;
	}

	public void setProxyPools(ProxyThreadPools proxyPools) {
		this.proxyPools = proxyPools;
	}

	
}
