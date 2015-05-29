package com.mdream.gamemanage.model.game;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class GameTypeRefPK implements Serializable{
	
	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = 5139477663965929974L;


	@ManyToOne
    @JoinColumn(name="gameId")
	private Game game;
		
	
	@ManyToOne
    @JoinColumn(name="gameTypeId")
	private GameType gameType ;

	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	
}
