package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Send extends HandleAbstractCommands {

	public Send(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		getGame().getServer().sendStringToClients(command.getCommandStringTwo());
	}

}
