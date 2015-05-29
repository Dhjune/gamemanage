package com.mdream.gamemanage.common.proxy.hibernate.mapper;

import java.io.IOException;

public class SysException extends IOException{

	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = 7035928380821723229L;
	
	public SysException(String msg){
		super(msg);
	}
	
	public SysException(Exception e){
		super(e);
	}
	

}
