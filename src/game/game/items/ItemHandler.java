package game.game.items;

import game.game.Game;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class ItemHandler {

	public ItemHandler(Game game) {
		setGame(game);
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	private Game game;
}
