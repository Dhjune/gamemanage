package com.mdream.gamemanage.model.client;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

@Embeddable
public class SlideRefPK implements Serializable{

	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = 2837385722186022151L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="slideId")
	@Expose
	private Slide slide;
	
	
	@ManyToOne()
	@JoinColumn(name="slideShowId")
	@Expose
	private SlideShow slideShow;

	
	public SlideRefPK(){
		
	}
	
	public SlideRefPK(Slide slide,SlideShow slideShow) {
		// TODO Auto-generated constructor stub
		this.slide =  slide;
		this.slideShow =  slideShow;
		
	}
	
	public Slide getSlide() {
		return slide;
	}

	public void setSlide(Slide slide) {
		this.slide = slide;
	}

	public SlideShow getSlideShow() {
		return slideShow;
	}

	public void setSlideShow(SlideShow slideShow) {
		this.slideShow = slideShow;
	}
	
	

}
