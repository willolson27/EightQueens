import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 
 * @author Will Olson (git: willolson27)
 * Assignment 9: Eight Queens
 * Due May 13, 2018
 *
 */
public class ChessBoard {

	
	//Fields
	private int n;
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	private Color colorOne = Color.WHITE;
	private Color colorTwo = Color.BLACK;
	private JFrame window;
	private final String SOLVED = "Eight Queens Solved! Iteration no: ";
	private final String RUN = "GO!";
	private int solNum = 0;
	private final int MAX_ITER = 1000;
	
	private ChessSquarePanel[][] chessGrid = new ChessSquarePanel[ROWS][COLS];
	
	private int iter = 0;
	
	/**
	 * creates a new default instance of ChessBoard for Eight Queens
	 */
	public ChessBoard() {
	
		n = 8;
		buildFrame();
		JPanel panelOne = buildGridPanels();
		JPanel panelTwo = bottomPanel();
		window.add(panelOne);
		window.add(panelTwo);
		window.setVisible(true);
	}
	
	/**
	 * creates a new instance of ChessBoard for n Queens
	 * @param queens size of grid/ number of queens to be placed
	 */
	public ChessBoard(int queens) {
		
		n = queens;
		buildFrame();
		JPanel panelOne = buildGridPanels();
		JPanel panelTwo = bottomPanel();
		window.add(panelOne);
		window.add(panelTwo);
		window.setVisible(true);
	}
	
	/**
	 * builds the outer frame of this ChessBoard
	 */
	public void buildFrame() {
	      window = new JFrame("Eight Queens");
	      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      window.setSize(new Dimension(WIDTH, HEIGHT));
	      window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS)); 
	      
	   } 
	   

	/**
	 * builds the JPanel containing a n*n grid of ChessSquarePanels
	 * @return
	 */
	public JPanel buildGridPanels() {
	      JPanel grid = new JPanel();
	      grid.setLayout(new GridLayout(ROWS,COLS));
	      Color bg;
	      for (int r = 0; r < ROWS; r++) {
	         for (int c = 0; c < COLS; c++) {
	            bg = setPanelColor(r,c);
	            ChessSquarePanel s = new ChessSquarePanel(bg, false);
	            chessGrid[r][c] = s;  
	            grid.add(s);	           
	         }
	      }
	      return grid;
	   }
	
	/**
	 * builds the bottom panel of this ChessBoard - contains a activation button
	 * @return Bottom JPanel
	 */
	public JPanel bottomPanel() {
		JPanel bottom = new JPanel();
		JButton go = new JButton(RUN);
		go.addActionListener(e -> action());
		bottom.add(go);
		return bottom;
	}
	
	/**
	 * Runs the n Queens program, called when the button is pressed
	 */
	public void action() {
		
		this.clear();
		ArrayList<Integer> unusedCols = new ArrayList<Integer>();
		for (int i = 0; i < 8; i++) {
			unusedCols.add(i);
		} 
		System.out.println(this.catchOverflow(unusedCols));
		
	}
	
	/**
	 * sets the panel color of a given chess square to color one or color two based on location
	 * @param r y coordinate of location
	 * @param c c coordinate of location
	 * @return Color to be set to a given chess panel
	 */
	public Color setPanelColor(int r, int c) {
			int indexR = r % 2;
			int indexC = c % 2;
			if (indexR == indexC)
				return colorOne;
			else
				return colorTwo;
		}
	 
	 /**
	  * checks if a placement at a given coordinate is valid/legal
	  * @param row - y coordinate of placement
	  * @param col - x coordinate of placement
	  * @return boolean : true if legal, false if not
	  */
	   public boolean isValid(int row, int col) {
		  
		  //check vertically 
		  for (int r = 0; r < ROWS; r++)
			  if (chessGrid[r][col].hasQueen())
				  return false;
		  
		  //check horizontally
		  for (int c = 0; c < COLS; c++) 
			  if (chessGrid[row][c].hasQueen())
				  return false;
			  
		  //Check diagonally
		  for (int r = row, c = col; r < 8 && c < 8; r++, c++)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
		  for (int r = row, c = col; r < 8 && c >= 0; r++, c--)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
		  for (int r = row, c = col; r >= 0 && c < 8; r--, c++)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
		  for (int r = row, c = col; r >= 0 && c >= 0; r--, c--)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
			  
			  
		   return true;
	   }
	   
