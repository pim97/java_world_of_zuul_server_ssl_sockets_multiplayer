package game.game.entity;

import java.util.ArrayList;
import java.util.Optional;

import game.commands.CommandAction;
import game.configs.Global;
import game.food.Food;
import game.game.Game;
import game.game.combat.Misc;
import game.game.combat.Weapons;
import game.game.entity.objects.PlayerObject;
import game.game.items.Item;
import game.room.Room;
import game.room.RoomAbstractLoading;
import game.sound.Sound;

/**
 * Bevat alle acties van een speler
 * @author pim
 *
 */
public class Player {

	public Player(Game game, String name) {
		setName(name);
		setHealth(100);
		setGame(game);
		setGear(new Item[Global.ARMOURSIZE]);
	}
	/**
	 * Kan de beamer setten en dan later terug teleporteren naar een kamer
	 */
	private Room beamer;
	
	/**
	 * The experience the player currently has
	 */
	private int experience;
	
	/**
	 * De gear van de speler
	 */
	private Item[] gear;
	
	/**
	 * De levens van de speler
	 */
	private int health;
	
	/**
	 * Naam van de speler
	 */
	private String name;
	
	/**
	 * Alle items van de speler
	 */
	private ArrayList<PlayerObject> objectsInventory = new ArrayList<PlayerObject>();
	
	/**
	 * De game van de speler
	 */
	private Game game;

	/**
	 * De speler zijn levens en zijn maximale gewicht dat hij mee mag dragen
	 * Kan omhoog gaan door het eten van sepciale items.
	 */
	private int maxPlayerWeight = 100;

	/**
	 * Het maximale van een player levens, kan omhoog gaan door het eten van sepciale items.
	 */
	private int maxPlayerHealth = 100;
	
	/**
	 * Returns the players level
	 * @return
	 */
	public int getPlayerLevel() {
		return experience / 50;
	}
	
