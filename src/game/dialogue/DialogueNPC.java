package game.dialogue;

import java.util.ArrayList;

import game.room.RoomAbstractLoading;


/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class DialogueNPC {
	
	public DialogueNPC(RoomAbstractLoading room, ArrayList<DialoguePlayerVsNPC> dialogue, String npcName) {
		setDialogue(dialogue);
		setNpcName(npcName);
		setRoom(room);
	}

	private RoomAbstractLoading room;
	private String npcName;
	
	/**
	 * Alle dialogues van de NPC of Speler
	 */
	private ArrayList<DialoguePlayerVsNPC> dialogue = new ArrayList<DialoguePlayerVsNPC>();
	
	public ArrayList<DialoguePlayerVsNPC> getDialogue() {
		return dialogue;
	}
	public void setDialogue(ArrayList<DialoguePlayerVsNPC> dialogue) {
		this.dialogue = dialogue;
	}
	public String getNpcName() {
		return npcName;
	}
	public void setNpcName(String npcName) {
		this.npcName = npcName;
	}
	public RoomAbstractLoading getRoom() {
		return room;
	}
	public void setRoom(RoomAbstractLoading room) {
		this.room = room;
	}
}
