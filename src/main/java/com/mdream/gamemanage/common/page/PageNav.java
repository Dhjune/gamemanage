package com.mdream.gamemanage.common.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

public class PageNav<T> {
	@Expose	
	private int pageSize;
	@Expose	
	private int pageIndex;
	@Expose	
	private int pageCount;	
	@Expose	
	private long total;
	@Expose	
	private List<T> list ;
	@Expose	
	private String pageUrl;
	@Expose	
	private int next;	
	@Expose	
	private String nextUrl;
	@Expose	
	private int prev;
	@Expose	
	private String prevUrl;
	@Expose	
	private List<Map> showitems = new ArrayList<Map>();
	@Expose	
	private String view="";	
	public void initPage(){
		
		pageCount = (int) ((total % pageSize >0)? (total / pageSize +1):(total / pageSize)) ;
		if(pageIndex < pageCount){			
			next = pageIndex + 1;
		}
		if(next>0){			
			setNextUrl(String.format(pageUrl,"?pageIndex="+next));			
		}else{
			setNextUrl("#");
		}
		if(pageIndex >1){
			prev = pageIndex -1 ;
		}
		
		if(prev >0){
			
			prevUrl = (String.format(pageUrl,"?pageIndex="+prev));	
			
		}else{
			
			prevUrl = "#";	
			
		}
		
		if(pageCount > 8){
			
			int start = pageIndex - 4;
			int end = pageIndex + 4 ;
			if (start <=0){
				start =1;
				end = 8;				
			}
			if(end>pageCount){
				end = pageCount;
			}
			for(int i=start;i<=end;i++){
				
				Map map  = new HashMap();
				if(i == pageIndex){	
					
					map.put("url", "#");
					map.put("active",true);
					map.put("index", i);
				
				}else{	
					
					map.put("url",String.format(pageUrl,"?pageIndex="+i));
					map.put("active",false);
					map.put("index", i);	
					
				}
				
				showitems.add(map);				
			}
		}else{
			for(int i=1;i<=pageCount;i++){
				Map map  = new HashMap();
				if(i == pageIndex){
					
					map.put("url", "#");
					map.put("active",true);
					map.put("index", i);
					
				}else{
					
					map.put("url",String.format(pageUrl,"?pageIndex="+i));
					map.put("active",false);
					map.put("index", i);					
				}
				showitems.add(map);
			}
		}
		
	}
	
	public PageNav(List<T> list, long total,
			int pageSize, int pageIndex, String url) {
		
		this.total = total ;
		this.pageIndex =pageIndex;
		this.pageSize = pageSize;
		this.list = list;
		this.pageUrl = url;
		this.initPage();
			
	}
			
	public String getPageUrl() {
		return pageUrl;
	}
	
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public int getNext() {
		return next;
	}
	
	public void setNext(int next) {
		this.next = next;
	}
	
	public String getNextUrl() {
		return nextUrl;
	}
	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
	
	public int getPrev() {
		return prev;
	}
	
	public void setPrev(int prev) {
		this.prev = prev;
	}
	
	public String getPrevUrl() {
		return prevUrl;
	}
	
	public void setPrevUrl(String prevUrl) {
		this.prevUrl = prevUrl;
	}
	
	public List getShowitems() {
		return showitems;
	}
	
	public void setShowitems(List showitems) {
		this.showitems = showitems;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	
	
}
