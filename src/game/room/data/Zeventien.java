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

public class Zeventien extends RoomAbstractLoading {

	public Zeventien(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.NORTH, RoomDirections.SOUTH
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Achttien"),
				getGame().getRoomsHandler().getRoom("Vijftien"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
			new NPC(this, "JohnSnow", 100000, 6,
					
			new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
				"Thousands of men don't need to die. Only one of us. Let's end this the old way. You against me.",
			}))
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
				new PlayerObject("Beamer", 1, 1),
			}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.KILL_BOSS;
	}

	@Override
	public boolean continueRequirement() {
		if (!getGame().getNPCHandler().getNPC("JohnSnow").isPresent()) {
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
		String name = npc.toLowerCase();
		
		switch (name) {
		case "JohnSnow":
			getGame().printText("You've gained a targaryan sword from John Snow");
			getGame().getPlayer().addItemToInventory("TargaryenSword", 1);
			break;
		}
	}

	@Override
	public void actionOnLoadRoom() {
		getGame().printText("You've encoutered a boss John Snow!");
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 17;
	}
	

	

}
