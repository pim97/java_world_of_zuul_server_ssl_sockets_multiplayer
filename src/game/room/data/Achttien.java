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

public class Achttien extends RoomAbstractLoading {

	public Achttien(Game game) {
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
				getGame().getRoomsHandler().getRoom("Negentien"),
				getGame().getRoomsHandler().getRoom("Zeventien"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
			new NPC(this, "DaenerysTargaryen", 2000, 7,
					
			new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You shall fear my dragons!",
			})),
			
			new NPC(this, "BlueDragon", 500, 4,
				new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You will burn!!",
			})),
			
			new NPC(this, "RedDragon", 500, 4,
				new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You will burn!!",
			})),
			
			new NPC(this, "Guide",
				new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You might need something to protect yourself from the dragons",
			}))
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
				new PlayerObject("Apple", 4)
			}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.KILL_BOSS;
	}

	@Override
	public boolean continueRequirement() {
		if (!getGame().getNPCHandler().getNPC("DaenerysTargaryen").isPresent() &&
				!getGame().getNPCHandler().getNPC("RedDragon").isPresent() &&
					!getGame().getNPCHandler().getNPC("BlueDragon").isPresent()) {
			setSolvedRoom(true);
			return true;
		}
		return false;
	}

	@Override
	public void useItem(String name, int amount) {
	}

	@Override
	public void dropNPCItems(String npc) {
		// TODO Auto-generated method stub
		String name = npc.toLowerCase();
		
		if (name.equalsIgnoreCase("DaenerysTargaryen")) {
			getGame().getPlayer().addItemToInventory("FireSword", 1);
			getGame().printText("You've obtained an ultra rare fire sword!");
		}
	}

	@Override
	public void actionOnLoadRoom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 18;
	}
	

	

}
