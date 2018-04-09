package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Serverpickup extends HandleAbstractCommands {

	public Serverpickup(Game game) {
		super(game);
	}

	@Override
	public void handleCommand(CommandAction command) {
		String[] splittedStringWeapon = command.getCommandStringTwo().split("_");
		getGame().getPlayer().addItemToInventory(splittedStringWeapon[0], Integer.parseInt(splittedStringWeapon[1]));
		getGame().printText("The item: "+splittedStringWeapon[0]+" "+splittedStringWeapon[1]+" was added to your inventory");
	}

}
