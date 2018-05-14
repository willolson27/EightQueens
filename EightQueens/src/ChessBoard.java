import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.Icon;
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

// FIELDS	

	//numbers used for various things
	private int n;
	private int ROWS = 8;
	private int COLS = 8;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 1000;
	private static int solNum = 0;
	private int numSuccess = 0;

	
	//Graphics Components
	private Color colorOne = Color.WHITE;
	private Color colorTwo = Color.BLACK;
	private JFrame window;
	
	//String Constants
	private final String SOLVED = "Eight Queens Solved! Iteration no: ";
	private final String FAILED = "Failed to Solve :( Iteration no: ";
	private final String RUN = "GO!";
	private final String CLEAR = "Clear";
	private final static String SUCCESS_RATE = "Success Rate";
	private static final String DISPLAY = "Display Solutions";
	private static final String WILLS = "Display Will's Solution: ";
	private static final String ERROR_IO = "Error: file not found";
	private static final String ERROR_SO = "Error: Stack Overflow";

	private final String SOLUTION_NUM = "Displaying solution number: ";
	
	//Data Structures used
	private ArrayList<ChessSquarePanel[][]> allSolutionsFound = new ArrayList<ChessSquarePanel[][]>();
	private ChessSquarePanel[][] chessGrid = new ChessSquarePanel[ROWS][COLS];
	private int[] givenCols = new int[8];
	
//METHODS
	
	//CONSTRUCTORS AND GRAPHICS
	
	/**
	 * creates a new default instance of ChessBoard for Eight Queens
	 */
	public ChessBoard() {
	
		this(8);
	}
	
	/**
	 * creates a new instance of ChessBoard for n Queens
	 * @param queens size of grid/ number of queens to be placed
	 */
	public ChessBoard(int queens) {
		
		n = queens;
		ROWS = n;
		COLS = n;
		buildFrame();
		JPanel panelOne = buildGridPanels();
		panelOne.setPreferredSize(new Dimension(800, 800));
		JPanel panelTwo = bottomPanel();
		panelTwo.setBounds(0, HEIGHT - 200, WIDTH, 200);
		window.add(panelOne);
		window.add(panelTwo);
		window.setVisible(true);
		window.setResizable(false);
	}
	
	/**
	 * builds the outer frame of this ChessBoard
	 */
	public void buildFrame() {
	      window = new JFrame("Eight Queens");
	      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      window.setSize(new Dimension(WIDTH, HEIGHT));
	      window.setLayout(new FlowLayout()); 
	      
	   } 
	   

	/**
	 * builds the JPanel containing a n*n grid of ChessSquarePanels
	 * @return JPanel that contains the Chess Grid
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
	      grid.setPreferredSize(new Dimension(800,800));
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
		JButton display = new JButton(DISPLAY);
		display.addActionListener(e -> displaySolutions());
		bottom.add(display);
		JButton clear = new JButton(CLEAR);
		clear.addActionListener(e -> clear());
		bottom.add(clear);
		JButton wills = new JButton(WILLS);
		wills.addActionListener(e -> displayGivenSol(givenCols));
		bottom.add(wills);
		return bottom;
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
	
//Methods used to actually run the program	
	
	/**
	 * Runs the n Queens program, called when the button is pressed
	 */
	public void action() {
		
		this.clear();
		ArrayList<Integer> unusedCols = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			unusedCols.add(i);
		} 
		System.out.println(this.catchOverflow(unusedCols));
		storeSolution();
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
		  for (int r = row, c = col; r < n && c < n; r++, c++)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
		  for (int r = row, c = col; r < n && c >= 0; r++, c--)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
		  for (int r = row, c = col; r >= 0 && c < n; r--, c++)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
		  for (int r = row, c = col; r >= 0 && c >= 0; r--, c--)
			  if (chessGrid[r][c].hasQueen()) 
				  return false;
			  
			  
		   return true;
	   }

	   /**
	    * Recursive method that solves the n Queens problem for this instance of ChessBoard
	    * It does this by iterating through each row, starting at n and going down to 0,
	    * traversing through a shuffled list of columns to see which one works first. The
	    * columns are shuffled because if they aren't, it will find the exact same solution 
	    * every time
	    * @param cols - An ArrayList of the columns that have not yet been filled
	    * @param row the current index 
	    * @return Boolean true if the spot is Valid, false otherwise
	    */
	   public boolean solve(ArrayList<Integer> cols, int row) {
			  
		   if (row == -1)
			   return true;
		   if (cols.size() == 0)
			   return true;
			  
		   ArrayList<Integer> temp = (ArrayList<Integer>) cols.clone();
		   for (Integer c : cols) {
			  if (isValid(row, c)) {
				  temp.remove(c);
				  chessGrid[row][c].setState(true);
				  if (solve(temp, row - 1) == true) {
					  chessGrid[row][c].setState(true);
					  return true;
				  }
				  else {
					  temp.add(c);
				  	chessGrid[row][c].setState(false);
				  }
			  }
		   }  
		   row++;
		   return false;
			   
		   }  
	
