package game.room.load;

import java.lang.reflect.InvocationTargetException;

import game.commands.HandleAbstractCommands;
import game.commands.ProcessCommand;
import game.game.Game;
import game.room.Room;
import game.room.RoomAbstractLoading;
import game.util.ClassFinder;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class LoadClasses {
	
	private Game game;
	
	public LoadClasses(Game game) {
		setGame(game);
	}
	
	/**
	 * Loads all classes in a package with reflection
	 */
	public void loadRooms() {
		for (Class<?> foundClass : ClassFinder.find("game.room.data")) {
			Room room = new Room(foundClass.getSimpleName());
			getGame().getRoomsHandler().getRooms().add(room);
		}
		for (Class<?> foundClass : ClassFinder.find("game.room.data")) {
			try {
				RoomAbstractLoading roomClass = (RoomAbstractLoading) foundClass.getConstructor(Game.class).newInstance(getGame());
				if (Game.DEBUG_MODE) {
					getGame().printText("RoomAction classes found, added new RoomAction room "+roomClass.getClass().getSimpleName());
				}
				getGame().getRoomsHandler().getGameRooms().add(roomClass);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads all classes in a package with reflection
	 */
	public void loadCommands() {
		for (Class<?> foundClass : ClassFinder.find("game.commands.all")) {
			try {
				HandleAbstractCommands commandClass = (HandleAbstractCommands) foundClass.getConstructor(Game.class).newInstance(getGame());
				if (Game.DEBUG_MODE) {
					getGame().printText("Command classes found, added new command room "+commandClass.getClass().getSimpleName());
				}
				ProcessCommand.getCommands().add(commandClass);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
}