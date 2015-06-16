package com.mdream.gamemanage.model.resulttransformer;

public class GameTypeTrans {
	
	private Integer gameId;
	
	private Integer gameTypeId;
	
	private Integer status;
	
	private Integer sort ;
	
	private String typeName;
	
	private String gameName;
	
	public GameTypeTrans(){
		
	}
		
	public GameTypeTrans(Integer gameId,Integer gameTypeId ,Integer status){
		
		this.gameId =  gameId;
		this.gameTypeId =  gameTypeId;
		this.status =  status;
		
	}
	
	public GameTypeTrans(Integer gameId,Integer gameTypeId,String typeName,String gameName,Integer sort){
		
		this.gameId =  gameId;
		this.gameTypeId =  gameTypeId;
		this.sort =  sort;
		this.typeName = typeName;
		this.gameName =  gameName;
		
	}
	
	public Integer getGameTypeId() {
		return gameTypeId;
	}

	public void setGameTypeId(Integer gameTypeId) {
		this.gameTypeId = gameTypeId;
	}

	public Integer getGameId() {
		
		return gameId;
		
	}

	public void setGameId(Integer gameId) {
		
		this.gameId = gameId;
		
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	
	
}
