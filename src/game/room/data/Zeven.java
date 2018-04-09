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

public class Zeven extends RoomAbstractLoading {

	public Zeven(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean continueRequirement() {
		if (getGame().getPlayer().playerHasItem("PrisonKey", 1)) {
			getGame().getPlayer().deleteItemFromInventory("PrisonKey", 1);
			setSolvedRoom(true);
			return true;
		}
		return isSolvedRoom();
	}

	@Override
	public void useItem(String name, int amount) {
		
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
			RoomDirections.WEST,
			RoomDirections.EAST
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
			getGame().getRoomsHandler().getRoom("Drie"),
			getGame().getRoomsHandler().getRoom("Acht")
		};
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.KEY;
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
				new NPC(this, "Key_master", new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You require a key to continue in this room",
					"This key has been hidden at the prison door you started at"
				})),
				new NPC(this, "Goblin", true, new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"I want to hurt you!",
				})),
		}));
		
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
			new PlayerObject("Apple", 1)
		}));
	}

	@Override
	public void dropNPCItems(String npc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionOnLoadRoom() {
		// TODO Auto-generated method stub
		getGame().getRoomsHandler().getRoomAction("Een").getObjects().add(new PlayerObject("PrisonKey", 1, 5));
		getGame().printText("A key has appeared in the prison room you started at! (room id 1)");
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 7;
	}

}
