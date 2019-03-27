package application;
import java.util.ArrayList;

/**
 * Queen class that extends piece.
 * diagonal, forward, backward, left, and right as many spaces it wants
 * @author Yelin Shin,Claudia Pan
 */
public class Queen extends Piece{
	
	int up,down,left,right;
	
	/**
	 * default constructor for Queen 
	 * 
	 * @param teamcolor is black or white
	 */
	public Queen(String teamcolor) {
		super("Q", teamcolor);
		this.color = teamcolor;
	}
	
	/**
	 * reset the the coordinate as original position 
	 */
	public void resetCoordinates () {
		up = file -1;
		down = file +1;
		left = rank -1;
		right = rank +1;
	}
	
	/**
	 * getting the possible movement position  
	 * 
	 * @param board chess board
	 * @return ArrayList of possible position to move
	 */
	public ArrayList<String> movePiece(Square [][] board) {
		
		ArrayList<String> posmoves = new ArrayList<String>(); //possible moves
		up = file -1; //file = actually rank
		down = file +1;
		left = rank -1; //rank = actually file
		right = rank +1;
		
		boolean DRmax = false; 
		boolean URmax = false; 
		boolean DLmax = false;
		boolean ULmax = false;
		boolean Umax = false;
		boolean Dmax = false;
		boolean Lmax = false;
		boolean Rmax = false;
		String tmpmove = new String();
		
		if (color =="w") {
			
			//setting up max position 
			while(up>-1 &&up<8 && !Umax) { // can go until hit white piece or kill black piece or end of the board
				if(board[up][rank].getPiece()==null) {
					tmpmove = getmoveString(up, rank);
					posmoves.add(tmpmove);
					up--;
				} else if ( board[up][rank].getPiece().getColor() == "b") { // can kill
					tmpmove = getmoveString(up, rank);
					posmoves.add(tmpmove);
					Umax=true; // can't go further 
				} else {
					// meet white piece (cannot take that position)
					Umax=true;
				}
			}
			resetCoordinates();
			//setting down max position
			while(down<8 &&down >-1&& !Dmax) { // can go until hit white piece or kill black piece or end of the board
				if(board[down][rank].getPiece()==null) {
					tmpmove = getmoveString(down, rank);
					posmoves.add(tmpmove);
					down++;
				} else if ( board[down][rank].getPiece().getColor() == "b") { // can kill
					tmpmove = getmoveString(down, rank);
					posmoves.add(tmpmove);
					Dmax=true; // can't go further 
				} else {
					// meet white piece (cannot take that position)
					Dmax=true;
				}
			}
			resetCoordinates();
			//setting right max position
			while(right<8 &&right>-1&& !Rmax) { // can go until hit white piece or kill black piece or end of the board
				if(board[file][right].getPiece()==null) {
					tmpmove = getmoveString(file, right);
					posmoves.add(tmpmove);
					right++;
				} else if ( board[file][right].getPiece().getColor() == "b") { // can kill
					tmpmove = getmoveString(file, right);
					posmoves.add(tmpmove);
					Rmax=true; // can't go further 
				} else {
					// meet white piece (cannot take that position)
					Rmax=true;
				}
			}
			resetCoordinates();
			//setting left max position
			while(left>-1 && left<8 && !Lmax) { // can go until hit white piece or kill black piece or end of the board
				if(board[file][left].getPiece()==null) {
					tmpmove = getmoveString(file, left);
					posmoves.add(tmpmove);
					left--;
				} else if ( board[file][left].getPiece().getColor() == "b") { // can kill
					tmpmove = getmoveString(file, left);
					posmoves.add(tmpmove);
					Lmax=true; // can't go further 
				} else {
					// meet white piece (cannot take that position)
					Lmax=true;
				}
			}
			
			
			resetCoordinates();
			
			//for Upper Right diagonal 
			while(up>-1 &&up<8 &&right>-1&& right<8 && !URmax) {
				if(board[up][right].getPiece()==null) {
					tmpmove = getmoveString(up, right);
					posmoves.add(tmpmove);
					up--;
					right++;
				} else if (board[up][right].getPiece().getColor() == "b"){ // kill diagonal
					tmpmove = getmoveString(up, right);
					posmoves.add(tmpmove);
					URmax = true;
				} else {
					URmax = true;
				}
			}
			
			resetCoordinates();
			
			//for Upper Left diagonal 
			while(up>-1 &&up<8 &&left<8 && left>-1 && !ULmax) {
				if(board[up][left].getPiece()==null) {
					tmpmove = getmoveString(up, left);
					posmoves.add(tmpmove);
					up--;
					left--;
				} else if (board[up][left].getPiece().getColor() == "b"){ // kill diagonal
					tmpmove = getmoveString(up, left);
					posmoves.add(tmpmove);
					ULmax = true;
				} else {
					ULmax = true;
				}
			}
			
			resetCoordinates();
			
			//for Down Left diagonal 
			while(down<8 &&down>-1 && left>-1 &&left<8 && !DLmax) {
				if(board[down][left].getPiece()==null) {
					tmpmove = getmoveString(down, left);
					posmoves.add(tmpmove);
					down++;
					left--;
				} else if (board[down][left].getPiece().getColor() == "b"){ // kill diagonal
					tmpmove = getmoveString(down, left);
					posmoves.add(tmpmove);
					DLmax = true;
				} else {
					DLmax = true;
				}
			}
			
			resetCoordinates();
			
			//for Down right diagonal 
			while(down<8&&down>-1&&right>-1 && right<8 && !DRmax) {
				if(board[down][right].getPiece()==null) {
					tmpmove = getmoveString(down, right);
					posmoves.add(tmpmove);
					down++;
					right++;
				} else if (board[down][right].getPiece().getColor() == "b"){ // kill diagonal
					tmpmove = getmoveString(down, right);
					posmoves.add(tmpmove);
					DRmax = true;
				} else {
					DRmax = true;
				}
			}
			
			
		}else { // for black piece
			
			//setting up max position 
			while(up>-1 && up<8&& !Umax) { // can go until hit black piece or kill white piece or end of the board
				if(board[up][rank].getPiece()==null) {
					tmpmove = getmoveString(up, rank);
					posmoves.add(tmpmove);
					up--;
				} else if ( board[up][rank].getPiece().getColor() == "w") { // can kill
					tmpmove = getmoveString(up, rank);
					posmoves.add(tmpmove);
					Umax=true; // can't go further 
				} else {
					// meet black piece (cannot take that position)
					Umax=true;
				}
			}
			resetCoordinates();
			//setting down max position
			while(down<8 &&down>-1&& !Dmax) { // can go until hit black piece or kill white piece or end of the board
				if(board[down][rank].getPiece()==null) {
					tmpmove = getmoveString(down, rank);
					posmoves.add(tmpmove);
					down++;
				} else if ( board[down][rank].getPiece().getColor() == "w") { // can kill
					tmpmove = getmoveString(down, rank);
					posmoves.add(tmpmove);
					Dmax=true; // can't go further 
				} else {
					// meet black piece (cannot take that position)
					Dmax=true;
				}
			}
			resetCoordinates();
			//setting right max position
			while(right<8 &&right>-1 && !Rmax) { // can go until hit black piece or kill white piece or end of the board
				if(board[file][right].getPiece()==null) {
					tmpmove = getmoveString(file, right);
					posmoves.add(tmpmove);
					right++;
				} else if ( board[file][right].getPiece().getColor() == "w") { // can kill
					tmpmove = getmoveString(file, right);
					posmoves.add(tmpmove);
					Rmax=true; // can't go further 
				} else {
					// meet black piece (cannot take that position)
					Rmax=true;
				}
			}
			resetCoordinates();
			//setting left max position
			while(left>-1 &&left<8&& !Lmax) { // can go until hit black piece or kill white piece or end of the board
				if(board[file][left].getPiece()==null) {
					tmpmove = getmoveString(file, left);
					posmoves.add(tmpmove);
					left--;
				} else if ( board[file][left].getPiece().getColor() == "w") { // can kill
					tmpmove = getmoveString(file, left);
					posmoves.add(tmpmove);
					Lmax=true; // can't go further 
				} else {
					// meet black piece (cannot take that position)
					Lmax=true;
				}
			}
			
			resetCoordinates();
			
			//for Upper Right diagonal 
			while(up>-1 &&up<8&& right>-1&& right<8 && !URmax) {
				if(board[up][right].getPiece()==null) {
					tmpmove = getmoveString(up, right);
					posmoves.add(tmpmove);
					up--;
					right++;
				} else if (board[up][right].getPiece().getColor() == "w"){ // kill diagonal
					tmpmove = getmoveString(up, right);
					posmoves.add(tmpmove);
					URmax = true;
				} else {
					URmax = true;
				}
			}
			
			resetCoordinates();
			
			//for Upper Left diagonal 
			while(up>-1 && up<8 &&left<8&& left>-1 && !ULmax) {
				if(board[up][left].getPiece()==null) {
					tmpmove = getmoveString(up, left);
					posmoves.add(tmpmove);
					up--;
					left--;
				} else if (board[up][left].getPiece().getColor() == "w"){ // kill diagonal
					tmpmove = getmoveString(up, left);
					posmoves.add(tmpmove);
					ULmax = true;
				} else {
					ULmax = true;
				}
			}
			
			resetCoordinates();
			
			//for Down Left diagonal 
			while(down<8 && down>-1 && left<8 && left>-1 && !DLmax) {
				if(board[down][left].getPiece()==null) {
					tmpmove = getmoveString(down, left);
					posmoves.add(tmpmove);
					down++;
					left--;
				} else if (board[down][left].getPiece().getColor() == "w"){ // kill diagonal
					tmpmove = getmoveString(down, left);
					posmoves.add(tmpmove);
					DLmax = true;
				} else {
					DLmax = true;
				}
			}
			
			resetCoordinates();
			
			//for Down right diagonal 
			while(down<8 && down>-1 && right>-1 && right<8 && !DRmax) {
				if(board[down][right].getPiece()==null) {
					tmpmove = getmoveString(down, right);
					posmoves.add(tmpmove);
					down++;
					right++;
				} else if (board[down][right].getPiece().getColor() == "w"){ // kill diagonal
					tmpmove = getmoveString(down, right);
					posmoves.add(tmpmove);
					DRmax = true;
				} else {
					DRmax = true;
				}
			}
		}
		
		return posmoves;
	}
}
