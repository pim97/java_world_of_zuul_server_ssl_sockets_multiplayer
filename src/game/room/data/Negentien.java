package game.room.data;

import java.util.ArrayList;
import java.util.Arrays;

import game.dialogue.DialogueOption;
import game.dialogue.DialoguePlayerVsNPC;
import game.game.Game;
import game.game.entity.NPC;
import game.game.entity.objects.PlayerObject;
import game.room.Room;
import game.room.RoomAbstractLoading;
import game.room.RoomDirections;
import game.room.RoomTypes;

public class Negentien extends RoomAbstractLoading {

	public Negentien(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.NORTH, RoomDirections.WEST, RoomDirections.EAST
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Twintig"),
				getGame().getRoomsHandler().getRoom("Eenentwintig"),
				getGame().getRoomsHandler().getRoom("Achttien"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
			new NPC(this, "GeorgeRRMartin", 5000, 10,
					new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
						"Quote me!",
				})),
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
			}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.PUZZLE;
	}

	@Override
	public boolean continueRequirement() {
		if (!getGame().getNPCHandler().getNPC("GeorgeRRMartin").isPresent()) {
			return true;
		}
		getGame().printText("You haven't defeated the boss yet!");
		return false;
	}

	@Override
	public void useItem(String name, int amount) {
	}

	@Override
	public void dropNPCItems(String npc) {
		String name = npc.toLowerCase();
		
		if (name.equalsIgnoreCase("GeorgeRRMartin")) {
			getGame().getPlayer().addItemToInventory("GeorgeHead", 1);
			getGame().printText("You've defeated the creator! Congratulations!");
		}
		
	}

	@Override
	public void actionOnLoadRoom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 19;
	}
	

	

}
