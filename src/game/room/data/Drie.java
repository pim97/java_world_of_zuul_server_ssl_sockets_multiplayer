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

public class Drie extends RoomAbstractLoading {

	public Drie(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.WEST, RoomDirections.EAST, RoomDirections.SOUTH, RoomDirections.NORTH
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Een"),
				getGame().getRoomsHandler().getRoom("Zeven"),
				getGame().getRoomsHandler().getRoom("Vier"),
				getGame().getRoomsHandler().getRoom("Twee"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
				new NPC(this, "Guard", 100, 2, new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"You small little thing!",
					"I am going to crush you!"
				}),
				new DialoguePlayerVsNPC(DialogueOption.PLAYER, new String[] {
					"I will attack you until you've dropped your key!",
				}),
				new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"We will see you outsider!",
				})),
				
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
				new PlayerObject("Shovel", 1),
				new PlayerObject("Apple", 4)
			}));
	}

	@Override
	public RoomTypes loadType() {
		return RoomTypes.PUZZLE;
	}

	@Override
	public boolean continueRequirement() {
		return isSolvedRoom();
	}

	@Override
	public void useItem(String name, int amount) {
		name = name.toLowerCase();
		switch (name) {
		case "hallwaykeyone":
			getGame().printText("You've continued to the next room");
			getGame().getCommmandHandler().goRoom(this.getClass().getSimpleName(), RoomDirections.EAST);
			setSolvedRoom(true);
			break;
		}
	}

	@Override
	public void dropNPCItems(String npc) {
		// TODO Auto-generated method stub
		
		switch (npc) {
		case "Guard":
			getGame().getPlayer().addItemToInventory("HallwayKeyOne", 1);
			break;
		}
		
	}

	@Override
	public void actionOnLoadRoom() {
		// TODO Auto-generated method stub
		getGame().printText("You've encoutered a guard that wants to attack you");
	}

	@Override
	public int roomId() {
		// TODO Auto-generated method stub
		return 3;
	}
	

	

}
