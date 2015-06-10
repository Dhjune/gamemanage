package com.mdream.gamemanage.common.xml;

import com.mdream.gamemanage.common.api.BaseDaoItl;
import com.mdream.gamemanage.common.api.BaseServiceItl;

public interface JxlResolve {
	
	public void filling(String file,int start,Class clazz,BaseServiceItl service)throws Exception;
	
	//public void reading();
	
}
