package com.mdream.gamemanage.common.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.common.tools.UrlTools;




public class DefaultRequestListener implements ServletRequestListener{

	public void requestDestroyed(ServletRequestEvent event) {
		
		
	}

	public void requestInitialized(ServletRequestEvent event) {
		
		HttpServletRequest res = (HttpServletRequest) event.getServletRequest();
		UrlTools urlTools =  ToolsFactory.urlTools;
		res.setAttribute("urlTools", urlTools);
		
		
	}

}
