package game.room;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public enum RoomDirections {
	NORTH, SOUTH, WEST, EAST, DIG;
	
	public static RoomDirections getDirection(String direction) {
		for (RoomDirections dir : values()) {
			if (dir.name().equalsIgnoreCase(direction)) {
				return dir;
			}
		}
		return null;
	}
}
