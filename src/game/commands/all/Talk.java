package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;
import game.sound.Sound;

public class Talk extends HandleAbstractCommands {

	public Talk(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		String dialogue = getGame().getCommmandHandler().talkToDialogue(command);
		
		if (dialogue != null && !dialogue.isEmpty()) {
			getGame().printText(dialogue);
			Sound.sendSoundToClient("mumbling");
		} else {
			getGame().printText("This NPC is not in this room");
		}
	}

}
