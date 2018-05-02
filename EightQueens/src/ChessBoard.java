import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessBoard {

	
	
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private Color colorOne;
	private Color colorTwo;
	private JFrame window;

	public ChessBoard() {
		buildFrame();
		
		window.setVisible(true);
	}
	
	private void buildFrame() {
	      window = new JFrame("Eight Queens");
	      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      window.setSize(new Dimension(WIDTH, HEIGHT));
	      window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS)); 
	   } 
	   

	  public void addPanels() {
		  
	  }
	 


	   public static void main(String[] args) {
	      ChessBoard cb = new ChessBoard();
	      
	   }

	
}
