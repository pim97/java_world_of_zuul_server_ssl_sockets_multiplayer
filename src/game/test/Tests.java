package game.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;

import game.commands.CommandAction;
import game.commands.CommandWordsEnum;
import game.commands.HandleAbstractCommands;
import game.commands.all.Back;
import game.food.Food;
import game.game.Game;
import game.game.combat.Weapons;
import game.game.entity.NPC;
import game.game.entity.Player;
import game.game.entity.objects.PlayerObject;
import game.room.Room;

public class Tests {

	private static final Game game = new Game();
	
	@Test
	public void testOneAppleShouldEquealOneApple() {
		PlayerObject object = new PlayerObject("Apple", 1);
		assertEquals(1, object.getObjectAmount());
	}
	
	@Test
	public void testAppleNameShouldEqualAppleName() {
		PlayerObject object = new PlayerObject("Apple", 1);
		assertEquals("Apple", object.getObjectName());
	}
	
	/**
	 * Is de room gevonden in de lijst?
	 */
	@Test
	public void testRoomNameShouldEqualRoomName() {
		Room room = new Room("Een");
		String roomName = room.getDescription();
		Room roomFound = game.getRoomsHandler().getRoom(room.getDescription());
		
		assertEquals(roomName, roomFound.getDescription());
	}

	/**
	 * Voegt en delete hij correct items toe aan de inventory?
	 */
	@Test
	public void testAdd1And3ToInventoryAndRemoveOneShouldEqueal3() {
		game.setPlayer(new Player(game, "Player"));
		Food food = Food.APPLE;
		
		game.getPlayer().addItemToInventory(Food.APPLE.name(), 1);
		game.getPlayer().addItemToInventory(Food.APPLE.name(), 3);
		game.getPlayer().deleteItemFromInventory(Food.APPLE.name(), 1);
		game.getPlayer().getObjectsInventory().forEach(item -> {
			if (item.getObjectName().equalsIgnoreCase(food.name())) {
				assertEquals(3, item.getObjectAmount());
			}
		});
		
	}
	
	/**
	 * Het testen of de command `back` in de lijst is verwerkt en dus ook doet
	 */
	@Test
	public void testIfCommandIsNotNull() {
		HandleAbstractCommands back = new Back(game);
		String className = back.getClass().getSimpleName();
		CommandAction command = new CommandAction(CommandWordsEnum.getCommand(className), "");
		HandleAbstractCommands found = game.getCommmandHandler().getCommand(command);
		assertNotNull(found);
	}
	
	/**
	 * Het testen of de command `back` in de lijst is verwerkt en dus ook doet
	 */
	@Test
	public void testIfNPCIsNotNullInRoom() {
		NPC npc = new NPC(null, "Prison_Guard");
		String npcName = npc.getName();
		game.setCurrentRoom(game.getRoomsHandler().getRoom("Een"));
		Optional<NPC> found = game.getNPCHandler().getNPC(npcName);
		assertNotNull(found.get());
	}
	
	@Test
	public void testIfWeaponIsNotNull() {
		Weapons weapon = Weapons.getWeapon("Dagger");
		assertNotNull(weapon);
	}

}
