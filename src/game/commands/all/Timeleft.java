package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Timeleft extends HandleAbstractCommands {

	public Timeleft(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		getGame().printText(getGame().minutesLeft(getGame().getCurrentTime()));
	}

}
