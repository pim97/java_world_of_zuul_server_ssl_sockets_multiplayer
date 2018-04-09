package game.dialogue;

import java.util.ArrayList;

import game.game.Game;
import game.room.Room;
import game.room.RoomAbstractLoading;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class DialogueHandler {
	
	/**
	 * Constructor met de game
	 * @param game
	 */
	public DialogueHandler(Game game) {
		setGame(game);
	}
	
	/**
	 * Stored de game klasse in een variabel
	 */
	private Game game;

	private ArrayList<DialogueNPC> dialogue = new ArrayList<DialogueNPC>();

	/**
	 * Dialogue toegevoegd aan de arraylist
	 * @param room
	 * @param npcName
	 * @param dia
	 */
	public void addDialogue(RoomAbstractLoading room, String npcName, DialoguePlayerVsNPC... dia) {
		ArrayList<DialoguePlayerVsNPC> dialogueChatNpc = new ArrayList<DialoguePlayerVsNPC>();
		
		for (int i = 0; i < dia.length; i++) {
			dialogueChatNpc.add(dia[i]);
		}
		DialogueNPC dialogue = new DialogueNPC(room, dialogueChatNpc, npcName);
		
		if (!dialogueContainsNPC(room.getRoom(), npcName)) {
			getDialogue().add(dialogue);
			
			if (Game.DEBUG_MODE) {
				getGame().printText("Dialogue was added to the list");
			}
		} else {
			getGame().printText("Dialogue already contains name");
		}
	}
	
	/**
	 * Returned de dialogueNPC met een kamer een een string
	 * @param room
	 * @param npcName
	 * @return
	 */
	public DialogueNPC getDialogueNPC(Room room, String npcName) {
		for (DialogueNPC dia : getDialogue()) {
			if (dia.getNpcName().equalsIgnoreCase(npcName) && dia.getRoom().getRoom() == room) {
				return dia;
			}
		}
		return null;
	}
	
	/**
	 * Checked of dialogue van de npc in deze kamer op het moment is
	 * @param room
	 * @param npcName
	 * @return
	 */
	private boolean dialogueContainsNPC(Room room, String npcName) {
		for (DialogueNPC dia : getDialogue()) {
			if (dia.getNpcName().equalsIgnoreCase(npcName) && dia.getRoom().getRoom() == room) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<DialogueNPC> getDialogue() {
		return dialogue;
	}

	public void setDialogue(ArrayList<DialogueNPC> dialogue) {
		this.dialogue = dialogue;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
