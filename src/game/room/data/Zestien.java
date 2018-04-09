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

public class Zestien extends RoomAbstractLoading {

	public Zestien(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomDirections[] loadDirections() {
		return new RoomDirections[] {
				RoomDirections.WEST
		};
	}

	@Override
	public Room[] loadRooms() {
		return new Room[] {
				getGame().getRoomsHandler().getRoom("Vijftien"),
			};
	}

	@Override
	public ArrayList<NPC> loadNPCs() {
		return new ArrayList<NPC>(Arrays.asList(new NPC[] {
			new NPC(this, "Trickster", 0, 0,
			
			new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
				"You've been tricket my boi!?",
			}),
			new DialoguePlayerVsNPC(DialogueOption.PLAYER, new String[] {
				"... how do I get back up?",
			}),
			new DialoguePlayerVsNPC(DialogueOption.NPC, new String[] {
					"NOT! muauaahhaha",
			}))
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
		return false;
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
		return 16;
	}
	

	

}
