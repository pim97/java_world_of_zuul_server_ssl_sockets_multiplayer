package game.commands.all;

import game.commands.CommandAction;
import game.commands.HandleAbstractCommands;
import game.game.Game;

public class Serverroom extends HandleAbstractCommands {

	public Serverroom(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCommand(CommandAction command) {
		// TODO Auto-generated method stub
		game.room.Room room = getGame().getRoomsHandler().getRoom(command.getCommandStringTwo());
		
		if (room != null) {
			getGame().setCurrentRoom(room);
			getGame().printText("Moved to room: "+getGame().getCurrentRoom().getDescription());
		} else {
			getGame().printText("Couldn't find room: "+command.getCommandStringTwo());
		}
	}

}
