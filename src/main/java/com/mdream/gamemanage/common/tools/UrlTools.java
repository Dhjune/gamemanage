package com.mdream.gamemanage.common.tools;

import org.springframework.stereotype.Component;

@Component
public class UrlTools {
	
	private  final String  sta = "http://www.happyplay.net/gamemanage/static" ;
	
	private  final String  url = "http://www.happyplay.net/gamemanage";
	
//	private  final String  sta = "http://www.kjava.com/gamemanage/static" ;
	
//	private  final String  url = "http://www.kjava.com/gamemanage";
	
//	private  final String  sta = "http://121.199.38.56:8899/gamemanage/static" ;
	
//	private  final String  url = "http://121.199.38.56:8899/gamemanage";
		
//	private  final String  sta = "http://121.199.38.56:8080/gamemanage/static" ;
	
//	private  final String  url = "http://121.199.38.56:8080/gamemanage";
	
//	private  final String  sta = "http://localhost:8080/gamemanage/static" ;
	
//	private  final String  url = "http://localhost:8080/gamemanage";
	
	

	public String getSta() {
		
		return sta;
		
	}

	public String getUrl() {
		return url;
	}
	
	/**
	 * 
	 * @Title: ts 
	 * @Description: 用于拼接静态文件的访问路径 
	 * @param s
	 * @return
	 * @return String
	 */
	public String ts(String s){
		
		return sta+s;
		
	}
	
	/**
	 * @Title: tr
	 * @Description: 用于动态url的拼接
	 * @param s
	 * @return String
	 */
	public String  tr(String s ){
		
		return url+s;
		
	}
		
}
