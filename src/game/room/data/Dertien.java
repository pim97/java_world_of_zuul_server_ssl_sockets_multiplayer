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

public class Dertien extends RoomAbstractLoading {

	public Dertien(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.WEST, RoomDirections.EAST
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Twaalf"),
				getGame().getRoomsHandler().getRoom("Veertien"),
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
				new PlayerObject("YellowKey", 1),
				new PlayerObject("MagicCookie", 1),
			}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.PUZZLE;
	}

	@Override
	public boolean continueRequirement() {
		return true;
	}

	@Override
	public void useItem(String name, int amount) {
	}

	@Override
	public void dropNPCItems(String npc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionOnLoadRoom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 13;
	}
	

	

}
