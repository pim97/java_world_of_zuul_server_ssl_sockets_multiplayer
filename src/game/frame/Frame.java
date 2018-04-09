package game.frame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Utilities;

import game.commands.CommandAction;
import game.commands.CommandWordsEnum;
import game.configs.Global;
import game.game.Game;
import game.game.entity.Player;

/**
 * De GUI van het spel
 * @author pim
 *
 */
public class Frame {
	
	/**
	 * Consturctor van Frame
	 * @param game
	 */
	public Frame(Game game) {
		setGame(game);
	}
    
	private Game game;
	
	/**
	 * Alle modules die worden gekoppeld aan een JFrame
	 */
	private JPanel movePanel = new JPanel();
	private JFrame mainFrame = new JFrame(Global.GAME_NAME);
	private MoveFrame move = new MoveFrame();
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollpane = new JScrollPane(getTextArea());
	private JTextArea textArea2 = new JTextArea();
	private JScrollPane scrollpane2 = new JScrollPane(getTextArea2());
	private JTextArea textArea3 = new JTextArea();
	private JScrollPane scrollpane3 = new JScrollPane(getTextArea3());
	
	public void alert(String text) {
		JOptionPane.showMessageDialog(getMainFrame(), text);
	}
	
	/**
	 * Nieuwe text in een text area
	 * @param text
	 */
	public void appendTextToArea(String text) {
		getTextArea().append("\n"+text);
	}
	
	/**
	 * Nieuwe text aan in de help area
	 * @param text
	 */
	public void appendTextToAreaHelp(String text) {
		getTextArea3().append("\n"+text);
	}
	
	/**
	 * Checked of er iets nieuws wordt gedrukt in de commands section
	 */
	public void textAreaListener() {
		getTextArea2().addKeyListener(new KeyListener() {
	
		    @Override
		    public void keyTyped(KeyEvent arg0) {
	
		    }
	
		    @Override
		    public void keyReleased(KeyEvent arg0) {
		        // TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void keyPressed(KeyEvent arg0) {
		    	 
		    	if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			    	try {
			    		 
			    	int end = getTextArea2().getDocument().getLength();
			    	int start = Utilities.getRowStart(getTextArea2(), end);
	
			    	while (start == end)
			    	{
			    	    end--;
			    	    start = Utilities.getRowStart(getTextArea2(), end);
			    	}
	
			    	String text = getTextArea2().getText(start, end - start);
			    	CommandAction command = getCommand(text);
			    	
			    	if (getGame().getPlayer() == null && !text.contains("setname")) {
			    		getGame().getFrameHandler().appendTextToArea("\nPlease type in your name first with the command: setname [name here]");
			    	} else if (command != null) {
				    	getGame().getCommmandHandler().processCommand(command);
			    	} else if (command == null) {
			    		getGame().getFrameHandler().appendTextToArea("\nThe command you tried to enter didnt work\n please use `help` to get more information about the commands");
			    	}
			    	
			    	} catch (BadLocationException exp) {
			    		exp.printStackTrace();
			    	}
		    	}
		    }
		});
	}
	
	/**
	 * De speler zijn naam in een GUI optie
	 * @return
	 */
	private String getPlayerName() {
        return JOptionPane.showInputDialog(
            getMainFrame(),
            "Please type the name of your warrior",
            "Welcome to the "+Global.GAME_NAME,
            JOptionPane.QUESTION_MESSAGE);
    }
	
	/**
	 * Returned de command met alleen een string waarde
	 * @param line
	 * @return
	 */
	public CommandAction getCommand(String line) {
		String command = line;
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
	 * Het frame dat wordt gemaakt waarin de panels e.d. worden geset en worden toegekend
	 * @param game
	 */
	public void initializeFrame(Game game) {
		textAreaListener();
		getTextArea().setBackground(Color.black);
		getTextArea().setBounds(5, 5, 500, 400);
		getTextArea().setForeground(Color.white);
		getTextArea().setToolTipText("This is your information to work work");
		getTextArea().setEditable(false);
		DefaultCaret caret = (DefaultCaret)getTextArea().getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		getTextArea2().setBackground(Color.black);
		getTextArea2().setForeground(Color.white);
		getTextArea2().setBounds(510, 5, 495, 400);
		getTextArea2().setFont(textArea.getFont().deriveFont(30f)); // will only change size to 12pt
		getTextArea2().setToolTipText("Please type any commands here to start");
		DefaultCaret caret2 = (DefaultCaret)getTextArea2().getCaret();
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		getTextArea3().setBackground(Color.black);
		getTextArea3().setBounds(5, 5, 500, 400);
		getTextArea3().setForeground(Color.white);
		getTextArea3().setToolTipText("This is your information to work work");
		getTextArea3().setEditable(false);
		DefaultCaret caret3 = (DefaultCaret)getTextArea3().getCaret();
		caret3.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		
		getScrollpane().setBounds(new Rectangle(5, 5, 500, 400));
		getScrollpane().setBackground(Color.white);
		getScrollpane2().setBounds(new Rectangle(510, 5, 495, 400));
		getScrollpane2().setBackground(Color.white);
		getScrollpane3().setBounds(new Rectangle(1010, 5, 495, 400));
		getScrollpane3().setBackground(Color.white);
		
		getMainFrame().add(getScrollpane());
		getMainFrame().add(getScrollpane2());
		getMainFrame().add(getScrollpane3());
		
		getMainFrame().setSize(Global.FRAME_SIZE_X, Global.FRAME_SIZE_Y);
		getMainFrame().setLayout(null);
		getMainFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getMainFrame().setVisible(true);
		getMainFrame().setResizable(false);

		if (!Game.SERVER_MODE) {
			getGame().setPlayer(new Player(game, getPlayerName()));
		}
	}
	
	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public MoveFrame getMove() {
		return move;
	}

	public void setMove(MoveFrame move) {
		this.move = move;
	}

	public JScrollPane getScrollpane() {
		return scrollpane;
	}

	public void setScrollpane(JScrollPane scrollpane) {
		this.scrollpane = scrollpane;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JPanel getMovePanel() {
		return movePanel;
	}

	public void setMovePanel(JPanel movePanel) {
		this.movePanel = movePanel;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public JTextArea getTextArea2() {
		return textArea2;
	}

	public void setTextArea2(JTextArea textArea2) {
		this.textArea2 = textArea2;
	}

	public JScrollPane getScrollpane2() {
		return scrollpane2;
	}

	public void setScrollpane2(JScrollPane scrollpane2) {
		this.scrollpane2 = scrollpane2;
	}

	public JScrollPane getScrollpane3() {
		return scrollpane3;
	}

	public void setScrollpane3(JScrollPane scrollpane3) {
		this.scrollpane3 = scrollpane3;
	}

	public JTextArea getTextArea3() {
		return textArea3;
	}

	public void setTextArea3(JTextArea textArea3) {
		this.textArea3 = textArea3;
	}

}
