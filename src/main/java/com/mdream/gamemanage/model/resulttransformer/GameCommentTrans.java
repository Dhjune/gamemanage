package com.mdream.gamemanage.model.resulttransformer;

public class GameCommentTrans {

	private Integer  gameId;
	
	
	private Integer commentId;
	
	private Integer  count;
	
	private Integer status=1;
	
	private String content;
	
	public GameCommentTrans(){
		
		
		
	}
	
	public GameCommentTrans(Integer gameId,Integer commentId,Integer  count){
		
		this.gameId = gameId;
		
		this.commentId =  commentId;
		
		this.count =count;
	}
	
	public GameCommentTrans(Integer gameId,Integer commentId,Integer  count,Integer status){
		
		this.gameId = gameId;
		
		this.commentId =  commentId;
		
		this.count =count;
		
		this.status =  status;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
