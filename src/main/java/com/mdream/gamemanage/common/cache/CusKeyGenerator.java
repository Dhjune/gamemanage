package com.mdream.gamemanage.common.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.interceptor.SimpleKeyGenerator;


public class CusKeyGenerator extends SimpleKeyGenerator {  
	  
	@Override
	public Object generate(Object target, Method method, Object... params) {
		
		return generateKey(params);
	}

	/**
	 * Generate a key based on the specified parameters.
	 */
	public static Object generateKey(Object... params) {
		if (params.length == 0) {
			return CusSimpleKey.EMPTY;
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param != null && !param.getClass().isArray()) {
				return param;
			}
		}
		//Object  o = new CusSimpleKey(params);
	//	System.out.println(o);
		return new CusSimpleKey(params);
	}
	
	public Map  assemble(Object target, Method method, Object... params){
		Map result = null;
		if(method!=null){
			
			DynamicCache cache =  method.getAnnotation(DynamicCache.class);
			CusSimpleKey key = (CusSimpleKey) generateKey(params);
			StringBuilder builder = new StringBuilder("");
			builder.append(cache.namespace()+":");
			builder.append(cache.queue()+":");
			builder.append(cache.tag()+":");		
			builder.append(key.getString());
			result =  new HashMap();
			result.put("namespace", cache.namespace());
			result.put("key", builder.toString().replaceAll(" ", ""));
			return result;
		}else{
			return null;
		}
			
	}
  
}  
