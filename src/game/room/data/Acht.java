package game.room.data;

import java.util.ArrayList;
import java.util.Arrays;

import game.game.Game;
import game.game.entity.NPC;
import game.game.entity.objects.PlayerObject;
import game.room.Room;
import game.room.RoomAbstractLoading;
import game.room.RoomDirections;
import game.room.RoomTypes;

public class Acht extends RoomAbstractLoading {

	public Acht(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useItem(String name, int amount) {
		
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				
		};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
				
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
			
		}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.TELEPORT_ROOM;
	}

	@Override
	public boolean continueRequirement() {
		return true;
	}

	@Override
	public void dropNPCItems(String npc) {
	}

	@Override
	public void actionOnLoadRoom() {
		Room teleportRoom = getGame().getRoomsHandler().getRooms().get((int)(Math.random() * getGame().getRoomsHandler().getRooms().size()));
		getGame().printText("You've been randomly teleported to the room: "+teleportRoom.getDescription());
		getGame().setCurrentRoom(teleportRoom);
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 8;
	}

}
