package application;
import java.util.ArrayList;

/**
 * Rook class that extends piece. 
 * Movements forward, backward, left, and right as many spaces it wants
 * @author Yelin Shin,Claudia Pan
 */
public  class Rook extends Piece {
	
	/**
	 * default constructor for Rook
	 * 
	 * @param color Team color(black or white)
	 */
	public Rook (String color){
		super("R", color);
		this.color = color;
	}
	
	/**
	 * getting the possible movement position  
	 * 
	 * @param board chess board
	 * @return ArrayList of possible position to move
	 */
	public ArrayList <String> movePiece (Square [][] board){
		ArrayList<String> posmoves = new ArrayList<String>(); //possible moves
		int up, down, left, right ;
		up = file -1;
		down = file +1;
		left = rank -1;
		right = rank +1;
		boolean downMax = false; 
		boolean upMax = false; 
		boolean leftMax = false;
		boolean rightMax = false;
		
		if (color == "w"){
			while (down < 8 && down>-1 && downMax == false){ // checking all possible moves down 
				if (board [down][rank].getPiece() == null){
					String move = new String();
					move = getmoveString(down, rank);
					posmoves.add(move);
					down ++;
				}else if (board[down][rank].getPiece().getColor() == "b"){ // kill black piece while moving down
					String move = new String ();
					move = getmoveString (down,rank);
					posmoves.add(move);
					downMax = true;
				}else {
					downMax = true; 
				}
				
			}
			while (up > -1 &&up<8&& upMax == false){
				if (board[up][rank].getPiece() == null){
					String move = new String();
					move = getmoveString(up,rank);
					posmoves.add(move);
					up --;
				}else if (board[up][rank].getPiece().getColor() == "b"){
					String move = new String ();
					move = getmoveString(up,rank);
					posmoves.add(move);
					upMax = true;
				}else { //no more moves upwards 
					upMax = true; 
				}
			}
			while (left > -1&&left<8 && leftMax == false ){
				if(board [file][left].getPiece() == null){
					String move = new String ();
					move = getmoveString (file, left);
					posmoves.add(move);
					left --;
				}else if (board[file][left].getPiece().getColor() == "b"){
					String move = new String();
					move = getmoveString(file, left);
					posmoves.add(move);
					leftMax = true;
				}else {
					leftMax = true;
				}
			}
			while (right <8 &&right>-1&& rightMax == false){
				if(board[file][right].getPiece() == null){
					String move = new String();
					move = getmoveString (file, right);
					posmoves.add(move);
					right ++;
				}else if(board [file][right].getPiece().getColor() == "b"){
					String move = new String();
					move = getmoveString (file, right);
					posmoves.add(move);
					rightMax = true;
				}else {
					rightMax = true; 
				}
			}	
				
		}else { //team is black 
			while (down < 8&&down>-1 && downMax == false){ // checking all possible moves down 
				if (board [down][rank].getPiece() == null){
					String move = new String();
					move = getmoveString(down, rank);
					posmoves.add(move);
					down ++;
				}else if (board[down][rank].getPiece().getColor() == "w"){ // capture white piece while moving down
					String move = new String ();
					move = getmoveString (down,rank);
					posmoves.add(move);
					downMax = true;
				}else {
					downMax = true; 
				}
			}
			while (up > -1 &&up<8 && upMax == false){
				if (board[up][rank].getPiece() == null){
					String move = new String();
					move = getmoveString(up,rank);
					posmoves.add(move);
					up --;
				}else if (board[up][rank].getPiece().getColor() == "w"){
					String move = new String ();
					move = getmoveString(up,rank);
					posmoves.add(move);
					upMax = true;
				}else { //no more moves upwards 
					upMax = true; 
				}
			}
			while (left > -1 &&left<8&& leftMax == false ){
				if(board [file][left].getPiece() == null){
					String move = new String ();
					move = getmoveString (file, left);
					posmoves.add(move);
					left --;
				}else if (board[file][left].getPiece().getColor() == "w"){
					String move = new String();
					move = getmoveString(file, left);
					posmoves.add(move);
					leftMax = true;
				}else {
					leftMax = true;
				}
			}
			while (right <8 &&right>-1&& rightMax == false){
				if(board[file][right].getPiece() == null){
					String move = new String();
					move = getmoveString (file, right);
					posmoves.add(move);
					right ++;
				}else if(board [file][right].getPiece().getColor() == "w"){
					String move = new String();
					move = getmoveString (file, right);
					posmoves.add(move);
					rightMax = true;
				}else {
					rightMax = true; 
				}
			}	
			
		}
		return posmoves;
		
	}
	
}
