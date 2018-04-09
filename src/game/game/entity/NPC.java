package game.game.entity;

import game.dialogue.DialoguePlayerVsNPC;
import game.room.RoomAbstractLoading;

/**
 * De NPC klas met alle variabelen van een NPC
 * @author pim
 *
 */
public class NPC {

	public NPC(RoomAbstractLoading room, String name, DialoguePlayerVsNPC... dialogue) {
		setName(name);
		setDialogue(dialogue);
		setRoom(room);
		setHealth(0);
		setLevel(1);
	}
	public NPC(RoomAbstractLoading room, String name, boolean moveRooms, DialoguePlayerVsNPC... dialogue) {
		setName(name);
		setDialogue(dialogue);
		setRoom(room);
		setHealth(0);
		setLevel(1);
		setMoveRooms(true);
	}
	public NPC(RoomAbstractLoading room, String name, int health, int level,  DialoguePlayerVsNPC... dialogue) {
		setName(name);
		setDialogue(dialogue);
		setRoom(room);
		setHealth(health);
		setLevel(level);
	}
	
	public NPC(RoomAbstractLoading room, String name, int health, int level, boolean moveRooms,  DialoguePlayerVsNPC... dialogue) {
		setName(name);
		setDialogue(dialogue);
		setRoom(room);
		setHealth(health);
		setLevel(level);
		setMoveRooms(moveRooms);
	}
	
	
	private int health, level;
	private String name;
	private DialoguePlayerVsNPC[] dialogue;
	private RoomAbstractLoading room;
	
	/**
	 * Of de NPC in verschillende kamers mag bewegen elke keer dat een speler naar een andere kamer gaat
	 */
	private boolean moveRooms;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DialoguePlayerVsNPC[] getDialogue() {
		return dialogue;
	}

	public void setDialogue(DialoguePlayerVsNPC[] dialogue) {
		this.dialogue = dialogue;
	}

	public RoomAbstractLoading getRoom() {
		return room;
	}

	public void setRoom(RoomAbstractLoading room) {
		this.room = room;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isMoveRooms() {
		return moveRooms;
	}
	public void setMoveRooms(boolean moveRooms) {
		this.moveRooms = moveRooms;
	}

	/*public ArrayList<DialogueNPC> getDialogue() {
		return dialogue;
	}

	public void setDialogue(ArrayList<DialogueNPC> dialogue) {
		this.dialogue = dialogue;
	}*/
}
