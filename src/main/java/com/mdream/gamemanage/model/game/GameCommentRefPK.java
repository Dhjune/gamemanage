package com.mdream.gamemanage.model.game;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

@Embeddable
public class GameCommentRefPK implements Serializable{

	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = 199504084712734463L;
	@Expose
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="gameId")
	private Game game ;
	
	@Expose
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="commentId")
	private GameComment comment;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GameComment getComment() {
		return comment;
	}

	public void setComment(GameComment comment) {
		this.comment = comment;
	}
	
	

}
