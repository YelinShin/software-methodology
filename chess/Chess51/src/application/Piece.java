package application;
import java.util.*;

/**
 * Abstract Piece class for all the piece types
 * @author Yelin Shin,Claudia Pan
 */
public abstract class Piece {
	public String type;
	public String color;
	public int rank;//rows
	public int file;//columns
	public boolean moved = false;

	/**
	 * default constructor for Piece
	 * 
	 * @param type of the piece
	 * @param color for team color
	 */
	public Piece(String type, String color) {
		this.type = type;
		this.color = color;
	}
	
	/**
	 * giving out the string of the movement by row and column number
	 * @param rowmove row movement
	 * @param colmove column movement
	 * @return String of that position
	 */
	public String getmoveString(int rowmove, int colmove) {
		//converting movement from int to string 
		
		String tmp = null;
		StringBuilder sb = new StringBuilder();
		
		char col = 0;
		int row = 8 - rowmove;
		
		if (colmove == 0) {
			col = 'a';
		}else if (colmove == 1) {
			col = 'b';
		}else if (colmove == 2) {
			col = 'c';
		}else if (colmove == 3) {
			col = 'd';
		}else if (colmove == 4) {
			col = 'e';
		}else if (colmove == 5) {
			col = 'f';
		}else if (colmove == 6) {
			col = 'g';
		}else if (colmove == 7) {
			col = 'h';
		}
		
		sb.append(col);
		sb.append(row);
		tmp = sb.toString();
		
		
		return tmp;
	}
	
	/**
	 * abstract method of move piece (getting possible position of that piece)
	 * @param board that we are using
	 * @return arrayList of board
	 */
	public abstract ArrayList<String> movePiece(Square[][] board);
	
	/**
	 * @return getting the type of that piece
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * @return getting the color of that piece
	 */
	public String getColor() {
		return this.color;
	}
	
	/**
	 * @return getting the piece is moved or not
	 */
	public boolean getMoved() {
		return this.moved;
	}
	
	/**
	 * @param movement of that piece
	 */
	public void setMove(boolean movement) {
		this.moved = movement;
	}
	

}