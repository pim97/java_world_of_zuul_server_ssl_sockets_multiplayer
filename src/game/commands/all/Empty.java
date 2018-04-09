package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Empty extends HandleAbstractCommands {

	public Empty(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		getGame().getFrameHandler().getTextArea().setText("");
		getGame().getFrameHandler().getTextArea2().setText("");
	}

}
