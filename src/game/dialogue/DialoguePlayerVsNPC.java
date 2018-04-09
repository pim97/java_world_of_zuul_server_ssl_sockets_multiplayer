package game.dialogue;

/**
 * Dit bevat de dialogueOption en de dialogue zelf
 * @author pim
 *
 */
public class DialoguePlayerVsNPC {

	private String[] dialogue;
	private DialogueOption option;
	
	/**
	 * Of een dialogue juist de speler is of de npc
	 * @param option
	 * @param dialogue
	 */
	public DialoguePlayerVsNPC(DialogueOption option, String... dialogue) {
		setDialogue(dialogue);
		setOption(option);
	}

	public String[] getDialogue() {
		return dialogue;
	}

	public void setDialogue(String[] dialogue2) {
		this.dialogue = dialogue2;
	}

	public DialogueOption getOption() {
		return option;
	}

	public void setOption(DialogueOption option) {
		this.option = option;
	}
}
