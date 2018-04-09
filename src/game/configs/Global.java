package game.configs;

import java.util.Calendar;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class Global {
	
	/**
	 * Keystore file name
	 */
	public static final String KEYSTORE_FILE_NAME = "";
	
	/**
	 * Keystore password for SSL
	 */
	public static final String KEYSTORE_PASSWORD = "";
	/**
	 * De kamer waar de speler in start
	 */
	public static final String STARTING_ROOM = "Een";
	
	/**
	 * De naam van de game
	 */
	public static final String GAME_NAME = "Game of Thrones Game";
	
	/**
	 * De port van de game wanneer de server enabled is
	 */
	public static final int GAME_PORT = 9001;

	/**
	 * De grootte van het frame (waardes)
	 */
	public static final int FRAME_SIZE_X = 1515;
	public static final int FRAME_SIZE_Y = 450;
	
	/**
	 * Hoeveel tijd de speler over heeft voordat de game over is
	 */
	public static final int TIME_UNIT = Calendar.MINUTE;
	public static final int TIME_LEFT_OF_UNIT = 30;
	
	/**
	 * De maximale kamers in het spel
	 */
	public static final int MAX_ROOMS = 100;
	
	/**
	 * De versie ID
	 */
	public static final double VERSION_ID = 1.1;
	
	/**
	 * Hoeveel de maximale weight van een speler omhoog gaat wanneer de speler de magic cookie eet
	 */
	public static final int MAGIC_COOKIE_INCREASE_WEIGHT = 10;

	/**
	 * De lengte van de armour variables
	 */
	public static final int ARMOURSIZE = 8;
	
	/**
	 * Indexen voor alle equipment slots
	 */
	public static final int CAPEINDEX = 7;
	public static final int WEAPONINDEX = 0;
	public static final int HELMETINDEX = 1;
	public static final int AMULETINDEX = 2;
	public static final int BODYINDEX = 3;
	public static final int LEGSINDEX = 4;
	public static final int GLOVESINDEX = 5;
	public static final int SHIELDINDEX = 6;
}
