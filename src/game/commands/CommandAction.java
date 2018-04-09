package game.commands;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class CommandAction {

	/**
	 * Het commandword van de command
	 */
    private CommandWordsEnum commandWord;
    
    /**
     * De string van de command waarin de informatie bevat van de command
     */
	private String commandStringTwo;

	public CommandAction(CommandWordsEnum commandWord, String commandStringTwo) {
		setCommandWord(commandWord);
		setCommandStringTwo(commandStringTwo);
	}

	public String getCommandStringTwo() {
		return commandStringTwo;
	}

	public void setCommandStringTwo(String commandStringTwo) {
		this.commandStringTwo = commandStringTwo;
	}
	
	public CommandWordsEnum getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return true if this command was not understood.
     */
	public boolean isUnknown()
    {
        return (commandWord == CommandWordsEnum.UNKNOWN);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (getCommandStringTwo() != null);
    }

	public void setCommandWord(CommandWordsEnum commandWord) {
		this.commandWord = commandWord;
	}
}
