package game.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import game.game.Game;
import game.game.entity.Player;
import game.room.Room;
import game.room.RoomAbstractLoading;
import game.room.RoomDirections;
import game.sound.Sound;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class ProcessCommand {
	
	/**
	 * Alle ingeladen commands van de game.commands.all package
	 */
	public static ArrayList<HandleAbstractCommands> commands = new ArrayList<HandleAbstractCommands>();
	
	public ProcessCommand(Game game) {
		setGame(game);
	}
	
	private Game game;
	
	/**
	 * Returned een class HandleCommands met de input van de gebruiker
	 * @param command
	 * @return
	 */
	public HandleAbstractCommands getCommand(CommandAction command) {
		for (HandleAbstractCommands com : getCommands()) {
			if (com.getClass().getSimpleName().equalsIgnoreCase(command.getCommandWord().name())) {
				return com;
			}
		}
		return null;
	}

	/**
	 * De command worden hier behandeld en worden daarna verder verwerkt in allemaal methodes
	 * @param command
	 * @return
	 */
	public boolean processCommand(CommandAction command) {
		
		/**
		 * De command ophalen
		 */
        CommandWordsEnum commandWord = command.getCommandWord();
        
        if (commandWord == null) {
        	commandWord = CommandWordsEnum.UNKNOWN;
        }
        /**
         * De laatste commands onthouden in een stack
         */
		if (getGame().getStackCommands().size() > 10) {
			getGame().getStackCommands().pop();
		}
		if (commandWord != CommandWordsEnum.PREV) {
			getGame().getStackCommands().push(command);
		}
		
		/**
		 * De command uitvoeren mbv abstracte klassen
		 */
		HandleAbstractCommands handleCommand = getCommand(command);
		if (handleCommand != null) {
			handleCommand.handleCommand(command);
		} else {
			getGame().printText("Command was invalid");
		}
		return false;
    }
	
	/**
	 * Showt informatie over de speler
	 */
	public void showPlayerInformation() {
		Player player = getGame().getPlayer();
		
		getGame().printText("\n== Information about "+player.getName()+" ==\n"
				+ "Health: "+player.getHealth()+"\nCurrently weight:"+player.getTotalWeightInInventory()+"\nMax carry weight:"+getGame().getPlayer().getMaxPlayerWeight()+""
						+ "\nMax health: "+getGame().getPlayer().getMaxPlayerHealth()+"\nExperience: "+getGame().getPlayer().getExperience()+" Level: "+
				getGame().getPlayer().getPlayerLevel());
		
	}
	
	/**
	 * Gaat een room back, maar kan ook meerdere rooms terug
	 */
	public void roomBack() {
		if (!getGame().getStack().isEmpty()) {
			getGame().setCurrentRoom(getGame().getStack().pop());
			getGame().printText("You've walked back to your last room, type `room` for the room's information");
		} else {
			getGame().printText("You don't have a previous room");
		}
	}
	
	/**
	 * Print alle items naar de console of de client
	 */
	public void showAllItems() {
		if (getGame().getPlayer().getObjectsInventory().size() == 0) {
			getGame().printText("You have no items stored in your inventory");
			return;
		}
		StringBuilder items = new StringBuilder();
		items.append("== Inventory Items == \n\nYou currently have the following items in your inventory: \n");
		getGame().getPlayer().getObjectsInventory().stream().filter(Objects::nonNull).forEach(item -> {
			items.append(item.getObjectName()+"_"+item.getObjectAmount()+" times\n");
		});
		items.append("\n\n== Items equipped == \n");
		Arrays.asList(getGame().getPlayer().getGear()).stream().filter(Objects::nonNull).forEach(item -> {
			items.append(item.getItemName()+"_"+item.getItemAmount()+" Slot: "+item.getItemSlot()+"\n");
		});
		
		getGame().printText(items.toString());
	}
	
	/**
	 * Hiermee kan je een item usen en wordt uiteindelijk weer gebruikt in een specifieke room met specifieke acties
	 * @param command
	 */
	public void useItem(CommandAction command) {
		 RoomAbstractLoading room = getRoom();
		 String itemName = command.getCommandStringTwo();
		 
		 String[] itemSplit = itemName.split("_");
		 if (itemSplit.length == 2 && room != null && itemName != null && !itemName.isEmpty()) {
			 if (getGame().getPlayer().playerHasItem(itemSplit[0], Integer.parseInt(itemSplit[1]))) {
				 room.useItem(itemSplit[0], Integer.parseInt(itemSplit[1]));
				 room.useItemGlobal(itemSplit[0], Integer.parseInt(itemSplit[1]));
			 } else {
				 getGame().printText("You don't have this item");
			 }
		 } else {
			 getGame().printText("You probably don't have this item or have mistyped it");
		 }
	}
	
	/**
	 * De items oppakken uit kamers
	 * @param command
	 */
	public void pickupItem(CommandAction command) {
		 RoomAbstractLoading room = getRoom();
		 
		 if (room != null) {
			 String[] split = command.getCommandStringTwo().split("_");
			 
			 if (split.length == 2) {
				 String itemName = split[0];
				 int itemAmount = Integer.parseInt(split[1]);
				 
				 room.removeItemFromRoom(getGame().getPlayer(), itemName, itemAmount);
			 } else {
				 getGame().printText("You must include an underscore [_] in your item");
			 }
		 }
	}
	
	/**
	 * Retourneert de roomAction waarin je op het moment zit
	 * @return
	 */
	private RoomAbstractLoading getRoom() {
		for (RoomAbstractLoading room : getGame().getRoomsHandler().getGameRooms()) {
			if (room.getRoomName().equalsIgnoreCase(getGame().getCurrentRoom().getDescription())) {
				return room;
			}
		}
		return null;
	}
	
	/**
	 * Hierdoor komt de text de staan om met een npc te praten waardoor dialogue ook terug komt
	 * @param command
	 * @return
	 */
	public String talkToDialogue(CommandAction command) {
		 RoomAbstractLoading room = getRoom();
				 
		 if (room != null) {
			 String com = command.getCommandStringTwo();
			 if (com.isEmpty()) {
				 getGame().printText("You haven't typed the npc correctly use: talk [name_here], including the underscore");
				 return null;
			 }
			
			 StringBuilder dialogue = new StringBuilder();
			 getGame().getDialogueHandler().getDialogue().forEach(npc -> {
				 
     			if (npc.getRoom() == room && command.getCommandStringTwo().equalsIgnoreCase(npc.getNpcName())) {
     				
     				npc.getDialogue().forEach(dia -> {
     					dialogue.append("\n\n== "+dia.getOption().name()+" "+ npc.getNpcName() +" says == ");
     					Arrays.asList(dia.getDialogue()).forEach(d -> {
     						dialogue.append("\n"+d);
     					});
     				});
     			}
     		});
			 return dialogue.toString();
		 }
		 return null;
	}
	
	/**
	 * Displayed alle informatie van een kamer wanneer iemand 'look' of 'room' intyped
	 * @param command
	 * @return
	 */
	public String printRoomInformation(CommandAction command) {
		StringBuilder text = new StringBuilder();
		
	    RoomAbstractLoading room = getRoom();

        if (room != null) {
        	if (command.getCommandStringTwo() == "") {
        		text.append(room.getRoom().getLongDescription());
        		text.append("\n\nRoom id: "+room.roomId()+"");

                text.append("\n\nNPC's in this room: ");
                room.getNpcs().stream().filter(Objects::nonNull).forEach(npc -> {
                	text.append("\n"+npc.getName()+" ");
                });
                
                text.append("\n\nItems to pick up in this room: ");
                if (room.getObjects().size() == 0) {
                	text.append("\nNone");
                }
                room.getObjects().stream().filter(Objects::nonNull).forEach(object -> {
                	text.append("\n"+object.getObjectName()+"_"+object.getObjectAmount()+" times");
                });
                
                
                text.append("\n\nAction here: "+room.getType());
                return text.toString();
        	}
	        for (int i = 0; i < room.getDirections().length; i++) {
	        	RoomDirections dir = room.getToDirection(i);
	        	
	        	if (dir != null && dir.name().equalsIgnoreCase(command.getCommandStringTwo())) {
	        		Room nextRoom = room.getToRoom(i);
	        		getGame().getStack().push(getGame().getCurrentRoom());
	        		//nex troom
	        		game.setCurrentRoom(nextRoom);
	        		text.append(nextRoom.getLongDescription());

	                text.append("\n\nNPC's in this room: ");

	                getGame().getRoomsHandler().getGameRooms().stream().filter(Objects::nonNull).forEach(rooms -> {
	                	if (rooms.getRoomName().equalsIgnoreCase(nextRoom.getDescription())) {

	    	        		text.append("\n\noom id: "+rooms.roomId()+"\n");
	    	        		
	                		rooms.getNpcs().stream().filter(Objects::nonNull).forEach(npc -> {
		                		text.append("\n NPC: "+npc.getName());
	                		});
	                		rooms.getObjects().stream().filter(Objects::nonNull).forEach(object -> {
		                		text.append("\n Item: "+object.getObjectName()+"_"+object.getObjectAmount()+" times");
	                		});
	                	}
	                	
	                	
	                });
	                
	                text.append("\n\nAction here: "+room.getType());
	        	} 
	        	
	        }
        } 
        return text.toString();
	}
	
	/**
	 * Gaat naar de kamer in de command
	 * @param command
	 */
	public void goRoom(CommandAction command) {
        if(!command.hasSecondWord()) {
            getGame().printText("Please retry with: go [direction here]");
            return;
        }
        RoomAbstractLoading room = getRoom();
        
        if (!room.continueRequirement()) {
        	getGame().printText("You must solve the "+room.getType().name().toLowerCase()+" first to continue");
        	return;
        }
        String information = printRoomInformation(command);
        
        if (room != null) {
        	Sound.sendSoundToClient("door");
        	getGame().printText("::CURRENTLOCATION "+room.getRoomName());
	        getGame().printText(information);
        }
        if (information == null || information.isEmpty()) {
        	getGame().printText("No door at this location, please try again");
        }
    }
	
	/**
	 * Gaat naar de kamer met een string (kamer naam) en de directie van de kamer
	 * @param roomName
	 * @param direction
	 */
	public void goRoom(String roomName, RoomDirections direction) {
		CommandAction command = new CommandAction(CommandWordsEnum.GO, direction.name().toLowerCase());
        String information = printRoomInformation(command);
        RoomAbstractLoading room = getGame().getRoomsHandler().getRoomAction(roomName);
        
        if (room != null) {
        	Sound.sendSoundToClient("door");
        	getGame().printText("::CURRENTLOCATION "+room.getRoomName());
	        getGame().printText(information);
        } else {
        	getGame().printText("There's no room, something bad happened");
        }
    }

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public static ArrayList<HandleAbstractCommands> getCommands() {
		return commands;
	}

	public static void setCommands(ArrayList<HandleAbstractCommands> commands) {
		ProcessCommand.commands = commands;
	}
	
}
