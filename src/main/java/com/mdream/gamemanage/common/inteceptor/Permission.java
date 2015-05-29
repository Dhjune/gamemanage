package com.mdream.gamemanage.common.inteceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
	
	boolean permission() default true;
	boolean superToken() default false;	
	boolean secondToken() default false;

}
