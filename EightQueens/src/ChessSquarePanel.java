import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * 
 * @author Will Olson (git: willolson27)
 * Assignment 9: Eight Queens
 * Due May 13, 2018
 *
 */
public class ChessSquarePanel extends JPanel{

	//FIELDS
	private static final long serialVersionUID = 1L;
	private static final int FONTSIZE = 50;
	private Color background;
	private boolean hasQueen;
	
	/**
	 * constructor for new Chess Panel based on a given color and state
	 * @param back color to be set to background color
	 * @param queen whether or not this panel has a queen in it
	 */
	public ChessSquarePanel (Color back, boolean queen) {
		background = back;
		hasQueen = queen;
	}
	
	/**
	 * updates the paint component of the chess square panel, draws a Q if hasQueen is true
	 */
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    g.setFont(new Font("TimesRoman", Font.PLAIN, FONTSIZE));
	    this.setBackground(background);
	    g.setColor(Color.RED);

	    int x = (this.getWidth() / 2) - FONTSIZE/4;
	    int y = (this.getHeight() / 2) + FONTSIZE/4;
	    if (hasQueen)
	    	g.drawString("Q", x, y);
	  }
	
	/**
	 * sets the background color of this panel to a new color
	 * @param c new color for background
	 */
	public void setColor (Color c) {
		background = c;
		repaint();
	}
	
	/**
	 * sets the state (whether it has a queen) of this panel to a given state
	 * @param queen true to change to queen, false to change to empty
	 */
	public void setState(boolean queen) {
		hasQueen = queen;
		repaint();
	}
	
	/**
	 * checks if this panel has a queen in it
	 * @return true if panel has queen, false if empty
	 */
	public boolean hasQueen() {
		return hasQueen;
	}
	
	
	 
	
}
