package game.sound;

import game.game.Game;

/**
 * @author Pim de Bree
 */
public class Sound {

	/**
	 * Verzendt een stukje data naar de client die het stukje muziek gaat afspelen
	 * @param text - het stukje data dat wordt verzonden
	 */
	public static void sendSoundToClient(String text) {
		if (Game.SERVER_MODE) {
			Game.getInstance().printText("::SOUND "+text);
		}
	}
	
	/**
	 * Het zelfde als sendSoundToClient, maar nu blijft deze in een lijst met repeaten
	 * @param text - stukje dat wordt verzonden
	 */
	public static void sendRepeatSoundToClient(String text) {
		if (Game.SERVER_MODE) {
			Game.getInstance().printText("::BG "+text);
		}
	}
}
