package game.commands;

import game.game.Game;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public abstract class HandleAbstractCommands {

	/**
	 * Constructor van Handlecommands, brengt een Game object met zich mee
	 * om te gebruiken in de commands
	 * @param game
	 */
	public HandleAbstractCommands(Game game) {
		setGame(game);
	}
	
	/**
	 * Wat de command precies gaat doen elke keer dat het gecalled is
	 * @param command
	 */
	public abstract void handleCommand(CommandAction command);
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	private Game game;
}
