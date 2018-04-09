package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Prev extends HandleAbstractCommands {

	public Prev(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		getGame().getPlayer().excecuteLastCommand();
	}

}
