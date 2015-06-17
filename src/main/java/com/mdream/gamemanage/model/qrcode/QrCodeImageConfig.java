package com.mdream.gamemanage.model.qrcode;

import java.awt.Color;
import java.util.HashMap;
import java.util.Hashtable;

import com.google.zxing.EncodeHintType;

public class QrCodeImageConfig {
	
	public static final int DEFAULT_BACKGROUD_COLOR = 0xFFFFFFFF;	
	public static final int DEFAULT_TO_COLOR = 0xff000000;
	public static final String DEFAULT_FORMAT = "png";
	
	private int background_color;
	
	private int to_color = 0xFFFFFFFF;
	
	private String bg_color_str;
	
	private String to_color_str;
	
	private String imagePath;
	
	private String imageUrl ;
	
	private String format;
	
	private int width = 200;
	
	private int height = 200;
	
	private String content;
	
	private  HashMap<EncodeHintType, Object> hints;
	
	public  QrCodeImageConfig(){
		background_color = DEFAULT_BACKGROUD_COLOR;
		to_color = DEFAULT_TO_COLOR;
		format = DEFAULT_FORMAT;
		hints =  new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	}
	
	public QrCodeImageConfig(int to_color,String imagePath){
		
		this.to_color = to_color;
		format = DEFAULT_FORMAT;
		this.imagePath = imagePath;
		
	}

	public int getBackground_color() {
		return background_color;
	}

	public void setBackground_color(int background_color) {
		this.background_color = background_color;
	}

	public int getTo_color() {
		return to_color;
	}

	public void setTo_color(int to_color) {
		this.to_color = to_color;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public HashMap<EncodeHintType, Object> getHints() {
		return hints;
	}

	public void setHints(HashMap<EncodeHintType, Object> hints) {
		this.hints = hints;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getContent() {
		return content;
	}

	public void setContents(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTo_color_str() {
		return to_color_str;
	}

	public void setTo_color_str(String to_color_str) {
		this.to_color_str = to_color_str;
	}

	public String getBg_color_str() {
		return bg_color_str;
	}

	public void setBg_color_str(String bg_color_str) {
		this.bg_color_str = bg_color_str;
	}
	
	
	
}
