package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.configs.Global;
import game.game.Game;
import game.game.items.Item;

public class Attacknpc extends HandleAbstractCommands {

	public Attacknpc(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		Item slot = getGame().getPlayer().getGear()[Global.WEAPONINDEX];
		
		if (slot == null) {
			getGame().printText("You must wear a weapon to attack a "+command.getCommandStringTwo());
		} else {
			String wName = getGame().getPlayer().getGear()[Global.WEAPONINDEX].getItemName();
			getGame().getNPCHandler().attackNPC(command.getCommandStringTwo(), wName);
		}
	}

}
