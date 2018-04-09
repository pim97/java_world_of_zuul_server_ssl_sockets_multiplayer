package game.commands;

import game.game.Game;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class CommandCommands {

	/**
	 * Retouneert of de command een command is of niet
	 * @param commandString
	 * @return
	 */
	public boolean isCommand(String commandString) {
		for (CommandWordsEnum command : CommandWordsEnum.values()) {
			String enumCommand = command.name();
			
			if (enumCommand.equalsIgnoreCase(commandString)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Print alle commands naar de console
	 */
	public void showAll() {
		Game.getInstance().printText("Available commands: \n");
		for (CommandWordsEnum command : CommandWordsEnum.values()) {
			String enumCommand = command.name();
			String instruction = command.getInstruction();
			
			Game.getInstance().printText(enumCommand+" - "+instruction);
		}
	}
}