/* Original Solution - It only works between 79-82% of time but i like it better so i'll leave it
	   /**
	    * Recursive method that solves the n Queens problem for this instance of ChessBoard
	    * It does this by iterating through each row, starting at n and going down to 0,
	    * and trying a random column to see if that spot isValid
	    * @param cols - An ArrayList of the columns that have not yet been filled
	    * @param row the current index 
	    * @return Boolean true if the spot is Valid, false otherwise
	    
	   public boolean solve(ArrayList<Integer> cols, int row) {
			  
			  if (row == -1)
				  return true;
			  if (cols.size() == 0)
				  return true;
			  
			  int a = (int)(Math.random() * cols.size() - 1);
			 
			  Integer c = cols.get(a);
			  
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
	  */
	   
	   /**
	    * stores the current solution to EightQueens in an Array
	    * @return True if solution was unique and stored false otherwise
	    */
	   public boolean storeSolution() {
		   
		   ChessSquarePanel[][] copy = new ChessSquarePanel[8][8];

		   for (int r = 0; r < ROWS; r ++) {
			   for (int c = 0; c < COLS; c++) {
				   copy[r][c] = new ChessSquarePanel(colorOne, chessGrid[r][c].hasQueen());
				 
			   }
		   }
		   
		   
		   for (ChessSquarePanel[][] g : allSolutionsFound) {
			   if (!gridEqual(g, copy))
				   return false;
		   }
		   allSolutionsFound.add(copy);
		   
		   return true;
		   
	   }
	   
	   /**
	    * displays all the solutions found for n Queens 
	    */
	   public void displaySolutions() {
		   
		   int i = 1;
		   for (ChessSquarePanel[][] g : allSolutionsFound) {
			 display(g, i);
			 i++;
			   try {
					Thread.sleep(2);
				   } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		   }
		   
		   
	   }
	   
	   /**
	    * Displays One Given Solution of the game
	    * @param g Given grid
	    * @param i number solution this was
	    */
	   public void display(ChessSquarePanel[][] g, int i) {

		   for (int r = 0; r < ROWS; r ++) {
			   for (int c = 0; c < COLS; c++) {
				   ChessSquarePanel s = chessGrid[r][c];
				   boolean boo = g[r][c].hasQueen();
				   if (boo == true)
					   chessGrid[r][c].setState(true);
				   else
					   chessGrid[r][c].setState(false);
				 
			   }
		   }
		   System.out.println(SOLUTION_NUM + i); 
	
	   }
	   
	   /**
	    * randomizes the order of a given arraylist
	    * @param old ArrayList to be randomized
	    * @return new arraylist in random order
	    */ 
		public ArrayList<Integer> randOrder(ArrayList<Integer> old) {
			
			ArrayList<Integer> cols = new ArrayList<Integer>();
			
			while (!old.isEmpty()) {
				int a = (int)(Math.random() * old.size());
				cols.add(old.get(a));
				old.remove(a);
			}
			
			return cols;
		}
		
		/**
		 * validates that there are eight queens on the board
		 * @return boolean - true if there are eight queens, false if not
		 */
		public boolean checkIfSolved() {
			
			int numQ = 0;
			
			ChessSquarePanel s;
			for (int r = 0; r < ROWS; r++) {
		         for (int c = 0; c < COLS; c++) {
		            s = chessGrid[r][c]; 
		            if (s.hasQueen()) {
		            	numQ++;
		            }
		         }
		      }
			
			if (numQ != n)
				return false;
			else
				return true;
			
		}
	 
		/**
		 * container method for solve() used to prevent a stack overflow
 		 * @param cols ArrayList of cols to be used in solve
		 * @return String representation of whether program worked or not
		 */
	 	public String catchOverflow(ArrayList<Integer> cols) {
	 		
	 		String toReturn = "";
	 		ArrayList<Integer> temp = randOrder(cols);
	 		
	 		try{
	            solve(temp, n - 1);
	        }
	        catch(StackOverflowError e){
	            return ERROR_SO;
	        }
	 		solNum += 1;
	 		if (checkIfSolved()) {
	 			numSuccess++;
	 			toReturn += SOLVED + solNum;

	 		}
	 		else {
	 			toReturn += FAILED + solNum;
	 			
	 		}
	 		return toReturn;
	 		
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
	   
	   /**
	    * checks if two given chess grids are equal
	    * @param g1 first grid to be compared
	    * @param g2 second grid to be compared
	    * @return true if equal false if not
	    */
	   public boolean gridEqual(ChessSquarePanel[][] g1, ChessSquarePanel[][] g2) {
		   
		   for (int r = 0; r < ROWS; r++) {
			   for (int c = 0; c < ROWS; c++)  {
				   boolean a = g1[r][c].hasQueen();
			   	   boolean b = g2[r][c].hasQueen();
				   if (b != a)
					return false;
			   }
		   }
		   
		   return true;
		   
	   }
	   
	   /**
	    * Displays a solution given in a text file
	    * @param col - column coordinates of where the queens are
	    */
	   public void displayGivenSol(int[] col) {
		   
		   this.clear();

		   int c = 0;
		   
		   for (int r = 0; r < n; r++) {
			   c = col[r];
			   chessGrid[r][c].setState(true);
		   }
		   
		   
	   }
	   

	   /**
	    * Tests this Program by running the problem 1000 times and finding the success rate
	    * @param args Program Args
	 * @throws IOException Error in file handling
	    */
	   public static void main(String[] args) throws IOException {
		
	      ChessBoard cb = new ChessBoard(8);
	      BufferedReader inputReader = null;
		    try {
		    	inputReader = new BufferedReader(new FileReader("willsSolution.txt"), 1024);
		    }
		    catch (FileNotFoundException e) {
		    	System.out.println(ERROR_IO);
		    	System.exit(0);
		    }
	      for (int i = 0; i < 8; i++) {
	    	  String s = inputReader.readLine();
	    	  cb.givenCols[i] = Integer.parseInt(s);
	      }
	      
	      while (solNum < 1000)
	    	  cb.action();
	      double s = (cb.numSuccess / (double) solNum) * 100;
	      System.out.println(SUCCESS_RATE + " " + s + "%");

	      
	      

	   }

	
}
