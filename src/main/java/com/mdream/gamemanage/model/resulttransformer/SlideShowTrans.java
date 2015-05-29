package com.mdream.gamemanage.model.resulttransformer;

public class SlideShowTrans {
	
	private Integer slideId;
	
	private Integer slideShowId;
	
	private Integer  status = 1;
	
	private Integer sort;
	
	public SlideShowTrans(){
		
	}
	
	public SlideShowTrans(Integer slideId,Integer slideShowId){
		this.slideId = slideId;
		this.slideShowId =  slideShowId;
	}
	
	public SlideShowTrans(Integer slideId,Integer slideShowId,Integer status){
		this.slideId = slideId;
		this.slideShowId =  slideShowId;
		this.status =  status;
	}

	public Integer getSlideId() {
		return slideId;
	}

	public void setSlideId(Integer slideId) {
		this.slideId = slideId;
	}

	public Integer getSlideShowId() {
		return slideShowId;
	}

	public void setSlideShowId(Integer slideShowId) {
		this.slideShowId = slideShowId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	

}
