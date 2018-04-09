package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;
import game.sound.Sound;

public class Look extends HandleAbstractCommands {

	public Look(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		String info = getGame().getCommmandHandler().printRoomInformation(command);
		getGame().printText(info);
		
		Sound.sendSoundToClient("door");
	}

}
