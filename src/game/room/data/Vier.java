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

public class Vier extends RoomAbstractLoading {

	public Vier(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.SOUTH, RoomDirections.NORTH
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Vijf"),
				getGame().getRoomsHandler().getRoom("Drie"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
			new NPC(this, "BossGuard", 400, 4,
				
				new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You are gonna die!!!",
				}))
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
				new PlayerObject("Pear", 2),
				new PlayerObject("Apple", 1),
				new PlayerObject("Bronsweapon", 1)
		}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.KILL;
	}

	@Override
	public boolean continueRequirement() {
		if (!getGame().getNPCHandler().getNPC("BossGuard").isPresent()) {
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
		// TODO Auto-generated method stub
		npc = npc.toLowerCase();
		
		switch (npc) {
		case "BossGuardRoomFour":
			getObjects().add(new PlayerObject("Dagger", 1, 5));
			getGame().printText("The boss has dropped an item!");
		break;
		}
	}

	@Override
	public void actionOnLoadRoom() {
		// TODO Auto-generated method stub
		getGame().printText("Prepare to fight the boss of level 4!");
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 4;
	}
	

	

}
