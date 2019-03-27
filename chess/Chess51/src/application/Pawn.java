package application;
import java.util.ArrayList;

/**
 * Pawn class that extends piece.
 * 1 or 2 for 1st move, after just 1 move. kill diagonal
 * @author Yelin Shin,Claudia Pan
 */
public class Pawn extends Piece {
	//boolean moved = false; //pawn can move 2 squares for first move
	
	/**
	 * default constructor for Pawn 
	 * 
	 * @param teamcolor(black or white)
	 */
	public Pawn(String teamcolor) {
		super("p", teamcolor);
		this.color = teamcolor;
	}
	
	/**
	 * getting the possible movement position  
	 * 
	 * @param board chess board
	 * @return ArrayList of possible position to move
	 */
	public ArrayList<String> movePiece(Square [][] board) {
		
		String spot1, spot2, spot3, spot4; // spot 1,2 up/down spot 3
		ArrayList<String> posmoves = new ArrayList<String>(); //possible moves
		
		/*if(getMoved() == false) { //not moved yet
			moved = false;
		}*/
		
		
		if (color == "w") { 
			spot1 = getmoveString(file-1, rank); // 1 up
			spot2 = getmoveString(file-2, rank); // 2 up
			
			if(getMoved() == false) {// can move 2 up or 1 up
				if(file-1>-1 && file-2>-1) {
				if (board[file-1][rank].getPiece() == null && board[file-2][rank].getPiece()==null) {
					// if there's no piece on the board for 1 up and 2 up
					posmoves.add(spot1);
					posmoves.add(spot2);
				}else if (board[file-1][rank].getPiece() == null) {
					// only 1up is free
					posmoves.add(spot1);
				}
				}
			}else { // already moved before, can go 1 up
				if(file-1>-1) {
				if(board[file-1][rank].getPiece() == null) {
					posmoves.add(spot1);
				}
				}
			}
			
			
			if( file >= 1 && file <8) {
				if (rank >0 && rank <7) {
					//top left
					if(board[file-1][rank-1].getPiece() != null && board[file-1][rank-1].getPiece().getColor() != "w") {
						spot3 = getmoveString(file-1,rank-1);
						posmoves.add(spot3);
					}
					
					//top right
					if(board[file-1][rank+1].getPiece() != null && board[file-1][rank+1].getPiece().getColor() != "w") {
						spot4 = getmoveString(file-1,rank+1);
						posmoves.add(spot4);
					}
				} else if ( rank == 0) {
					//only top right
					if(board[file-1][rank+1].getPiece() != null && board[file-1][rank+1].getPiece().getColor() != "w") {
						spot4 = getmoveString(file-1,rank+1);
						posmoves.add(spot4);
					}
				} else if ( rank == 7) {
					//only top left
					if(board[file-1][rank-1].getPiece() != null && board[file-1][rank-1].getPiece().getColor() != "w") {
						spot3 = getmoveString(file-1,rank-1);
						posmoves.add(spot3);
					}
				}
			}
			
			
			
		} else { // color is black
			spot1 = getmoveString(file+1, rank); // 1 down
			spot2 = getmoveString(file+2, rank); // 2 down
			
			if(getMoved() == false) { // can go 1 down or 2 down
				
				if(file+1<8 && file+2<8) {
					if(board[file+1][rank].getPiece() ==null && board[file+2][rank].getPiece() == null) {
						posmoves.add(spot1);
						posmoves.add(spot2);
					} else if (board[file+1][rank].getPiece() ==null) {
						posmoves.add(spot1);
					}
				}
				
			} else if (board[file+1][rank].getPiece()==null) { // moved before - can go only 1 down
				posmoves.add(spot1);
			}
			
			
			if( file >= 1 && file <8) {
				if (rank >0 && rank <7) {
					//down left
					if(board[file+1][rank-1].getPiece() != null && board[file+1][rank-1].getPiece().getColor() != "b") {
						spot3 = getmoveString(file+1,rank-1);
						posmoves.add(spot3);
					}
					
					//down right
					if(board[file+1][rank+1].getPiece() != null && board[file+1][rank+1].getPiece().getColor() != "b") {
						spot4 = getmoveString(file+1,rank+1);
						posmoves.add(spot4);
					}
				} else if ( rank == 0) {
					//only down right
					if(board[file+1][rank+1].getPiece() != null && board[file+1][rank+1].getPiece().getColor() != "b") {
						spot4 = getmoveString(file+1,rank+1);
						posmoves.add(spot4);
					}
				} else if ( rank == 7) {
					//only down left
					if(board[file+1][rank-1].getPiece() != null && board[file+1][rank-1].getPiece().getColor() != "b") {
						spot3 = getmoveString(file+1,rank-1);
						posmoves.add(spot3);
					}
				}
			}
			
			
			
			
		}
		
		return posmoves;
		
	}
	
}
