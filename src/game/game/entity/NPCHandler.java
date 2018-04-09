package game.game.entity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

import game.configs.Global;
import game.game.Game;
import game.game.combat.Weapons;
import game.room.RoomAbstractLoading;
import game.sound.Sound;

/**
 * Het handelen van de NPC acties en de lijst met de NPCs van het spel
 * @author pim
 *
 */
public class NPCHandler {

	/**
	 * Alle NPCS gestored in een arraylist
	 */
	private ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	public NPCHandler(Game game) {
		setGame(game);
	}
	
	/**
	 * Het aanvallen van een NPC
	 * @param npcName
	 * @param weaponName
	 */
	public void attackNPC(String npcName, String weaponName) {
		Optional<NPC> npcOptional = getNPC(npcName);
		
		if (npcOptional.isPresent()) {
			NPC npc = npcOptional.get();
			Player player = getGame().getPlayer();
			
			if (npc.getHealth() == 0) {
				getGame().printText("You may not attack this "+npc.getName());
				return;
			}
			Sound.sendSoundToClient("attack");
			
			int weaponDamage = Weapons.getWeaponDamage(weaponName);
			int randomWeaponDamage = (int) (Math.random() * weaponDamage);
			int npcDamage = (int) (Math.random() * 5 * npc.getLevel());
			
			if (getGame().getRoomsHandler().getRoom("Elf") == getGame().getCurrentRoom()) {
				if (!getGame().getPlayer().getGear()[Global.WEAPONINDEX].getItemName().equalsIgnoreCase("Skeletondagger")) {
					getGame().printText("You must wear a skeleton weapon to attack this boss");
					return;
				}
			}
			if (getGame().getRoomsHandler().getRoom("Zeventien") == getGame().getCurrentRoom()) {
				if (!getGame().getPlayer().playerHasItem("JohnSnowCape", 1)) {
					getGame().printText("You must wear John Snow his cape to deal any damage to John Snow");
					return;
				}
			}
			
			if (randomWeaponDamage > 0) {
				
				if (getGame().getRoomsHandler().getRoom("Achittien") == getGame().getCurrentRoom()) {
					if (Pattern.compile(Pattern.quote("Dragon"), Pattern.CASE_INSENSITIVE).matcher(npc.getName()).find() && !getGame().getPlayer().playerHasItem("AntiFireShield", 1)) {
						npcDamage = player.getHealth();
						getGame().printText("The dragon has attacked you! You've burned to death!");
					}
				}
				
				player.setHealth(player.getHealth() - npcDamage);
				npc.setHealth(npc.getHealth() - randomWeaponDamage);
				
				getGame().printText("You've dealt: "+randomWeaponDamage+" damage to the "+npc.getName());
				getGame().printText("You've gained: "+randomWeaponDamage+" experience, type `player` for more info");
				getGame().printText("The "+npc.getName()+" has dealt you damage: "+npcDamage);
				getGame().getPlayer().setExperience(getGame().getPlayer().getExperience() + randomWeaponDamage);
				if (npc.getHealth() <= 0) {
					Sound.sendSoundToClient("death");
					getGame().printText("The "+npc.getName()+" is now dead!");
					RoomAbstractLoading room = getGame().getRoomsHandler().getRoomAction(getGame().getCurrentRoom().getDescription());
					if (room != null) {
						room.dropNPCItems(npc.getName());
						room.dropAllRooms(npc);
					}
					Optional<NPC> npcKilled = getNPC(npcName);
					if (npcKilled.isPresent()) {
						getNpcs().remove(npcKilled.get());
					}
				}
				if (getGame().getPlayer().getHealth() <= 0) {
					getGame().printText("You are now dead! You've failed the game...");
					
					if (!Game.SERVER_MODE) {
						if (Game.SERVER_MODE) {
							getGame().getPlayer().setHealth(100);
						} else {
							System.exit(1);
						}
					}
				}
			} else {
				getGame().printText("You've not done enough damage to damage the monster");
			}
		} else {
			getGame().printText("The monster you're trying to find was not in this room");
		}
	}
	
	/**
	 * Een NPC wordt gezocht en wordt geretouneerd
	 * @param npcName
	 * @return
	 */
	public Optional<NPC> getNPC(String npcName) {
		return npcs.stream().filter(npc -> getGame().getCurrentRoom() == npc.getRoom().getRoom() && npc.getName().equalsIgnoreCase(npcName)).findFirst();
	}
	
	private Game game;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ArrayList<NPC> getNpcs() {
		return npcs;
	}

	public void setNpcs(ArrayList<NPC> npcs) {
		this.npcs = npcs;
	}
}
