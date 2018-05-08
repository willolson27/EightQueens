import java.awt.Color;

import javax.swing.JPanel;

public class ChessSquarePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color background;
	private boolean isEmpty;
	
	public ChessSquarePanel (Color back, boolean empty) {
		setBackground(back);
		isEmpty = empty;
	}
	
	public void setColor (Color c) {
		background = c;
	}
	
	public void setState(boolean empty) {
		isEmpty = empty;
	}
	
	public boolean isEmpty() {
		return isEmpty();
	}
	
	
	 
	
}
