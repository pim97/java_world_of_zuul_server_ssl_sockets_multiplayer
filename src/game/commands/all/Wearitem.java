package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Wearitem extends HandleAbstractCommands {

	public Wearitem(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		String[] splittedStringWeapon = command.getCommandStringTwo().split("_");
		if (splittedStringWeapon.length == 2) {
			getGame().getPlayer().wearItem(splittedStringWeapon[0], Integer.parseInt(splittedStringWeapon[1]));
		} else {
			getGame().printText("Please use an underscore to define your weapon name and amount");
		}
	}

}
