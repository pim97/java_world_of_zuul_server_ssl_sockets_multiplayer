package game.room;

import java.util.ArrayList;

import game.game.Game;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
* 
* Slaat alle games op in deze klasse
*/
public class RoomsHandler {
	
	public RoomsHandler(Game game) {
		setGame(game);
	}
	
	private ArrayList<RoomAbstractLoading> gameRooms = new ArrayList<RoomAbstractLoading>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	private Game game;
	
	public Room getRoom(String roomName) {
		for (Room roomObject : rooms) {
			if (roomObject.getDescription().equalsIgnoreCase(roomName)) {
				return roomObject;
			}
		}
		return null;
	}
	
	public RoomAbstractLoading getRoomAction(String roomName) {
		for (RoomAbstractLoading roomObject : gameRooms) {
			if (roomObject.getRoomName().equalsIgnoreCase(roomName)) {
				return roomObject;
			}
		}
		return null;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ArrayList<RoomAbstractLoading> getGameRooms() {
		return gameRooms;
	}

	public void setGameRooms(ArrayList<RoomAbstractLoading> gameRooms) {
		this.gameRooms = gameRooms;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
}
