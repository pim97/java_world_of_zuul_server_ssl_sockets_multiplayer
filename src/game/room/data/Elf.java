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

public class Elf extends RoomAbstractLoading {

	public Elf(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.EAST
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Twaalf"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
			new NPC(this, "BossSkeleton", 600, 5,
				
				new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You are gonna die hooman!!!",
				}))
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
				new PlayerObject("Pear", 2),
				new PlayerObject("Apple", 1),
				new PlayerObject("JohnSnowCape", 1),
		}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.KILL;
	}

	@Override
	public boolean continueRequirement() {
		if (!getGame().getNPCHandler().getNPC("BossSkeleton").isPresent()) {
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
		case "BossSkeleton":
			getObjects().add(new PlayerObject("SkeletonScimitar", 1, 5));
			getGame().printText("The boss has dropped an item!");
		break;
		}
	}

	@Override
	public void actionOnLoadRoom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 11;
	}
	

	

}
