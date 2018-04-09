package game.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * Wordt niet gebruikt
 * @author pim
 *
 */
@SuppressWarnings("serial")
public class MoveFrame extends JPanel {
	
	int x = 100;
	int y = 100;
	
	public void move(int moveX, int moveY) {
		x = x + moveX;
		y = y + moveY;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.green);
        g2d.fillOval(x, y, 30, 30);
	}
}
