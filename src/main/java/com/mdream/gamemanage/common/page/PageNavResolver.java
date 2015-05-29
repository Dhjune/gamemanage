package com.mdream.gamemanage.common.page;

import java.util.List;

import org.springframework.stereotype.Component;



@Component
public class PageNavResolver {

	
	public <T> PageNav<T> initPagenav(List<T> result, T target,long total, int pageSize,
			int pageIndex, String url){
		
		PageNav<T> context = null;
		context = new PageNav<T>(result, total, pageSize, pageIndex, url);					
		return context;
		
	}
	
}
