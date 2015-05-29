package com.mdream.gamemanage.common.inteceptor;

import java.lang.reflect.Method;
import java.text.Normalizer.Form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mdream.gamemanage.common.tools.ToolsFactory;



public class FormRepeatInterceptor implements HandlerInterceptor{
		
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object handler, Exception arg3)
			throws Exception {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
        FormToken annotation = method.getAnnotation(FormToken.class);
        if (annotation != null) {
        	
        	boolean RemoveSession = annotation.RemoveToken();
        	
            if (RemoveSession && !isRepeatSubmit(request)) {  
            	
            	request.getSession().removeAttribute("token"); 
            	
            }
        }

	}

	
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
				
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		 HandlerMethod handlerMethod = (HandlerMethod) handler;		 		 
         Method method = handlerMethod.getMethod();                  
         FormToken annotation = method.getAnnotation(FormToken.class);		
         if (annotation != null) {
             boolean SaveSession = annotation.SaveToken();
             if (SaveSession) {         
            	 
            	 String token = RandomStringUtils.randomAlphabetic(24);            	 
                 request.getSession().setAttribute("token", token);    
                 
             }		
             boolean RemoveSession = annotation.RemoveToken();
             if (RemoveSession) {        	 
		         if (isRepeatSubmit(request)) {
		        	 
		        	response.sendRedirect(ToolsFactory.urlTools.tr("error/repeat"));
		        	return false;
		        	
		         }                          
             }
        }         
        return true;
	}
	
	private boolean isRepeatSubmit(HttpServletRequest request) {
	
        String serverToken = (String) request.getSession().getAttribute("token");        
        if (serverToken == null) {      	
            return true;           
        }
        String clinetToken = request.getParameter("token");
	        if (clinetToken == null){	        	
            return true;               
        }
        if (!serverToken.equals(clinetToken)) {             	
            return true;
        }      
        return false;
    }

}
