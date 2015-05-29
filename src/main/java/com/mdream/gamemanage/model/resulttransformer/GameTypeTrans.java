package com.mdream.gamemanage.model.resulttransformer;

public class GameTypeTrans {
	
	private Integer gameId;
	
	private Integer gameTypeId;
	
	private Integer status;
	
	private String typeName;
	
	public GameTypeTrans(){
		
	}
		
	public GameTypeTrans(Integer gameId,Integer gameTypeId ,Integer status){
		
		this.gameId =  gameId;
		this.gameTypeId =  gameTypeId;
		this.status =  status;
		
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
	
	
	
}
