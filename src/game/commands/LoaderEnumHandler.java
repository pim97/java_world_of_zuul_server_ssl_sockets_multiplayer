package game.commands;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael K�lling and David J. Barnes
 * @version 2016.02.29
 */

public class LoaderEnumHandler
{
    // A mapping between a command word and the CommandWord
    // associated with it.
    private HashMap<String, CommandWordsEnum> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public LoaderEnumHandler()
    {
        validCommands = new HashMap<>();
        for(CommandWordsEnum command : CommandWordsEnum.values()) {
            if(command != CommandWordsEnum.UNKNOWN) {
                validCommands.put(command.name().toString().toLowerCase(), command);
            }
        }
    }

    /**
     * Find the CommandWord associated with a command word.
     * @param commandWord The word to look up.
     * @return The CommandWord correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWordsEnum getCommandWord(String commandWord)
    {
        CommandWordsEnum command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWordsEnum.UNKNOWN;
        }
    }
    
    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out.
     */
    public StringBuilder getCommandList() 
    {
    	StringBuilder commands = new StringBuilder();
    	commands.append("\n == All the commands you may use == \n");
    	for (Entry<String, CommandWordsEnum> entry  : validCommands.entrySet()) {
    		commands.append(entry.getKey().toUpperCase()+" : "+entry.getValue().getInstruction()+"\n");
    	    //Game.printTextToConsole(entry.getKey() + " - " + entry.getValue());
    	}
		return commands;
    }
    
    
}
