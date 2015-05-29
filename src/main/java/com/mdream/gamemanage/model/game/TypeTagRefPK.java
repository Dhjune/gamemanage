package com.mdream.gamemanage.model.game;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TypeTagRefPK implements Serializable{
	
	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = -8257143341055807513L;


	@ManyToOne
    @JoinColumn(name="tagId")
	private Tag tag;
		
	
	@ManyToOne
    @JoinColumn(name="gameTypeId")
	private GameType gameType ;

	
	

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
}