	/**
	 * Of de speler de items heeft
	 * @param itemName
	 * @param amount
	 * @return
	 */
	public boolean playerHasItem(String itemName, int amount) {
		for (PlayerObject items : getObjectsInventory()) {
			if (items.getObjectName().equalsIgnoreCase(itemName)) {
				if (items.getObjectAmount() >= amount) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Het dragen van een item
	 * @param itemName
	 * @param amount
	 */
	public void wearItem(String itemName, int amount) {
		if (playerHasItem(itemName, amount)) {
			Weapons weapon = Weapons.getWeapon(itemName);

			if (weapon != null) {
				Sound.sendSoundToClient("wear");
				Item gear = new Item(itemName, amount, weapon.getClass().getSimpleName(), weapon.getWeight());
				getGear()[Global.WEAPONINDEX] = gear;
				getGame().printText("You've equipped the weapon: "+itemName);
				getGame().printText("You will now do: "+weapon.getDamage()+" max. damage");
			}
		} else {
			getGame().printText("You don't have the required items to use this command");
		}
	}
	
	/**
	 * Het verwijderen van een item in de inventory
	 * @param itemName
	 * @param amount
	 * @return
	 */
	public boolean deleteItemFromInventory(String itemName, int amount) {
		if (playerHasItem(itemName, amount)) {
			Optional<PlayerObject> item = getInventoryObject(itemName);
			
			if (item.isPresent()) {
				Sound.sendSoundToClient("drop");
				
				if (amount > item.get().getObjectAmount()) {
					getObjectsInventory().remove(item.get());
				} else {
					item.get().setObjectAmount(item.get().getObjectAmount() - amount);
				}
				getGame().printText("The item "+itemName+" "+ amount+" x was deleted from your inventory");
				return true;
			} else {
				getGame().printText("Could not found the item");
			}
		}
		return false;
	}
	
	/**
	 * De speciale acties van het eten van een food
	 * @param food
	 */
	private void foodsActions(Food food) {
		switch (food) {
		case MAGICCOOKIE:
			maxPlayerWeight += Global.MAGIC_COOKIE_INCREASE_WEIGHT;
			getGame().printText("Your maxiumum weight was increased to: "+maxPlayerWeight);
			break;
		default:
			break;
		
		}
	}
	
	/**
	 * Het eten van een food waarmee de levens van de speler weer omhoog gaat
	 * @param command
	 */
	public void eat(CommandAction command) {
		String[] split = command.getCommandStringTwo().split("_");
		
		if (split.length == 2) {
			String itemName = split[0];
			int amount = Integer.parseInt(split[1]);
		
			if (playerHasItem(itemName, amount)) {
				Food food = Food.getFood(itemName);
				
				if (food != null) {
					Sound.sendSoundToClient("eat");
					foodsActions(food);
					int newHealth = getHealth() + (food.getRegenerate() * amount);
					if (newHealth > maxPlayerHealth) {
						setHealth(maxPlayerHealth);
					} else {
						setHealth(newHealth);
					}
					deleteItemFromInventory(itemName, amount);
					getGame().printText("You've eaten the apple, you gained "+(food.getRegenerate() * amount) +" lifepoints now have: "+getHealth()+" health (max hp is: "+maxPlayerHealth+")");
				} else {
					getGame().printText("This item isn't food");
				}
			} else {
				getGame().printText("You don't have this item");
			}
		} else {
			getGame().printText("You've typed the command incorrectly, please use `help`");
		}
	}
	
	/**
	 * De items wordt gedelete en wordt terug in de kamer gezet waarin hij zich nu bevindt
	 * @param command
	 */
	public void deleteItemAndDropInRoom(CommandAction command) {
		String[] split = command.getCommandStringTwo().split("_");
		
		if (split.length == 2) {
			String itemName = split[0];
			int amount = Integer.parseInt(split[1]);
			
			if (playerHasItem(itemName, amount)) {
				Weapons weapon = Weapons.getWeapon(itemName);
				Misc misc = Misc.getMisc(itemName);
				Food food = Food.getFood(itemName);
	
				if (weapon != null || misc != null || food != null) {
					RoomAbstractLoading room = getGame().getRoomsHandler().getRoomAction(getGame().getCurrentRoom().getDescription());
				
					if (getGear()[Global.WEAPONINDEX] != null && misc == null) {
						
						if (room != null) {
							Item gear = new Item("", 0, 0);
							getGear()[Global.WEAPONINDEX] = gear;
							room.getObjects().add(new PlayerObject(itemName, amount, getWeight(weapon, null, null)));
							getGame().printText("You've unequiped the weapon: "+itemName);
							getGame().printText("You've dropped the item in the current room");
						} else {
							getGame().printText("You may not drop this item in the room");
						}
					} else {
						if (room != null) {
							if (weapon != null || food != null || misc != null) {
								if (deleteItemFromInventory(itemName, amount)) {
									//Sound.sendSoundToClient("drop");
									room.getObjects().add(new PlayerObject(itemName, amount, getWeight(weapon, food, misc)));
									getGame().printText("You've dropped the item in the room");
								}
							}
						} else {
							getGame().printText("You may not drop this item in the room");
						}
					}
				} else {
					getGame().printText("Item could not be found");
				}
			} else {
				getGame().printText("You don't have the item or the amount required");
			}
		} else {
			getGame().printText("You've typed the command incorrectly, please use `help`");
		}
	}
	
	/**
	 * Deze item wordt van de equipment slots weggehaald
	 * @param itemName
	 * @param amount
	 */
	public void removeItemFromEquip(String itemName, int amount) {
		if (playerHasItem(itemName, amount)) {
			Weapons weapon = Weapons.getWeapon(itemName);

			if (weapon != null) {
				Item gear = new Item("", 0, 0);
				getGear()[Global.WEAPONINDEX] = gear;
				getGame().printText("You've unequiped the weapon: "+itemName);
				getGame().printText("You've now reset your weapon slot");
			}
		} else {
			getGame().printText("You don't have the required items to use this command");
		}
	}
	
	/**
	 * Het totale gewicht wordt geretourneerd van de speler met al zijn items
	 * @return
	 */
	public int getTotalWeightInInventory() {
		int weight = 0;
		
		for (PlayerObject objects : getObjectsInventory()) {
			weight += (objects.getWeight() * objects.getObjectAmount());
		}
		return weight;
	}
	
	public void excecuteLastCommand() {
		if (!getGame().getStackCommands().isEmpty()) {
			CommandAction command = getGame().getStackCommands().peek();
			
			if (command != null) {
				getGame().getCommmandHandler().processCommand(command);
			}
		} else {
			getGame().printText("No commands stored");
		}
	}
	
	public Object getItemObject(String itemName) {
		Weapons weapon = Weapons.getWeapon(itemName);
		Food food = Food.getFood(itemName);
		Misc misc = Misc.getMisc(itemName);
		
		if (weapon != null) {
			return weapon;
		} else if (food != null) {
			return food;
		} else if (misc != null) {
			return misc;
		} else {
			System.out.println("ERROR - No item found for this");
		}
		return null;
	}
	
	private int getWeight(Weapons weapon, Food food, Misc misc) {
		if (weapon != null) {
			return weapon.getWeight();
		} else if (food != null) {
			return food.getWeight();
		} else if (misc != null) {
			misc.getWeight();
		} else {
			System.out.println("ERROR - No item found for this");
		}
		return 0;
	}
	
	public void addItemToInventory(String name, int amount) {
		//getGame().printTextToConsole(this);
		Optional<PlayerObject> item = getInventoryObject(name);
		PlayerObject playerItem = null;
		Weapons weapon = Weapons.getWeapon(name);
		Food food = Food.getFood(name);
		Misc misc = Misc.getMisc(name);
		
		if (getTotalWeightInInventory() < maxPlayerWeight) {
			if (weapon != null || food != null || misc != null) {
				Sound.sendSoundToClient("pickup");
				if (item.isPresent()) {
					playerItem = item.get();
					playerItem.setObjectAmount(playerItem.getObjectAmount() + amount);
					getGame().printText("You've picked up the item: "+name+" for "+amount+" times, you now have "+playerItem.getObjectAmount());
				} else {
					getObjectsInventory().add(new PlayerObject(name, amount, getWeight(weapon, food, misc)));
					getGame().printText("You've picked up the item: "+name+" for "+amount);
				}
			} else {
				getGame().printText("You may not pick up this item, it was not defined");
			}
		} else {
			getGame().printText("You may not carry this, you've reached the maximum of weight, currently at: "+getTotalWeightInInventory()+" / "+maxPlayerWeight);
		}
		
	}
	
	public Optional<PlayerObject> getInventoryObject(String name) {
		return objectsInventory.stream().filter(object -> object.getObjectName().equalsIgnoreCase(name)).findFirst();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<PlayerObject> getObjectsInventory() {
		return objectsInventory;
	}

	public void setObjectsInventory(ArrayList<PlayerObject> objectsInventory) {
		this.objectsInventory = objectsInventory;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Item[] getGear() {
		return gear;
	}

	public void setGear(Item[] gear) {
		this.gear = gear;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Room getBeamer() {
		return beamer;
	}

	public void setBeamer(Room beamer) {
		this.beamer = beamer;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getMaxPlayerWeight() {
		return maxPlayerWeight;
	}

	public void setMaxPlayerWeight(int maxPlayerWeight) {
		this.maxPlayerWeight = maxPlayerWeight;
	}

	public int getMaxPlayerHealth() {
		return maxPlayerHealth;
	}

	public void setMaxPlayerHealth(int maxPlayerHealth) {
		this.maxPlayerHealth = maxPlayerHealth;
	}


}
