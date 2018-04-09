package game.room;

import java.util.ArrayList;
import java.util.Iterator;

import game.commands.CommandAction;
import game.commands.CommandWordsEnum;
import game.configs.Global;
import game.food.Food;
import game.game.Game;
import game.game.combat.Misc;
import game.game.combat.Weapons;
import game.game.entity.NPC;
import game.game.entity.Player;
import game.game.entity.objects.PlayerObject;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public abstract class RoomAbstractLoading {
	
	/**
	 * Elke kamer heeft deze klasse met zich, waarmee alle gegevens van een klasse worden bewaard
	 * @param game
	 */
	public RoomAbstractLoading(Game game) {
		setGame(game);
		setRoom(getGame().getRoomsHandler().getRoom(this.getClass().getSimpleName()));
		setRoomName(getRoom().getDescription());
		setDirections(loadDirections());
		setToRoom(loadRooms());
		setNpcs(loadNPCs());
		setObjects(loadObjects());
		setType(loadType());
		
		load();
	}
	
	/**
	 * Voegt alle npcs toe aan een game en de dialogue daarmee
	 */
	public void addAllNPCS() {
		ArrayList<NPC> npcs = loadNPCs();
		getGame().getNPCHandler().getNpcs().addAll(npcs);
		
		npcs.forEach(npc -> {
			getGame().getDialogueHandler().addDialogue(this, npc.getName(), npc.getDialogue());
		});
	}
	
	/**
	 * Zet alle exits in een kamer
	 */
	public void load() {
		addAllNPCS();
		if (directions != null && toRoom != null) {
			if (directions.length == toRoom.length) {
				for (int i = 0; i < directions.length; i++) {
					if (directions[i] != null && toRoom[i] != null) {
						getRoom().setExit(directions[i], toRoom[i]);
					}
				}
			}
		}
	}
	
	/**
	 * Returned of de speler verder mag in de kamer of niet
	 * @return
	 */
	public abstract boolean continueRequirement();
	
	/**
	 * Wanneer je een kamer gebruikt in een kamer zal dit worden
	 * gebruikt
	 * @param name
	 * @param amount
	 */
	public abstract void useItem(String name, int amount);
	
	/**
	 * De locaties van alle deuren worden geladen
	 * @return
	 */
	public abstract RoomDirections[] loadDirections();
	
	/**
	 * Een link wordt gemaakt met de locaties waardoor de kamer en directies
	 * samen worden gekoppeld
	 * @return
	 */
	public abstract Room[] loadRooms();
	
	/**
	 * De puzzel type
	 * @return
	 */
	public abstract RoomTypes loadType();
	
	/**
	 * De NPCS in elke kamer worden hier gedefinieerd
	 * @return
	 */
	public abstract ArrayList<NPC> loadNPCs();
	
	/**
	 * De objecten in elke kamer worden gedfinieerd
	 * @return
	 */
	public abstract ArrayList<PlayerObject> loadObjects();
	
	/**
	 * Alle npcs in een kamer hun drops worden hier gedefineerd
	 * @param npc
	 */
	public abstract void dropNPCItems(String npc);
	
	/**
	 * Wanneer een kamer laadt, dan zal dit automatisch worden geroepen
	 */
	public abstract void actionOnLoadRoom();
	
	/**
	 * Het room ID van de kamer, terug te vinden op de map.png
	 * @return
	 */
	public abstract int roomId();
	
	/**
	 * Een random kamer wordt gerturned
	 * @return roomAction - een random kamer
	 */
	public RoomAbstractLoading getRandomRoom() {
		return getGame().getRoomsHandler().getGameRooms().get((int)(Math.random() * getGame().getRoomsHandler().getGameRooms().size()));
	}
	
	/**
	 * Zoekt naar een npc in een bepaalde kamer
	 * @param npc
	 * @return de kamer waarin de npc zich bevindt
	 */
	public RoomAbstractLoading getNPCFromRooms(NPC npc) {
		for (RoomAbstractLoading room : getGame().getRoomsHandler().getGameRooms()) {
			for (NPC npcRoom : room.getNpcs()) {
				if (npc.getName().equalsIgnoreCase(npcRoom.getName())) {
					return room;
				}
			}
		}
		return null;
	}
	
	/**
	 * De npc wordt verwijderd
	 * @param remove
	 * @param npc
	 */
	public void deleteNPCFromRoom(RoomAbstractLoading remove, NPC npc) {
		
		Iterator<NPC> iterator = remove.getNpcs().iterator();
		
		while (iterator.hasNext()) {
			NPC npcRoom = iterator.next();
			if (npcRoom.getName().equalsIgnoreCase(npc.getName())) {
				iterator.remove();
			}
		}
	}
	
	/**
	 * De npcs worden naar een andere kamer gezet die gedefinieerd zijn als bewegende npc's van kamer
	 * naar kamer
	 */
	public void moveNPCSToOtherRoom() {
		Iterator<NPC> iterator = getGame().getNPCHandler().getNpcs().iterator();
		
		getGame().printText("\n== Random walking - These monsters drop very good loot ==\n");
		while (iterator.hasNext()) {
			NPC npc = iterator.next();
			
			if (npc.isMoveRooms()) {
				//Remove from room
				RoomAbstractLoading toRemoveRoomAction = getNPCFromRooms(npc);
				deleteNPCFromRoom(toRemoveRoomAction, npc);
				
				//Add to room
				RoomAbstractLoading roomAction = getRandomRoom();
				roomAction.getNpcs().add(npc);
				
				//Message to room
				getGame().printText(npc.getName()+" has moved to: "+roomAction.getRoomName());
			}
		}
		
	}
	
	/**
	 * Not for one singly npc, but for all npcs in all rooms
	 * @param npc
	 */
	public void dropAllRooms(NPC npc) {
		String name = npc.getName().toLowerCase();
		
		switch (name) {
		case "Goblin":
		case "Fairy":
		case "Unicorn":
		case "Rat":
			getGame().getPlayer().addItemToInventory("UltimateWeapon", 1);
			getGame().getPlayer().addItemToInventory("RainbowApple", 5);
			getGame().printText("A weapon and some food has appeared in your inventory!");
			break;
		}
	}
	
	/**
	 * Wanneer je een item gebruikt in welke kamer dan ook, moet dit worden aangeroepen
	 * @param itemName
	 * @param amount
	 */
	public void useItemGlobal(String itemName, int amount) {
		Object itemObject = getGame().getPlayer().getItemObject(itemName);
		
		if (itemObject instanceof Misc) {
			Misc misc = (Misc) itemObject;
			switch (misc) {
			case BEAMER:
				if (getGame().getPlayer().getBeamer() == null) {
					getGame().getPlayer().setBeamer(getGame().getCurrentRoom());
					getGame().printText("You've set your new beamer location to: "+getGame().getPlayer().getBeamer().getDescription());
				} else {
					getGame().getPlayer().deleteItemAndDropInRoom(new CommandAction(CommandWordsEnum.DROPITEM, "Beamer_1"));
					getGame().setCurrentRoom(getGame().getPlayer().getBeamer());
					getGame().printText("You've used your beamer and your current location is: "+getGame().getCurrentRoom().getDescription());
					getGame().printText("The beamer was too powerful and was force dropped to the ground in your previous room");
				}
				break;
				
				
			case BLUEKEY:
			case YELLOWKEY:
			case PURPLEKEY:
			case GRAYKEY:
				if (getGame().getPlayer().playerHasItem("Bluekey", 1) && getGame().getPlayer().playerHasItem("Yellowkey", 1) &&
					getGame().getPlayer().playerHasItem("Purplekey", 1) && getGame().getPlayer().playerHasItem("Graykey", 1)) {
					getGame().getPlayer().addItemToInventory("RainbowKey", 1);
					getGame().getRoomsHandler().getRoomAction("Negen").setSolvedRoom(true);
				} else {
					getGame().printText("You don't have all the keys yet");
				}
				break;
				
			case RAINBOWKEY:
				getGame().getRoomsHandler().getRoomAction("Vijftien").setSolvedRoom(true);
				break;
				
			default:
				break;
			}
		} else if (itemObject instanceof Food) {
			Food food = (Food) itemObject;
			switch (food) {
			default:
				break;
			}
		} else if (itemObject instanceof Weapons) {
			Weapons weapon = (Weapons) itemObject;
			switch (weapon) {
			default:
				break;
			}
		}
	} 
	
	/**
	 * Of de kamer al behaald is of niet
	 */
	private boolean solvedRoom;
	
	/**
	 * De game accessor
	 */
	private Game game;
	
	/**
	 * De kamer naam
	 */
	private String roomName;
	
	/**
	 * Type raadsel van de kamer
	 */
	private RoomTypes type;
	
	/**
	 * De kamer zelf met alle directies
	 */
	private Room room;
	
	/**
	 * De npcs in de kamer
	 */
	private ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	/**
	 * De objecten in een kamer
	 */
	private ArrayList<PlayerObject> objects = new ArrayList<PlayerObject>();
	
	/**
	 * De directies van de kamer
	 */
	private RoomDirections[] directions = new RoomDirections[Global.MAX_ROOMS];
	
	/**
	 * De directies van de kamer gekoppeld met een kamer
	 */
	private Room[] toRoom = new Room[Global.MAX_ROOMS];
	
	
	/**
	 * Een object wrodt verwijderd uit een kamer
	 * @param player - de speler zelf
	 * @param itemName -  de item naam die verwijderd moet worden
	 * @param amount - de hoeveelheid die verwijderd meot worden
	 */
	public void removeItemFromRoom(Player player, String itemName, int amount) {
		if (getObjects().size() == 0) {
			getGame().printText("No items to pick up in this room anymore");
			return;
		}
		boolean foundItem = false;
		Iterator<PlayerObject> iterator = getObjects().iterator();
		
		while (iterator.hasNext()) {
			PlayerObject object = iterator.next();
			if (object != null && object.getObjectName().equalsIgnoreCase(itemName)) {
					foundItem = true;
				if (object.getObjectAmount() <= amount) {
					amount = object.getObjectAmount();
					iterator.remove();
				} else {
					object.setObjectAmount(object.getObjectAmount() - amount);
				}
				player.addItemToInventory(itemName, amount);
			}
		}
		if (!foundItem) {
			getGame().printText("Item doesn't exist in this floor, you can not pick it up");
		}
	}
	//public abstract void doAction();

	public RoomTypes getType() {
		return type;
	}

	public void setType(RoomTypes type) {
		this.type = type;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public RoomDirections[] getDirections() {
		return directions;
	}

	public void setDirections(RoomDirections[] directions) {
		this.directions = directions;
	}

	public Room getToRoom(int index) {
		return toRoom[index];
	}
	
	public void setToRoom(int index, Room value) {
		toRoom[index] = value;
	}
	
	public RoomDirections getToDirection(int index) {
		return directions[index];
	}
	
	public void setToDirection(int index, RoomDirections value) {
		directions[index] = value;
	}
	
	public Room[] getToRoom() {
		return toRoom;
	}

	public void setToRoom(Room[] toRoom) {
		this.toRoom = toRoom;
	}

	public ArrayList<NPC> getNpcs() {
		return npcs;
	}

	public void setNpcs(ArrayList<NPC> npcs) {
		this.npcs = npcs;
	}

	public ArrayList<PlayerObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<PlayerObject> objects) {
		this.objects = objects;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isSolvedRoom() {
		return solvedRoom;
	}

	public void setSolvedRoom(boolean solvedRoom) {
		this.solvedRoom = solvedRoom;
	}
	
}
