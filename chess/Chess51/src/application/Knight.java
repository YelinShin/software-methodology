package application;

import java.util.ArrayList;

/**
 * Knights class that extends piece. 
 * Movements include 2vertical/1horizontal or 1vertical/2horizontal in different directions
 * @author Yelin Shin,Claudia Pan
 */
public class Knight extends Piece {
	
	/**
	 * default constructor for Knight
	 * 
	 * @param color Team color(black or white)
	 */
	public Knight (String color){
		super("N", color);
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
		String spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8;
		
		if(color == "w"){
			//spot1 is up 2, left 1
			if (rank != 0 && file > 1){
				if(file-2>=0 && rank-1>=0) {
					if(board[file-2][rank-1].getPiece()== null || (board[file-2][rank-1].getPiece()!=null && board[file-2][rank-1].getPiece().getColor()=="b")){
						spot1 = getmoveString(file-2, rank-1);
						posmoves.add(spot1);
					}
				}
			}//spot2 is up 2, right 1
			if(rank !=7 && file > 1){
				if(file-2>=0 && rank+1<=7) {
					if(board[file-2][rank+1].getPiece()==null || (board[file-2][rank+1].getPiece()!=null && board[file-2][rank+1].getPiece().getColor() == "b")){
						spot2 = getmoveString(file-2, rank+1);
						posmoves.add(spot2);
					}
				}
			}
			//spot3 is up1, right 2
			if(rank < 6 && file !=0){
				if(file-1>=0 && rank+2<=7) {
					if(board[file-1][rank+2].getPiece() == null ||(board[file-1][rank+2].getPiece()!=null && board[file-1][rank+2].getPiece().getColor()=="b")){
						spot3 = getmoveString(file-1,rank+2);
						posmoves.add(spot3);
					}
				}
			}
			//spot4 is down1, right 2
			if(rank < 6 && file != 7){
				if(file+1<=7 && rank+2 <=7) {
					if(board[file+1][rank+2].getPiece()==null || (board[file+1][rank+2].getPiece()!=null&&board[file+1][rank+2].getPiece().getColor()=="b" )){
						spot4 = getmoveString(file+1,rank+2);
						posmoves.add(spot4);
					}
				}
			}
			//spot 5 is down2, right 1
			if(rank!=7 && file <6){
				if(file+2<=7 && rank+1<=7) {
					if(board[file+2][rank+1].getPiece() == null ||( board[file+2][rank+1].getPiece()!=null&&board[file+2][rank+1].getPiece().getColor()== "b")){
						spot5 = getmoveString(file+2,rank+1);
						posmoves.add(spot5);
					}
				}
			}
			//spot6 is down2, left 1
			if(rank!=0 && file <6){
				if(file+2<=7 && rank-1>=0) {
					if(board[file+2][rank-1].getPiece()==null){
						spot6 = getmoveString(file+2,rank-1);
						posmoves.add(spot6);
					}
				}else {
					if(file+2<=7 && rank+1<=7) {
						if(board[file+2][rank+1].getPiece()!=null && board[file+2][rank+1].getPiece().getColor() == "b"){
							spot6 = getmoveString(file+2,rank-1);
							posmoves.add(spot6);
						}
					}
				}
			}
			//spot7 is down1, left 2
			if(rank > 1 && file!=7){
				if(file+1<=7 && rank-2 >=0) {
					if(board[file+1][rank-2].getPiece()==null || (board[file+1][rank-2].getPiece()!=null&&board[file+1][rank-2].getPiece().getColor()=="b")){
						spot7 = getmoveString(file+1, rank-2);
						posmoves.add(spot7);
					}
				}
			}
			//spot 8 up1, left 2
			if(rank >1 && file !=0){
				if(file-1>=0 && rank-2>=0) {
					if(board[file-1][rank-2].getPiece() == null || (board[file-1][rank-2].getPiece()!=null&&board[file-1][rank-2].getPiece().getColor() == "b")){
						spot8 = getmoveString(file-1,rank-2);
						posmoves.add(spot8);
					}
				}
			}
		}
		else { //piece color is black 
			//spot1 is up 2, left 1
			if (rank != 0 && file > 1){
				if(file-2>=0 && rank-1>=0) {
					if(board[file-2][rank-1].getPiece()== null || (board[file-2][rank-1].getPiece()!=null&& board[file-2][rank-1].getPiece().getColor()=="w")){
						spot1 = getmoveString(file-2, rank-1);
						posmoves.add(spot1);
					}
				}
			}//spot2 is up 2, right 1
			if(rank !=7 && file > 1){
				if(file-2>=0 && rank+1<=7) {
					if(board[file-2][rank+1].getPiece()==null || (board[file-2][rank+1].getPiece()!=null&&board[file-2][rank+1].getPiece().getColor() == "w")){
						spot2 = getmoveString(file-2, rank+1);
						posmoves.add(spot2);
					}
				}
			}
			//spot3 is up1, right 2
			if(rank < 6 && file !=0){
				if(file-1>=0 && rank+2<=7) {
					if(board[file-1][rank+2].getPiece() == null || (board[file-1][rank+2].getPiece()!=null&&board[file-1][rank+2].getPiece().getColor()=="w")){
						spot3 = getmoveString(file-1,rank+2);
						posmoves.add(spot3);
					}
				}
			}
			//spot4 is down1, right 2
			if(rank < 6 && file != 7){
				if(file+1<=7 && rank+2<=7) {
					if(board[file+1][rank+2].getPiece()==null || (board[file+1][rank+2].getPiece()!=null&&board[file+1][rank+2].getPiece().getColor()=="w") ){
						spot4 = getmoveString(file+1,rank+2);
						posmoves.add(spot4);
					}
				}
			}
			//spot 5 is down2, right 1
			if(rank!=7 && file <6){
				if(file+2<=7 && rank+1<=7) {
					if(board[file+2][rank+1].getPiece() == null || (board[file+2][rank+1].getPiece()!=null&&board[file+2][rank+1].getPiece().getColor()== "w")){
						spot5 = getmoveString(file+2,rank+1);
						posmoves.add(spot5);
					}
				}
			}
			//spot6 is down2, left 1
			if(rank!=0 && file <6){
				if(file+2<=7 && rank-1>=0) {
					if(board[file+2][rank-1].getPiece()==null || (board[file+2][rank+1].getPiece()!=null&&board[file+2][rank+1].getPiece().getColor() == "w")){
						spot6 = getmoveString(file+2,rank-1);
						posmoves.add(spot6);
					}
				}
			}
			//spot7 is down1, left 2
			if(rank > 1 && file!=7){
				if(file+1<=7 && rank-2>=0) {
					if(board[file+1][rank-2].getPiece()==null || (board[file+1][rank-2].getPiece()!=null&&board[file+1][rank-2].getPiece().getColor()=="w")){
						spot7 = getmoveString(file+1, rank-2);
						posmoves.add(spot7);
					}
				}
			}
			//spot 8 up1, left 2
			if(rank >1 && file !=0){
				if(file-1>=0 && rank-2>=0) {
					if(board[file-1][rank-2].getPiece() == null || (board[file-1][rank-2].getPiece()!=null&&board[file-1][rank-2].getPiece().getColor() == "w")){
						spot8 = getmoveString(file-1,rank-2);
						posmoves.add(spot8);
					}
				}
			}
		}
		
		return posmoves; 
	}
}
