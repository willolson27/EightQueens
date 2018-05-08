import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessBoard {

	
	
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	private Color colorOne;
	private Color colorTwo;
	private JFrame window;

	public ChessBoard() {
		   colorOne = Color.RED;
		   colorTwo = Color.BLACK;
		buildFrame();
		
		window.setVisible(true);
	}
	
	private void buildFrame() {
	      window = new JFrame("Eight Queens");
	      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      window.setSize(new Dimension(WIDTH, HEIGHT));
	      window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS)); 
	      addPanels();
	   } 
	   

	  public void addPanels() {
		  int xSize = 50;
		  int ySize = 50;
		  ChessSquarePanel sqr = new ChessSquarePanel(colorOne, true);
		  int index = 0;
		  for (int i = 0; i < ROWS; i++) {
			  for (int j = 0; j < COLS; j++) {
				  if (index % 2 == 0) {
					 sqr = new ChessSquarePanel(colorOne, true); 
				  }
				  else
					  sqr = new ChessSquarePanel(colorTwo, true);
				  
				  window.add(sqr);
				  sqr.setBounds((i + 1) * xSize , (j + 1) * ySize, xSize, ySize);
				  index++;
			  }
		  } */
	  }
	 


	   public static void main(String[] args) {
		
	      ChessBoard cb = new ChessBoard();
	      
	   }

	
}