/*	   public int getDiagLength(int row, int col) {
		   
		   int length = 0;
		   
		   if (row > col)
			   return 8 - (row - col);
		   else if (col > row)
			   return 8 - (row - col);
		   return 8;
		   
		   
		   
	   }
		
 	   private boolean solve(ArrayList<int[]> positions) {
		   
		   if (positions.size() == 8)
			   return true;
		   
		   for (int r = 0; r < ROWS; r++) {
		         for (int c = 0; c < COLS; c++) {
		          if (isValid(r,c)) {
		        	  chessGrid[r][c].setState(true);
		        	  int[] pos = {r,c};
		        	  positions.add(pos);
		        	  if (solve(positions) == true)
		        		  return true;
		        	  else
		        		  positions.remove(pos);
		          }
	  
		         }
		      }
		   
		   return false;
		   
	   } 
	   
	 private boolean solve(ArrayList<Integer> cols, int row) {
		  
		  if (row == -1)
			  return true;
		  if (cols.size() == 0)
			  return true;
		  
		  int a = (int)(Math.random() * cols.size() - 1);
		 
		  int c = cols.get(a);
		  int r = 0;
		  if (isValid(row, c)) {
			  cols.remove(cols.get(a));
			  chessGrid[row][c].setState(true);
			  if (solve(cols, row - 1) == true) {
				  chessGrid[row][c].setState(true);
				  return true;
			  }
			  else {
		//		if (iter >= MAX_ITER)
		//		   return false;
				  cols.add(c);
			  	chessGrid[row][c].setState(false);
			  	iter++;
			  	return solve (cols, row);
			  
			  }
		  }
			  
		   row++;
		   return false;
		   
	   } 
	 
	 private boolean solve(ArrayList<Integer> cols, int row) {
		  
		  if (row == -1)
			  return true;
		  if (cols.size() == 0)
			  return true;
		  ArrayList<Integer> temp = randOrder(cols);
		  cols = (ArrayList<Integer>) temp.clone();
		  Integer c = 0;
		  
		  Iterator<Integer> iter = temp.iterator();
		  while(iter.hasNext()) {
			  c = iter.next();
			  if (isValid(row, c)) {
			      iter.remove();
				  chessGrid[row][c].setState(true);
				  if (solve(temp, row - 1) == true) {
					  
					  return true;
				  }
				  else {
				  chessGrid[row][c].setState(false);
				  temp.add(c);
				  }
			  }
		  }
			  
		   row++;
		   return false;
		   
	   } 
	   
	   private boolean solve(ArrayList<Integer> cols, int row) {
			  
			  if (row == -1)
				  return true;
			  if (cols.size() == 0)
				  return true;
			  
			  int a = (int)(Math.random() * cols.size() - 1);
			 
			  int c = cols.get(a);
			  int r = 0;
			  if (isValid(row, c)) {
				  cols.remove(cols.get(a));
				  chessGrid[row][c].setState(true);
				  if (solve(cols, row - 1) == true) {
					  chessGrid[row][c].setState(true);
					  return true;
				  }
				  else {
					  cols.add(c);
				  	chessGrid[row][c].setState(false);
			//	  	iter++;
			//	  	return solve (cols, row);
				  
				  }
			  }
				  
			   row++;
			   return false;
			   
		   }  */
	
	   private boolean solve(ArrayList<Integer> cols, int row) {
			  
			  if (row == -1)
				  return true;
			  if (cols.size() == 0)
				  return true;
			  
			  int a = (int)(Math.random() * cols.size() - 1);
			 
			  int c = cols.get(a);
			  
			  if (isValid(row, c)) {
				  cols.remove(cols.get(a));
				  chessGrid[row][c].setState(true);
				  if (solve(cols, row - 1) == true) {
					  chessGrid[row][c].setState(true);
					  return true;
				  }
				  else {
					cols.add(c);
				  	chessGrid[row][c].setState(false);
				  	iter++;
				  	return solve (cols, row);
				  }
			  }
				  
			   row++;
			   return false;
			   
		   } 
	   
		public ArrayList<Integer> randOrder(ArrayList<Integer> old) {
			
			ArrayList<Integer> cols = new ArrayList<Integer>();
			
			while (!old.isEmpty()) {
				int a = (int)(Math.random() * old.size() - 1);
				cols.add(old.get(a));
				old.remove(a);
			}
			
			return cols;
		}
	 
	 	private String catchOverflow(ArrayList<Integer> cols) {
	 		
	 		try{
	            solve(cols, n - 1);
	        }
	        catch(StackOverflowError e){
	         /*   this.clear();
	            return catchOverflow(cols); */
	        	return "failed";
	        }
	 		solNum += 1;
	 		return SOLVED + solNum;
	 		
	 	}
	 	
	 	
	   /**
	    * removes all queens from the board
	    */
	   public void clear () {
	 		for (int r = 0; r < ROWS; r++) {
	 			for (int c = 0; c < COLS; c++) {
	 				chessGrid[r][c].setState(false);
	 			}
	 		}
	 	}
	   
	   
	   public boolean solve() {
		   
		   
		   return false;
	   }

	   public static void main(String[] args) {
		
	      ChessBoard cb = new ChessBoard();
/*	//      ArrayList<int[]> pos = new ArrayList<int[]>();
	    
	      ArrayList<Integer> unusedCols = new ArrayList<Integer>();
	//      boolean[] cols = new boolean[8];
	      for (int i = 0; i < 8; i++) {
	    //	  cols[i] = false;
	    	  unusedCols.add(i);
	      } 
	      System.out.println(cb.catchOverflow(unusedCols));

	     */
	   }

	
}
