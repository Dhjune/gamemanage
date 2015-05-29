package com.mdream.gamemanage.common.cache;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class SimpleCacheAspect implements Ordered{
	
	@Pointcut(value="@annotation(com.mdream.gamesky.common.cache.SimpleCache)")  
    public  void cacheCut() {  
		
    }
	
	@Before(value="cacheCut()")
	public void exectPoint(JoinPoint jp){
		
		//jp.getArgs();
		System.out.println("sssssssssssssssssss");
		
	}
	
	@Around("cacheCut()")  
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{  
        System.out.println("进入环绕通知ss");  
        Signature signature = pjp.getSignature();
         
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod(); 
        
        Object object =null;
//        
//        object =  targetMethod.invoke(pjp.getTarget(), pjp.getArgs());
        System.out.println(pjp.getTarget());
        object =  pjp.proceed();
        System.out.println("退出方法ss");  
        return object;  
    }

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}  

}
