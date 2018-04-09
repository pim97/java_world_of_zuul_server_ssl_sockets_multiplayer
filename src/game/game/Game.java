package game.game;

import java.io.IOException;
import java.util.Calendar;
import java.util.Stack;

import game.commands.CommandAction;
import game.commands.CommandWordsEnum;
import game.commands.ProcessCommand;
import game.commands.input.Parser;
import game.configs.Global;
import game.dialogue.DialogueHandler;
import game.frame.Frame;
import game.game.entity.NPCHandler;
import game.game.entity.Player;
import game.game.items.ItemHandler;
import game.room.Room;
import game.room.RoomAbstractLoading;
import game.room.RoomsHandler;
import game.room.load.LoadClasses;
import game.server.Server;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class Game {
	
	/**
	 * Returned de game instance, hiermee kan je vanuit alle classen de game
	 * accessen
	 * @return Game
	 */
	public static Game getInstance() {
		return game;
	}
	
	private static Game game;
	
	/**
	 * Wanneer hij op debug mode staat dan zullen er extra info worden geoutput in de console
	 */
	public static final boolean DEBUG_MODE = false;
	
	/**
	 * Wanneer hij op gui mode staat dan zal de GUI worden opgezet
	 */
	public static final boolean GUI_MODE = false;
	
	/**
	 * Wanneer hij op server mode staat dan zal alleen de server worden opgezet worden
	 */
	public static final boolean SERVER_MODE = true;
	
	/**
	 * De GUI van het spel wanneer de server niet actief is
	 */
	private Frame frameHandler;
	
	/**
	 * De commands worden geladen van het spel
	 */
	private ProcessCommand commmandHandler;
	
	/**
	 * De kamers worden geladen van het spel
	 */
	private RoomsHandler roomsHandler;
	
	/**
	 * De dialogues worden gehalden van het spel
	 */
	private DialogueHandler dialogueHandler;
	
	/**
	 * De kamers worden hierin geladen van het spel
	 */
	private LoadClasses loadRoom;
	
	/**
	 * De npcs worden hierin geladen van het spel
	 */
	private NPCHandler npc;
	
	/**
	 * De items worden hierin geladen van het spel
	 */
	private ItemHandler itemHandler;
	
	/**
	 * Wanneer de server aan staat met de variabele dan zal dit worden geset
	 */
	private Server server;
	
	/**
	 * De speler zelf die het spel door gaat
	 */
	private Player player;
	
	/**
	 * Hoe de input wordt gehandeld
	 */
	private Parser parser;
	
	/**
	 * De huidige kamer
	 */
	private Room currentRoom;
	
	/**
	 * Een object Stack met daarin Rooms, deze vertellen tot hoeveel het persoon terug kan gaan
	 * @Opdracht 8.23 en 8.26
	 */
	private Stack<Room> stackRooms;
	
	/**
	 * Een extra functie waarmee je terug kunt gaan in commands, zodat je niet alles
	 * weer opnieuw hoeft te typen
	 */
	private Stack<CommandAction> stackCommands;
	
	/**
	 * Hoeveel tijd het persoon over heeft totdat het spel zichzelf afsluit
	 * @Opdracht 8.41
	 */
	private Calendar timeLimit = Calendar.getInstance();
	private Calendar currentTime = Calendar.getInstance();
	
	public Game() {
		
		/**
		 * Allemaal setters die de variabelen setten
		 */
		setGame(this);
		
		setServer(new Server(this));
		
		/**
		 * Wanneer deze op true staat dan wordt de server aangezet
		 */
		if (SERVER_MODE) {
			try {
				getServer().initializeServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
				
		//Loading all information
		setFrameHandler(new Frame(this));
		setNPCHandler(new NPCHandler(this));
		setRoomsHandler(new RoomsHandler(this));
		setCommmandHandler(new ProcessCommand(this));
		setDialogueHandler(new DialogueHandler(this));
		setLoadRoom(new LoadClasses(this));
		setParser(new Parser());
		setItemHandler(new ItemHandler(this));
		setStack(new Stack<Room>());
		setStackCommands(new Stack<CommandAction>());
		
		//Loading all rooms and automatically initializing the rooms with reflection
		getLoadRoom().loadRooms();
		
		//Loads all the commands
		getLoadRoom().loadCommands();
		
		/**
		 * Wanneer de guide mode aan staat dan zet hij deze acties aan
		 */
		if (GUI_MODE) {
			getFrameHandler().initializeFrame(this);
			getFrameHandler().appendTextToAreaHelp(getParser().getCommands().getCommandList().toString());
		}
		setCurrentTime(Calendar.getInstance());
    	getCurrentTime().add(Global.TIME_UNIT, Global.TIME_LEFT_OF_UNIT);
	}
	
	/**
	 * De game wordt nu geexit
	 */
	private void exitGame() {
		System.exit(1);
	}
	
	/**
	 * Returned in een string hoe veel minuten er nog over zijn in het spel
	 * @param limitedTime
	 * @return
	 */
	public String minutesLeft(Calendar limitedTime) {
		setTimeLimit(Calendar.getInstance());
    	if (limitedTime.getTime().before(getTimeLimit().getTime())) {
    		printText("== You've not made it in time, you could try again? == ");
    		exitGame();
    	} else {
    		long diff = limitedTime.getTime().getTime() - getTimeLimit().getTime().getTime();
    		int minutesLeft = (int) (diff / 60000);
    		return "Time left to finish the game: "+minutesLeft+" minutes"+" (milliseconds: "+diff+")";
    	}
    	return "Not found";
	}
	
	/**
	 * Print de text op verschillende manieren afhaneklijk van elke mode aan staat
	 * @param text
	 */
	public void printText(String text) {
		if (GUI_MODE) {
			/**
			 * Hij zal alles naar de GUI versturen
			 */
			getFrameHandler().appendTextToArea(text);
		} else if (SERVER_MODE) {
			/**
			 * Hij zal alle data naar de clients sturen
			 */
			getServer().sendStringToClients(text);
		} else {
			/**
			 * Print de text naar de console als laatste optie
			 */
			System.out.println(text);
		}
	}
	
	/**
	 * Gaat nu het spel aanmaken en wacht telkens op inputs
	 */
	public void play() {
    	printText(minutesLeft(getCurrentTime()));
		printWelcomeText();
		
		if (DEBUG_MODE) {
			setPlayer(new Player(this, "test"));
		}
		boolean finished = false;
        while (!finished) {
    		
	        CommandAction command = parser.getCommand();
	        
	        if (command != null) {
	        	 CommandWordsEnum type = command.getCommandWord();
	 			if (player == null && type != CommandWordsEnum.SETNAME) {
	 	    		printText("\nPlease type in your name first with the command: setname [name here]");
	 	        } else {
	 	            finished = getCommmandHandler().processCommand(command);
	 	        }
	        } else {
	        	printText("The command could not be found, please use `help` and try again");
	        }
	       
        }
        printText("Thank you for playing " + Global.GAME_NAME + " See you next time!");
	}
	

	/**
	 * Zorgt ervoor dat de speler informatie krijgt over het spel in het begin van de game
	 */
    private void printWelcomeText()
    {
    	StringBuilder string = new StringBuilder();
    	string.append("\n Welcome to the " + Global.GAME_NAME
    			+ "\n Type 'help' if you need help. \n \n == Current room information ==");
    	this.setCurrentRoom(this.getRoomsHandler().getRoom(Global.STARTING_ROOM));
    	
    	if (GUI_MODE) {
    		getFrameHandler().alert("Welcome there "+getPlayer().getName()+"!\nYou've entered the world of warriors\n\nUnfurtunately you've been captured by a dark demon called Cesar\n\nYou must escape");
    	}
    	printText(string.toString());
    	printText(this.getCommmandHandler().printRoomInformation(new CommandAction(CommandWordsEnum.ROOM, "")));
    }
	
	public Parser getParser() {
		return parser;
	}
	
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	/**
	 * Set de current room met de functie waardoor npcs zullen laden naar verschillende kamers elke keer
	 * de speler naar een andere kamer gaat
	 * @param currentRoom
	 */
	public void setCurrentRoom(Room currentRoom) {
		if (getCurrentRoom() != null) {
			this.currentRoom = currentRoom;
			RoomAbstractLoading room = getRoomsHandler().getRoomAction(getCurrentRoom().getDescription());
			if (room != null) {
				room.actionOnLoadRoom();
				room.moveNPCSToOtherRoom();
			}
		} else {
			this.currentRoom = currentRoom;
		}
	}

	public ProcessCommand getCommmandHandler() {
		return commmandHandler;
	}

	public void setCommmandHandler(ProcessCommand commmandHandler) {
		this.commmandHandler = commmandHandler;
	}

	public RoomsHandler getRoomsHandler() {
		return roomsHandler;
	}

	public void setRoomsHandler(RoomsHandler roomsHandler) {
		this.roomsHandler = roomsHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public DialogueHandler getDialogueHandler() {
		return dialogueHandler;
	}

	public void setDialogueHandler(DialogueHandler dialogueHandler) {
		this.dialogueHandler = dialogueHandler;
	}

	public LoadClasses getLoadRoom() {
		return loadRoom;
	}

	public void setLoadRoom(LoadClasses loadRoom) {
		this.loadRoom = loadRoom;
	}

	public NPCHandler getNPCHandler() {
		return npc;
	}

	public void setNPCHandler(NPCHandler npc) {
		this.npc = npc;
	}

	public ItemHandler getItemHandler() {
		return itemHandler;
	}

	public void setItemHandler(ItemHandler itemHandler) {
		this.itemHandler = itemHandler;
	}


	public Stack<Room> getStack() {
		return stackRooms;
	}

	public void setStack(Stack<Room> stack) {
		this.stackRooms = stack;
	}

	public Stack<CommandAction> getStackCommands() {
		return stackCommands;
	}

	public void setStackCommands(Stack<CommandAction> stack) {
		this.stackCommands = stack;
	}

	public Calendar getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Calendar timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Calendar getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Calendar currentTime) {
		this.currentTime = currentTime;
	}

	public Frame getFrameHandler() {
		return frameHandler;
	}

	public void setFrameHandler(Frame frameHandler) {
		this.frameHandler = frameHandler;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Game.game = game;
	}



	
}
