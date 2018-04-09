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

public class Een extends RoomAbstractLoading {

	public Een(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useItem(String name, int amount) {
		String itemName = name.toLowerCase();
		
			
			// TODO Auto-generated method stub
		switch (itemName) {
		case "pickaxe":
			getGame().printText("You've used the pickaxe to mine a hole in the prison");
			//getGame().printTextToConsole("The pickaxe broke during the process");
			getGame().getCommmandHandler().goRoom(this.getClass().getSimpleName(), RoomDirections.DIG);
			setSolvedRoom(true);
			break;
		}
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.DIG,
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Drie"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
				new NPC(this, "Prison_Guard", 150, 3,
				
				new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"Can you just shut up prisoner?",
					"There's no point in escaping from here",
					"You won't even get far"
				}),
				new DialoguePlayerVsNPC(DialogueOption.PLAYER, new String[] {
					"... being very silent",
				}))
		}));
	}

	@Override
	public ArrayList<PlayerObject> loadObjects() {
		return new ArrayList<PlayerObject>(Arrays.asList(new PlayerObject[] {
				new PlayerObject("Pickaxe", 1),
				new PlayerObject("Apple", 2),
				new PlayerObject("MagicCookie", 1),
				new PlayerObject("Beamer", 1)
		}));
	}

	@Override
	public RoomTypes loadType() {
		// TODO Auto-generated method stub
		return RoomTypes.PUZZLE;
	}

	@Override
	public boolean continueRequirement() {
		if (!getGame().getNPCHandler().getNPC("Prison_Guard").isPresent() || isSolvedRoom()) {
			setSolvedRoom(true);
			getGame().printText("\n== Congratulations == \nCongratulations, you've solved the "+getType());
			return true;
		}
		return false;
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
		return 1;
	}






}
