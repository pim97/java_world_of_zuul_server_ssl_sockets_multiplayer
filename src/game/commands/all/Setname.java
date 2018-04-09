package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;
import game.game.entity.Player;

public class Setname extends HandleAbstractCommands {

	public Setname(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		if (getGame().getPlayer() != null) {
    		getGame().printText("You've already set a name, you can't do this again");
    		return;
    	}
    	String name = command.getCommandStringTwo();
    	getGame().setPlayer(new Player(getGame(), name));
    	getGame().printText("Congratulations, you've set your name to: "+name);
	}

}
