package game.room;

import java.util.HashMap;
import java.util.Set;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class Room {
	
	private String description;
	
	private HashMap<RoomDirections, Room> exists;
	
	public Room(String description) {
		setDescription(description);
		setExists(new HashMap<RoomDirections, Room>());
	}
	
	public void setExit(RoomDirections direction, Room neighbor) {
		//Game.printTextToConsole(this+" "+direction+" "+neighbor);
        getExists().put(direction, neighbor);
    }
	
	public String getLongDescription() {
		return "\nYou are currently in the room: "+getDescription()+".\n" +getExitString();
	}
	
	public String getExitString() {
		//Game.printTextToConsole("ROom.java"+this.getDescription());
		StringBuilder string = new StringBuilder();
		string.append("Exits: ");
		
		Set<RoomDirections> keys = getExists().keySet();
		for (RoomDirections exit : keys) {
			string.append(" "+ exit);
		}
		return string.toString();
	}
	
	public Room getExit(RoomDirections direction) {
		return getExists().get(direction);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HashMap<RoomDirections, Room> getExists() {
		return exists;
	}

	public void setExists(HashMap<RoomDirections, Room> hashMap) {
		this.exists = hashMap;
	}

}
