package com.mdream.gamemanage.model.game;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class GameTagRefPK implements Serializable{

	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = -9084950753638670718L;
	

	@ManyToOne
    @JoinColumn(name="tagId")
	private Tag tag;

	@ManyToOne
    @JoinColumn(name="gameId")
	private Game game;
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
