package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Los extends HandleAbstractCommands {

	public Los(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		getGame().getPlayer().deleteItemAndDropInRoom(command);
	}

}
