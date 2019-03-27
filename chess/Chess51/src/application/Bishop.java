package application;
import java.util.ArrayList; 

/**
 * Bishop class that extends piece. Movements diagonal
 * @author Yelin Shin,Claudia Pan
 */
public class Bishop extends Piece  {
	
	int up = file -1;
	int down = file +1;
	int left = rank -1;
	int right = rank +1;
	
	/**
	 * default constructor for Bishop 
	 * 
	 * @param color Team color(black or white)
	 */
	public Bishop (String color){
		super("B", color);
		this.color = color;
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
	public ArrayList <String> movePiece (Square [][] board){
		

		ArrayList<String> posmoves = new ArrayList<String>(); //possible moves
		resetCoordinates();
		boolean upLeftMax = false; 
		boolean upRightMax = false; 
		boolean downLeftMax = false;
		boolean downRightMax = false;
		String move = new String();
		
		if (color == "w"){
			//down- left diagonal moves
			while (down < 8 && left > -1 &&  downLeftMax == false){
				if (board [down][left].getPiece() == null){
					move = getmoveString(down, left);
					posmoves.add(move);
					down++ ;
					left--;
				}else if (board [down][left].getPiece().getColor() == "b"){
					move = getmoveString(down,left);
					posmoves.add(move);
					downLeftMax = true;
				}else {
					downLeftMax = true; 
				}
			}
			//up- right diagonal moves
			resetCoordinates();
			while (up > -1 && right < 8 && upRightMax == false){
				if (board [up][right].getPiece() == null){
					move = getmoveString (up, right);
					posmoves.add(move);
					up --;
					right ++; 
				}else if (board[up][right].getPiece().getColor() == "b"){
					move = getmoveString (up,right);
					posmoves.add(move);
					upRightMax = true;
				}else {
					upRightMax = true; 
				}
			}
			//up - left diagonal moves
			resetCoordinates();
			while (up > -1 && left >-1  && upLeftMax == false){
				if(board[up][left].getPiece() == null){
					move = getmoveString(up,left);
					posmoves.add(move);
					up--;
					left --;
				}else if (board[up][left].getPiece().getColor() == "b"){
					move = getmoveString(up,left);
					posmoves.add(move);
					upLeftMax = true;
				}else {
					upLeftMax = true; 
				}
				
			}
			//down- right diagonal moves
			resetCoordinates();
			while (down  < 8 && right < 8 && downRightMax == false){
				if(board[down][right].getPiece() == null ){
					move = getmoveString(down, right);
					posmoves.add(move);
					down ++ ;
					right ++;
				}else if(board [down][right].getPiece().getColor() == "b"){
					move = getmoveString(down, right);
					posmoves.add(move);
					downRightMax = true;
				}else {
					downRightMax = true; 
				}
			}
			resetCoordinates();
			
		}else { //team is black 
			//down- left diagonal moves
			while (down < 8 && left > -1 &&  downLeftMax == false){
				if (board [down][left].getPiece() == null){
					move = getmoveString(down, left);
					posmoves.add(move);
					down++ ;
					left--;
				}else if (board [down][left].getPiece().getColor() == "w"){
					move = getmoveString(down,left);
					posmoves.add(move);
					downLeftMax = true;
				}else {
					downLeftMax = true; 
				}
			}
			//up- right diagonal moves
			resetCoordinates();
			while (up > -1 && right < 8 && upRightMax == false){
				if (board [up][right].getPiece() == null){
					move = getmoveString (up, right);
					posmoves.add(move);
					up --;
					right ++; 
				}else if (board[up][right].getPiece().getColor() == "w"){
					move = getmoveString (up,right);
					posmoves.add(move);
					upRightMax = true;
				}else {
					upRightMax = true; 
				}
			}
			//up - left diagonal moves
			resetCoordinates();
			while (up > -1 && left >-1&& upLeftMax == false){
				if(board[up][left].getPiece() == null){
					move = getmoveString(up,left);
					posmoves.add(move);
					up--;
					left --;
				}else if (board[up][left].getPiece().getColor() == "w"){
					move = getmoveString(up,left);
					posmoves.add(move);
					upLeftMax = true;
				}else {
					upLeftMax = true; 
				}
				
			}
			//down- right diagonal moves
			resetCoordinates();
			while (down  < 8 && right < 8 && downRightMax == false){
				if(board[down][right].getPiece() == null ){
					move = getmoveString(down, right);
					posmoves.add(move);
					down ++ ;
					right ++;
				}else if(board [down][right].getPiece().getColor() == "w"){
					move = getmoveString(down, right);
					posmoves.add(move);
					downRightMax = true;
				}else {
					downRightMax = true; 
				}
			}
			resetCoordinates();
			
		}
		
		return posmoves; 
		
	}
	
	
}
