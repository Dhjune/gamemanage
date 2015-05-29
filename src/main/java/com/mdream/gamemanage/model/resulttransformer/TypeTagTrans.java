package com.mdream.gamemanage.model.resulttransformer;

public class TypeTagTrans {
	

	private Integer tagId;
	
	private Integer  gameTypeId;
	
	private Integer  status;
	
	private String tagName;
	
	public TypeTagTrans(){
		
	}
	
	public TypeTagTrans(Integer gameTypeId,Integer tagId){
		
		this.setTagId(tagId);
		this.gameTypeId =  gameTypeId;
		
	}
	
	public TypeTagTrans(Integer gameTypeId,Integer tagId,Integer status){
		
		this.setTagId(tagId);
		this.gameTypeId =  gameTypeId;
		this.status =  status;
		
	}

	
	public Integer getGameTypeId() {
		return gameTypeId;
	}

	public void setGameTypeId(Integer gameTypeId) {
		this.gameTypeId = gameTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
	
}
