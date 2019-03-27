package application;
import java.util.ArrayList;

/**
 * King class that extends piece.
 * Movements diagonal, forward, backward, left, and right one space
 * @author Yelin Shin,Claudia Pan
 */
public class King extends Piece {
	
	/**
	 * default constructor for King
	 * 
	 * @param teamcolor (black or white)
	 */
	public King(String teamcolor) {
		super("K", teamcolor);
		this.color = teamcolor;
	}
	
	/**
	 * getting the possible movement position  
	 * 
	 * @param board chess board
	 * @return ArrayList of possible position to move
	 */
	public ArrayList<String> movePiece(Square [][] board) {
		//king has 9 possible positions to move
		
		String forward, backward, left, right, dFL, dFR, dBL, dBR;
		ArrayList<String> posmoves = new ArrayList<String>(); //possible moves
		
		forward = getmoveString(file-1, rank);
		backward = getmoveString(file+1, rank);
		left = getmoveString(file, rank-1);
		right = getmoveString(file, rank+1);
		dFL = getmoveString(file-1, rank-1);
		dFR = getmoveString(file-1, rank+1);
		dBL = getmoveString(file+1, rank-1);
		dBR = getmoveString(file+1, rank+1);

		if (color == "w") {
			if(file >0) { // can go forward
				if(board[file-1][rank].getPiece() == null || board[file-1][rank].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(forward);
				}
			}
			
			if(file <7) { // can go backward
				if(board[file+1][rank].getPiece() == null || board[file+1][rank].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(backward);
				}
			}
			
			if(rank>0) { // can go left
				if(board[file][rank-1].getPiece() == null || board[file][rank-1].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(left);
				}
			}
			
			if(rank<7) { // can go right
				if(board[file][rank+1].getPiece() == null || board[file][rank+1].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(right);
				}
			}
			
			if(rank>0 && file>0) { // can go forward left
				if(board[file-1][rank-1].getPiece() == null || board[file-1][rank-1].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(dFL);
				}
			}
			
			if(file>0 && rank<7) { // can go forward right
				if(board[file-1][rank+1].getPiece() == null || board[file-1][rank+1].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(dFR);
				}
			}
			
			if(rank!=0 && file !=7) { // can go backward left
				if(board[file+1][rank-1].getPiece() == null || board[file+1][rank-1].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(dBL);
				}
			}
			
			if(rank!=7 && file!=7) { // can go backward right
				
				if(board[file+1][rank+1].getPiece() == null || board[file+1][rank+1].getPiece().getColor() =="b") {
					// the position should free to move OR killing the black piece
					posmoves.add(dBR);
				}
			}
			
		} else {
			//when king is black
			
			if(file >0) { // can go forward
				if(board[file-1][rank].getPiece() == null || board[file-1][rank].getPiece().getColor() =="w") {
					// the position should free to move OR killing the black piece
					posmoves.add(forward);
				}
			}
			
			if(file <7) { // can go backward
				if(board[file+1][rank].getPiece() == null || board[file+1][rank].getPiece().getColor() =="w") {
					// the position should free to move OR killing the black piece
					posmoves.add(backward);
				}
			}
			
			if(rank>0) { // can go left
				if(board[file][rank-1].getPiece() == null || board[file][rank-1].getPiece().getColor() =="w") {
					// the position should free to move OR killing the black piece
					posmoves.add(left);
				}
			}
			
			if(rank<7) { // can go right
				if(board[file][rank+1].getPiece() == null || board[file][rank+1].getPiece().getColor() =="w") {
					// the position should free to move OR killing the black piece
					posmoves.add(right);
				}
			}
			
			if(rank>0 && file>0) { // can go forward left
				if(board[file-1][rank-1].getPiece() == null || board[file-1][rank-1].getPiece().getColor() =="white") {
					// the position should free to move OR killing the black piece
					posmoves.add(dFL);
				}
			}
			
			if(file>0 && rank<7) { // can go forward right
				if(board[file-1][rank+1].getPiece() == null || board[file-1][rank+1].getPiece().getColor() =="w") {
					// the position should free to move OR killing the black piece
					posmoves.add(dFR);
				}
			}
			
			if(rank!=0 && file !=7) { // can go backward left
				if(file+1<8 && rank-1>-1) {
				if(board[file+1][rank-1].getPiece() == null || board[file+1][rank-1].getPiece().getColor() =="w") {
					// the position should free to move OR killing the black piece
					posmoves.add(dBL);
				}
				}
			}
			
			if(rank!=7 && file!=7) { // can go backward right
				if(file+1<8 && rank+1<8) {
				if(board[file+1][rank+1].getPiece() == null || board[file+1][rank+1].getPiece().getColor() =="w") {
					// the position should free to move OR killing the black piece
					posmoves.add(dBR);
				}
				}
			}
		}
		
		return posmoves;
		
	}
	
}
