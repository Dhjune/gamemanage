package com.mdream.gamemanage.common.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleCache {
		
	String  namespace() default "simple";
	
	String  queue() default "none";
	
	String tag() default "none";

}
