package application;
import java.util.Scanner;

/**
 *main class for the chess game
 *@author Yelin Shin,Claudia Pan
 */
public class Chess {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		boolean whiteTurn = true;
		Board board = new Board();
		
		String move;
		String[] splitMove;
		String sign,moveFrom, moveTo, draw;
		
		boolean onGame=true;
		boolean drawOn=false;
		
		int enpass_count=0;
		int rankTo, fileTo, rankFrom, fileFrom;
		
		board.getBoard();
		
		while(onGame) {
			
			while(onGame == true && whiteTurn == true) {
				System.out.print("White's move: ");
				move = scan.nextLine();
				splitMove = move.split(" ");
				
				System.out.println("");
				
				
				if(splitMove.length == 1) {//flag of sign
					sign = splitMove[0];
					
					if(sign.equals("resign")){ //white turn + resign -> black win
						onGame = false;
						System.out.println("Black wins");
						return;
					}else if(drawOn) {
						if(sign.equals("draw")) {
							onGame = false;
							System.out.println("The game is a draw.");
							return;
							//break;
						}
					} else {
						System.out.println("Illegal move, try again.");
						System.out.println("");
						break;
					}
				}else if(splitMove.length == 2) {// proper moving (no sign)
					moveFrom = splitMove[0];
					moveTo = splitMove[1];
					
					if(moveFrom.length()!=2 ||moveTo.length()!=2) {
						System.out.println("Illegal move, try again.");
						System.out.println("");
						break;
					}
					
					boolean test;
					test = board.validMove(moveFrom, moveTo, whiteTurn, null,enpass_count);
					
					if (test==true) {
						whiteTurn = false; // now is black turn
						
						Piece tmp = null;
						rankTo= Math.abs(Character.getNumericValue(moveTo.charAt(1))-8);
						fileTo = Character.getNumericValue(moveTo.charAt(0))-10;
						rankFrom= Math.abs(Character.getNumericValue(moveFrom.charAt(1))-8);
						fileFrom = Character.getNumericValue(moveFrom.charAt(0))-10;
						
						tmp = Board.board[rankTo][fileTo].getPiece();
						Board.board[rankFrom][fileFrom].setPiece(null);
						tmp.moved = true;
						
					} else {
						System.out.println("Illegal move, try again.");
						System.out.println("");
						break;
					}
				} else if(splitMove.length == 3) { // draw? ask
					moveFrom = splitMove[0];
					moveTo = splitMove[1];
					draw = splitMove[2];
					
					if(draw.equals("draw?")){
						if (board.validMove(moveFrom, moveTo, whiteTurn,null,enpass_count)) {
							whiteTurn = false;
							drawOn = true;
							rankFrom= Math.abs(Character.getNumericValue(moveFrom.charAt(1))-8);
							fileFrom = Character.getNumericValue(moveFrom.charAt(0))-10;
							Board.board[rankFrom][fileFrom].setPiece(null);
						} else {
							System.out.println("Illegal move, try again.");
							System.out.println("");
							break;
						}
					} else if(draw.matches("R|B|N|Q")) {
						if(board.validMove(moveFrom, moveTo, whiteTurn, draw,enpass_count)){
							whiteTurn = false;
							drawOn = true;
							rankFrom= Math.abs(Character.getNumericValue(moveFrom.charAt(1))-8);
							fileFrom = Character.getNumericValue(moveFrom.charAt(0))-10;
							Board.board[rankFrom][fileFrom].setPiece(null);
						} else {
							System.out.println("Illegal move, try again.");
							System.out.println("");
							break;
						}
					}else {
						System.out.println("Illegal move, try again.");
						System.out.println("");
						break;
					}
				}
				
				if(board.getOnCheckmate()== true) {
					enpass_count++;
					board.getBoard();
					System.out.println("Checkmate\n");
					if(whiteTurn) { //true = white die
						System.out.println("Black wins");
						return;
					}else { //whiteTurn==false (right after white moves the piece) 
						System.out.println("White wins");
						return;
					}
				}
				
				if(board.isKingDie(whiteTurn) == true) {
					if(whiteTurn) { 
						System.out.println("Black wins");
						return;
					}else { //black king die by white piece
						System.out.println("White wins");
						return;
					}
				}
				
				if(onGame) {
					enpass_count++;
					board.getBoard();
									
				}
			}// while white turn
			
			while(onGame == true && whiteTurn == false) {
				System.out.print("Black's move: ");
				move = scan.nextLine();
				splitMove = move.split(" ");
				
				System.out.println("");
				
				if(splitMove.length == 1) {//flag of sign
					sign = splitMove[0];
					
					if(sign.equals("resign")){ //black turn + resign -> white win
						onGame = false;
						System.out.println("White wins");
						return;
					}else if(drawOn) {
						if(sign.equals("draw")) {
							onGame = false;
							System.out.println("The game is a draw.");
							return;
							//break;
						}
					} else {
			            System.out.println("Illegal move, try again.");
			            System.out.println("");
			            break;
			        }
				}else if(splitMove.length == 2) {// proper moving (no sign)
					moveFrom = splitMove[0];
					moveTo = splitMove[1];
					
					if(moveFrom.length()!=2 ||moveTo.length()!=2) {
						System.out.println("Illegal move, try again.");
						System.out.println("");
						break;
					}
					
					if (board.validMove(moveFrom, moveTo, whiteTurn, null,enpass_count)) {
						whiteTurn = true; // now is black turn
						Piece tmp = null;
						rankTo= Math.abs(Character.getNumericValue(moveTo.charAt(1))-8);
						fileTo = Character.getNumericValue(moveTo.charAt(0))-10;
						rankFrom= Math.abs(Character.getNumericValue(moveFrom.charAt(1))-8);
						fileFrom = Character.getNumericValue(moveFrom.charAt(0))-10;
						
						tmp = Board.board[rankTo][fileTo].getPiece();
						Board.board[rankFrom][fileFrom].setPiece(null);
						tmp.moved = true;
					} else {
						System.out.println("Illegal move, try again.");
						System.out.println("");
						break;
					}
				} else if(splitMove.length == 3) { // draw? ask
					moveFrom = splitMove[0];
					moveTo = splitMove[1];
					draw = splitMove[2];
					
					if(draw.equals("draw?")){
						if (board.validMove(moveFrom, moveTo, whiteTurn,null,enpass_count)) {
							whiteTurn = false;
							drawOn = true;
							rankFrom= Math.abs(Character.getNumericValue(moveFrom.charAt(1))-8);
							fileFrom = Character.getNumericValue(moveFrom.charAt(0))-10;
							Board.board[rankFrom][fileFrom].setPiece(null);
						} else {
							System.out.println("Illegal move, try again.");
							System.out.println("");
							break;
						}
					} else if(draw.matches("R|B|N|Q")) {
						if(board.validMove(moveFrom, moveTo, whiteTurn, draw,enpass_count)){
							whiteTurn = false;
							drawOn = true;
							rankFrom= Math.abs(Character.getNumericValue(moveFrom.charAt(1))-8);
							fileFrom = Character.getNumericValue(moveFrom.charAt(0))-10;
							Board.board[rankFrom][fileFrom].setPiece(null);
							break;
						} else {
							System.out.println("Illegal move, try again.");
							System.out.println("");
							break;
						}
					}else {
						System.out.println("Illegal move, try again.");
						System.out.println("");
						break;
					}
				}
				
				if(board.getOnCheckmate()== true) {
					enpass_count++;
					board.getBoard();
				
					System.out.println("Checkmate\n");
					if(whiteTurn) { //true = white die
						System.out.println("Black wins");
						return;
					}else { //whiteTurn==false (right after white moves the piece) 
						System.out.println("White wins");
						return;
					}
				}
				
				if(board.isKingDie(whiteTurn) == true) {
					if(whiteTurn) { 
						System.out.println("Black wins");
						return;
					}else { //black king die by white piece
						System.out.println("White wins");
						return;
					}
				}
				
				
				if(onGame) {
					enpass_count++;
					board.getBoard();
					
				}
				
			}//while black turn

		}//while on game

		scan.close();
	}
}
