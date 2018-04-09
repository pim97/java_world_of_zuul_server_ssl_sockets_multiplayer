package game.commands.input;

import java.util.Scanner;

import game.commands.CommandAction;
import game.commands.CommandWordsEnum;
import game.commands.LoaderEnumHandler;
import game.game.Game;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class Parser {

	private LoaderEnumHandler commands;
	private Scanner reader;
	
	public Parser() {
		setReader(new Scanner(System.in));
		setCommands(new LoaderEnumHandler());
	}
	
	/**
	 * Returned een een nieuwe, deze wordt gemaakt met een CommandWordt en een string
	 * @return
	 */
	public CommandAction getCommand() {
		String command = null;
		
		if (getReader().hasNext()) {
			command = getReader().nextLine();
		}
		String[] commandSplitted = command.split(" ");
		CommandWordsEnum commandWord = CommandWordsEnum.getCommand(commandSplitted[0]);
		if (commandWord != null) {
			if (commandSplitted.length > 1) {
				return new CommandAction(commandWord, commandSplitted[1]);
			} else {
				return new CommandAction(commandWord, "");
			}
		}
		return null;
	}
	
	/**
	 * Returned een een nieuwe, deze wordt gemaakt met een CommandWordt en een string
	 * @return
	 */
	public CommandAction getCommand(String data) {
		String[] commandSplitted = data.split(" ");
		
		if (commandSplitted.length > 0) {
			CommandWordsEnum commandWord = CommandWordsEnum.getCommand(commandSplitted[0]);
			
			if (commandWord != null) {
				if (commandSplitted.length > 1) {
					return new CommandAction(commandWord, commandSplitted[1]);
				} else {
					return new CommandAction(commandWord, "");
				}
			}
		}
		return null;
	}

	/**
	 * Showt alle commands
	 */
	public void getCommandList() {
		Game.getInstance().printText(getCommands().getCommandList().toString());
	}
	
	public Scanner getReader() {
		return reader;
	}

	public void setReader(Scanner reader) {
		this.reader = reader;
	}

	public LoaderEnumHandler getCommands() {
		return commands;
	}

	public void setCommands(LoaderEnumHandler commands) {
		this.commands = commands;
	}

}
