package game.frame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Wordt niet gebruikt
 * @author pim
 *
 */
public class KeyListening implements KeyListener{

	public KeyListening(Frame frame) {
		setFrame(frame);
	}
	private Frame frame;
	
	public void keyPressed (KeyEvent e){
    	int x = 0, y = 0;
    	
	    if (e.getKeyCode()==KeyEvent.VK_UP){
	    	y -= 10;
	    } else if(e.getKeyCode()==KeyEvent.VK_DOWN){
	    	y += 10;
	    } else if(e.getKeyCode()==KeyEvent.VK_RIGHT){        
	    	x += 10;
	    } else if(e.getKeyCode()==KeyEvent.VK_LEFT){
	    	x -= 10;
	    }
	    getFrame().getMove().move(x, y);
    }
    public void keyReleased (KeyEvent e){}
    
    public void keyTyped (KeyEvent e){}

    public Frame getFrame() {
		return frame;
	}
	public void setFrame(Frame frame) {
		this.frame = frame;
	}
}