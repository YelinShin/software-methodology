package application;
import java.util.ArrayList;

/**
 *implementing functional board
 *@author Yelin Shin,Claudia Pan
 */
public class Board {
	
	public static Square [][] board = new Square [8][8];
	
	String BKpos = "e8";
	String WKpos = "e1";
	

	private static boolean BKcastling = false; 
	private static boolean WKcastling = false; 
	private static boolean BPenpass = false;
	private static boolean WPenpass = false;
	
	
	boolean isChecked = false;
	boolean OppIsChecked = false;
	boolean onCheckmate = false;
	Piece checkedBy;
	int check_count;
	Piece wenpass_pawn;
	int wenpass_rank;
	int wenpass_file;
	Piece benpass_pawn;
	int benpass_rank;
	int benpass_file;
	
	/**
	 * generate board and set every pieces in original position 
	 * 
	 */
	public Board(){
		//i- length of board
		//j - width of board
		for(int i = 0; i< 8; i++){
			for(int j = 0; j< 8; j++){
				Square sq = new Square();
				//black initial position for rank 8
				if(i == 0){
					//rook in file a and h
					if(j == 0 || j == 7){
						Piece rook = new Rook("b");
						sq.setPiece(rook);
						sq.setPos(j, i);
						board[i][j] = sq;
					//knight in file b and g
					}else if(j ==1 || j ==6){
						Piece knight = new Knight("b");
						sq.setPiece(knight);
						sq.setPos(j, i);
						board[i][j] = sq;
					//bishop in file c and f
					}else if (j == 2 || j == 5){
						Piece bishop = new Bishop("b");
						sq.setPiece(bishop);
						sq.setPos(j, i);
						board[i][j] = sq;
					//queen in file d
					}else if (j == 3){
						Piece queen = new Queen("b");
						sq.setPiece(queen);
						sq.setPos(j, i);
						board[i][j] = sq;
					//king in file e	
					}else if(j == 4){
						Piece king = new King("b");
						sq.setPiece(king);
						sq.setPos(j, i);
						board[i][j] = sq;
					}
				//rank 1, row of black pawns	
				}else if (i == 1){
					Piece pawn = new Pawn("b");
					sq.setPiece(pawn);
					sq.setPos(j, i);
					board[i][j] = sq;
				//rank 6, row of white pawns
				}else if (i == 6){
					Piece pawn = new Pawn("w");
					sq.setPiece(pawn);
					sq.setPos(j, i);
					board[i][j]= sq;
				//initial position of rank 1, white pieces
				}else if (i == 7){
					//rook in file a and h
					if(j == 0 || j == 7){
						Piece rook = new Rook("w");
						sq.setPiece(rook);
						sq.setPos(j, i);
						board[i][j] = sq;
					//knight in file b and g
					}else if(j ==1 || j ==6){
						Piece knight = new Knight("w");
						sq.setPiece(knight);
						sq.setPos(j, i);
						board[i][j] = sq;
					//bishop in file c and f
					}else if (j == 2 || j == 5){
						Piece bishop = new Bishop("w");
						sq.setPiece(bishop);
						sq.setPos(j, i);
						board[i][j] = sq;
						//queen in file d
					}else if (j == 3){
						Piece queen = new Queen("w");
						sq.setPiece(queen);
						sq.setPos(j, i);
						board[i][j] = sq;
						//king in file e	
					}else if(j == 4){
						Piece king = new King("w");
						sq.setPiece(king);
						sq.setPos(j, i);
						board[i][j] = sq;
					}
				//even rank	
				}if(i % 2 == 0){
					//even rank ,even files set to white
					if(j% 2 == 0){
						sq.setSqColor("w");
						board[i][j] = sq;
						continue;
					//even rank, odd files set to black
					}else if (j %2 == 1){
						sq.setSqColor("b");
						board[i][j] = sq;
						continue;
					}
				//odd rank
				}if(i % 2 == 1){
					//odd rank, even files set to black
					if(j%2 == 0){
						sq.setSqColor("b");
						board[i][j] = sq;
						continue;
					//odd rank, odd files set to white 
					}else if(j % 2 == 1){
						sq.setSqColor("w");
						board[i][j] = sq;
						continue;
					}
				}
			}
		}	
	}
	
	
	/**
	 * determine the move is valid or not
	 * @param moveFrom start position of piece
	 * @param moveTo end position of piece
	 * @param whiteTurn it is true or not
	 * @param promoType for promotion
	 * @param count for checking enpassant
	 * @return move valid or not
	 */
	public boolean validMove(String moveFrom, String moveTo, boolean whiteTurn, String promoType, int count){
		
		int rankFrom= Math.abs(Character.getNumericValue(moveFrom.charAt(1))-8);
		int fileFrom = Character.getNumericValue(moveFrom.charAt(0))-10;
		int rankTo= Math.abs(Character.getNumericValue(moveTo.charAt(1))-8);
		int fileTo = Character.getNumericValue(moveTo.charAt(0))-10;
		
		//check from to is vaild position
		if (rankFrom <0 || rankFrom>7 || fileFrom <0 || fileFrom>7 || rankTo <0 || rankTo>7 || fileTo <0 || fileTo>7 ) {
			return false;
		}
		
		boolean Kmoved = false;
		ArrayList<String> posmoves = new ArrayList<String>();
		
		Piece fromPiece = pieceFrom(moveFrom);
		Piece toPiece = pieceFrom(moveTo);
		
		if(fromPiece!=null) {
			if(whiteTurn) {// should move white pieces
				if(fromPiece.getColor() == "b") {
					board[rankFrom][fileFrom].setPiece(fromPiece);
					return false;
				}
			}else {//should move black pieces
				if(fromPiece.getColor() == "w") {
					board[rankFrom][fileFrom].setPiece(fromPiece);
					return false;
				}
			}
		}
		
		if(fromPiece == null){// user from input is empty (no piece to move)
			return false;
		}
		
		
		posmoves = fromPiece.movePiece(board); 
		
		
		 
		if (fromPiece.getType()=="K") {
			if (toPiece==null) {
				Piece tmp1 = new Pawn("w");
				tmp1.rank =rankTo;
				tmp1.file=fileTo;
				board[rankTo][fileTo].setPiece(tmp1);
				isChecked = underchecked(rankTo, fileTo, whiteTurn);
				board[rankTo][fileTo].setPiece(null);
			} else {
				isChecked = underchecked(rankTo, fileTo, whiteTurn);
			}
		}
		
		if(isChecked) { //king is under checked / check flag is set
			//dont return 
			return false; 
		}else{
			//castling
			/*once a game, allowed once for each King
			 * king and rook need to be never moved during the game
			 * no pieces between king and rook 
			 * king is not in check and pieceTo must not cause king to be in check
			 */
			if(fromPiece.getType()=="K"){
				if(fromPiece.getMoved() == false ){
				//white king at e1 goes to c1
				
					if(WKcastling == false && fromPiece.getColor()=="w" && moveFrom.equals("e1") && moveTo.equals("c1") && whiteTurn == true){
						//check the pieces between them 7,1 7,2 7,3
						if(board[7][0].getPiece().getType() == "R" && board[7][0].getPiece().getMoved() == false
								&& board[7][1].getPiece() == null && board[7][2].getPiece() == null && board[7][3].getPiece() == null
								&& underchecked(7,4, true) == false && underchecked (7,2, true) == false && underchecked(7,3, true) == false ){
									Piece Rook = board[7][0].getPiece();
									board[rankTo][fileTo].setPiece(fromPiece);
									board[rankTo][fileTo].setPos(fileTo, rankTo);
									board[7][3].setPiece(Rook);
									board[7][3].setPos(3, 7);
									board[7][0].setPiece(null);
									WKcastling = true; 
									fromPiece.moved = true;
									Rook.moved = true;
									oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
									if(OppIsChecked) {
										onCheckmate = isCheckmate(whiteTurn);
									}
									return true;
						}
						
					//white king at e1 goes to g1
					}else if(WKcastling == false && fromPiece.getColor()=="w" && moveFrom.equals("e1") && moveTo.equals("g1") && whiteTurn == true){	
						//check the pieces between them 7,5 7,6 
						if(board[7][7].getPiece().getType() == "R" && board[7][7].getPiece().getMoved() == false
							&& board[7][5].getPiece() == null && board[7][6].getPiece() == null 
							&& underchecked(7,4, true) == false && underchecked (7,6, true) == false && underchecked(7,5, true) == false ){
								Piece Rook = board[7][7].getPiece();
								board[rankTo][fileTo].setPiece(fromPiece);
								board[rankTo][fileTo].setPos(fileTo, rankTo);
								board[7][5].setPiece(Rook);
								board[7][5].setPos(5, 7);
								board[7][7].setPiece(null);
								
								WKcastling = true; 
								fromPiece.moved = true;
								Rook.moved = true;
								oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
								if(OppIsChecked) {
									onCheckmate = isCheckmate(whiteTurn);
								}
								return true;
						}
					//black king at e8 goes to c8	
					}else if(BKcastling == false && fromPiece.getColor()=="b" && moveFrom.equals("e8") && moveTo.equals("c8")&& whiteTurn == false){
						if(board[0][0].getPiece().getType() == "R" && board[0][0].getPiece().getMoved() == false
							&& board[0][1].getPiece() == null && board[0][2].getPiece() == null && board[0][3].getPiece() == null
							&& underchecked(0,4, false) == false && underchecked (0,2, false) == false && underchecked(0,3, false) == false ){
								Piece Rook = board[0][0].getPiece();
								board[rankTo][fileTo].setPiece(fromPiece);
								board[rankTo][fileTo].setPos(fileTo, rankTo);
								board[0][3].setPiece(Rook);
								board[0][3].setPos(3, 0);
								board[0][0].setPiece(null);

								BKcastling = true; 
								fromPiece.moved = true;
								Rook.moved = true;
								oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
								if(OppIsChecked) {
									onCheckmate = isCheckmate(whiteTurn);
								}
								return true;
						}
					//black king at e8 goes to g8
					}else if(BKcastling == false && fromPiece.getColor()=="b" && moveFrom.equals("e8") && moveTo.equals("g8")&& whiteTurn == false){
							if(board[0][7].getPiece().getType() == "R" && board[0][7].getPiece().getMoved() == false
									&& board[0][5].getPiece() == null && board[0][6].getPiece() == null 
									&& underchecked(0,4, false) == false && underchecked (0,6, false) == false && underchecked(0,5, false) == false ){
									Piece Rook = board[0][7].getPiece();

									board[rankTo][fileTo].setPiece(fromPiece);
									board[rankTo][fileTo].setPos(fileTo, rankTo);

									board[0][5].setPiece(Rook);
									board[0][5].setPos(5, 0);
									board[0][7].setPiece(null);
									
									BKcastling = true; 
									fromPiece.moved = true;

									Rook.moved = true;
									oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
									if(OppIsChecked) {
										onCheckmate = isCheckmate(whiteTurn);
									}
									return true;
							}
					} else if (posmoves.contains(moveTo)) {
						if(toPiece != null) {
							if(whiteTurn==true) {
								if(toPiece.getColor() == "b") {
									toPiece = null;
								}
							}else { // black turn
								if(toPiece.getColor() == "w") {
									toPiece = null;
								}
							}
						}
						
						board[rankTo][fileTo].setPiece(fromPiece);
						board[rankTo][fileTo].setPos(fileTo, rankTo);
						fromPiece.moved = true;
						oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
						if(OppIsChecked) {
							onCheckmate = isCheckmate(whiteTurn);
						}
						return true;
					}
					
				}else if(posmoves.contains(moveTo)){
					
					if(toPiece != null) {
						if(whiteTurn==true) {
							if(toPiece.getColor() == "b") {
								toPiece = null;
							}
						}else { // black turn
							if(toPiece.getColor() == "w") {
								toPiece = null;
							}
						}
					}
					
					board[rankTo][fileTo].setPiece(fromPiece);
					board[rankTo][fileTo].setPos(fileTo, rankTo);
					fromPiece.moved = true;
					oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
					if(OppIsChecked) {
						onCheckmate = isCheckmate(whiteTurn);
					}
					return true;
				}
				
			}
		}
		
		
		
		//Pawn Promotion, white pawn must be moving from rank 7 to 8
		// black pawn must be moving from rank 2 to 1
		//else just check if pieceTo is a valid move and return true
		if(fromPiece.getType() == "p"){
			if(posmoves.contains(moveTo)){
				if (count != check_count+1) {
					WPenpass=false;
					BPenpass=false;
				}
				if((rankFrom == 1 && fromPiece.getColor() == "w" && whiteTurn == true && rankTo == 0 )|| (rankFrom == 6 && fromPiece.getColor()=="b" && whiteTurn == false && rankTo==7)){
						if (promoType == null) {
							promoType = "Q";
						}
						switch(promoType) {
						case "R":
							fromPiece = new Rook (fromPiece.getColor());
							board[rankTo][fileTo].setPiece(fromPiece);
							board[rankTo][fileTo].setPos(fileTo, rankTo);
							fromPiece.moved = true;
							oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
							if(OppIsChecked) {
								onCheckmate = isCheckmate(whiteTurn);
							}
							return true;
						
						case "B":
							fromPiece = new Bishop (fromPiece.getColor());
							board[rankTo][fileTo].setPiece(fromPiece);
							board[rankTo][fileTo].setPos(fileTo, rankTo);
							fromPiece.moved = true;
							oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
							if(OppIsChecked) {
								onCheckmate = isCheckmate(whiteTurn);
							}
							return true;
						case "N":
							fromPiece = new Knight (fromPiece.getColor());
							board[rankTo][fileTo].setPiece(fromPiece);
							board[rankTo][fileTo].setPos(fileTo, rankTo);
							fromPiece.moved = true;
							oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
							if(OppIsChecked) {
								onCheckmate = isCheckmate(whiteTurn);
							}
							return true;
						case "Q":
							fromPiece = new Queen (fromPiece.getColor());
							board[rankTo][fileTo].setPiece(fromPiece);
							board[rankTo][fileTo].setPos(fileTo, rankTo);
							fromPiece.moved = true;
							oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
							if(OppIsChecked) {
								onCheckmate = isCheckmate(whiteTurn);
							}
							return true;
						default:
							return false;
						}
					
				}else {
					// we have to implement first enpassant allowed check in here
					
					if (rankFrom - rankTo == 2 || rankFrom-rankTo ==-2 ) { //first move and move 2 up or down
						if(fromPiece.getColor()=="w" && fromPiece.getMoved() == false) {
							WPenpass=true;
							check_count = count;
							wenpass_pawn = fromPiece;
							wenpass_rank = rankTo;
							wenpass_file = fileTo;
						} else if(fromPiece.getColor()=="w" && fromPiece.getMoved() == true) {
							WPenpass=false;
						} else if(fromPiece.getColor()=="b" && fromPiece.getMoved() == false) {
							BPenpass=true;
							check_count = count;
							benpass_pawn = fromPiece;
							benpass_rank = rankTo;
							benpass_file = fileTo;
						} else {
							BPenpass=false;
						}
					}
					
					if(toPiece != null) {
						if(whiteTurn==true) {
							if(toPiece.getColor() == "b") {
								toPiece = null;
							}
						}else { // black turn
							if(toPiece.getColor() == "w") {
								toPiece = null;
							}
						}
					}
					
					board[rankTo][fileTo].setPiece(fromPiece);
					board[rankTo][fileTo].setPos(fileTo, rankTo);
					fromPiece.moved = true;
					oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
					if(OppIsChecked) {
						onCheckmate = isCheckmate(whiteTurn);
					}
					return true;
				}
			}
		}
		
		
		//check consecutive enpassant
		if(!whiteTurn && WPenpass == true) {//black turn
			if(count == check_count+1) {//consecutive turn
				if(rankFrom == wenpass_rank && rankTo == wenpass_rank +1 && fileFrom -1 == wenpass_file) {
					//enpassant to down-left position
					
					board[rankTo][fileTo].setPiece(fromPiece);
					board[rankTo][fileTo].setPos(fileTo, rankTo);
					fromPiece.moved = true;
					
					//kill white pawn who move 2 position
					board[rankFrom][fileTo].setPiece(null);
					WPenpass = false;
					oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
					if(OppIsChecked) {
						onCheckmate = isCheckmate(whiteTurn);
					}
					return true;
					
				}else if(rankFrom == wenpass_rank && rankTo == wenpass_rank +1 && fileFrom +1 == wenpass_file) {
					//enpassant to down-right position
					board[rankTo][fileTo].setPiece(fromPiece);
					board[rankTo][fileTo].setPos(fileTo, rankTo);
					fromPiece.moved = true;
					
					//kill white pawn who move 2 position
					board[rankFrom][fileTo].setPiece(null);
					WPenpass = false;
					oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
					if(OppIsChecked) {
						onCheckmate = isCheckmate(whiteTurn);
					}
					return true;
				}
			}
		}
		
		if(whiteTurn && BPenpass == true) {//white turn
			if(count == check_count+1) {//consecutive turn
				if(rankFrom == benpass_rank && rankTo == benpass_rank -1 && fileFrom -1 == benpass_file) {
					//enpassant to up-left position
					
					board[rankTo][fileTo].setPiece(fromPiece);
					board[rankTo][fileTo].setPos(fileTo, rankTo);
					fromPiece.moved = true;
					
					//kill white pawn who move 2 position
					board[rankFrom][fileTo].setPiece(null);
					BPenpass = false;
					oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
					if(OppIsChecked) {
						onCheckmate = isCheckmate(whiteTurn);
					}
					return true;
					
				}else if(rankFrom == benpass_rank && rankTo == benpass_rank -1 && fileFrom +1 == benpass_file) {
					//enpassant to down-right position
					board[rankTo][fileTo].setPiece(fromPiece);
					board[rankTo][fileTo].setPos(fileTo, rankTo);
					fromPiece.moved = true;
					
					//kill white pawn who move 2 position
					board[rankFrom][fileTo].setPiece(null);
					BPenpass = false;
					oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
					if(OppIsChecked) {
						onCheckmate = isCheckmate(whiteTurn);
					}
					return true;
				}
			}
		}
		
		if(fromPiece.getType() == "R" || fromPiece.getType() == "B" || fromPiece.getType() == "N" || fromPiece.getType() == "Q") {
		
			if(posmoves.contains(moveTo)==true) {
				if(toPiece != null) {
					if(whiteTurn==true) {
						if(toPiece.getColor() == "b") {
							toPiece = null;
						}
					}else { // black turn
						if(toPiece.getColor() == "w") {
							toPiece = null;
						}
					}
				}
				board[rankTo][fileTo].setPiece(fromPiece);
				board[rankTo][fileTo].setPos(fileTo, rankTo);
				
				
				
				fromPiece.moved = true;
				oppInCheck(toPiece,fromPiece, rankTo,fileTo,whiteTurn);
				if(OppIsChecked) {
					onCheckmate = isCheckmate(whiteTurn);
				}
				return true;
			}
		}
		

		//remove the following line
		return false;
	}
	
	
	/**
	 * Determines piece from input position
	 * @param position that you are wondering
	 * @return piece that sitting there
	 */
	public Piece pieceFrom(String position) {
		Piece tmp = null;
		
		int rank= Math.abs(Character.getNumericValue(position.charAt(1))-8);
		int file = Character.getNumericValue(position.charAt(0))-10;
		
		tmp = board[rank][file].getPiece();
		//board[rank][file].setPiece(null);//set piece position to 2nd position in vaildMove method
		
		return tmp;
	}
	
	/**
	 * Determines king die
	 * @param whiteTurn is true on white turn 
	 * @return if opponent king die, return true
	 */
	public boolean isKingDie (boolean whiteTurn) {
		
		if (!whiteTurn) { //(this method is used after Chess.java change the whiteTurn...) check black king alive
			for (int row=7; row>-1; row--){ 
			    for(int col=0; col<8; col++){ 
			    	if(board[row][col].getPiece()!=null) {
			    		if(board[row][col].getPiece().getColor()=="b" && board[row][col].getPiece().getType()=="K") {
			    			return false;
			    		}
			    	}
			    }
			}
		} else {
			for (int row=7; row>-1; row--){ 
			    for(int col=0; col<8; col++){ 
			    	if(board[row][col].getPiece()!=null) {
			    		if(board[row][col].getPiece().getColor()=="w" && board[row][col].getPiece().getType()=="K") {
			    			return false;
			    		}
			    	}
			    }
			}
		}
		
		
		return true;
	}
	
	/**
	 * Determines checkmate
	 * @param whiteTurn is true on white turn 
	 * @return check mate is possible than true
	 */
	public boolean isCheckmate (boolean whiteTurn) {
		
		ArrayList<String> bkposmoves = null;
		ArrayList<String> wkposmoves = null;
		int rank, file;
		boolean pos_avoid; // possible to avoid -> false = can avoid checkmate
		
		if(whiteTurn) {// find black king's posmove -> one of position has to be not underchecked
			 for (int row=7; row>-1; row--){ 
				    for(int col=0; col<8; col++){ 
				    	if(board[row][col].getPiece()!=null) {
				    		if(board[row][col].getPiece().getColor()=="b" && board[row][col].getPiece().getType()=="K" ) {
				    			bkposmoves = board[row][col].getPiece().movePiece(board);
				    		}
				    	}
				    }
			 }
			 
			 for(int j = 0; j< bkposmoves.size(); j++) {
				rank= Math.abs(Character.getNumericValue(bkposmoves.get(j).charAt(1))-8);
   			  	file = Character.getNumericValue(bkposmoves.get(j).charAt(0))-10;
				pos_avoid = underchecked_checkmate(rank, file, whiteTurn);
				if(pos_avoid==false) {
					return false;
				}
			 }
			 
		} else { // black turn, so find white king's posmove
			for (int row=7; row>-1; row--){ 
			    for(int col=0; col<8; col++){ 
			    	if(board[row][col].getPiece()!=null) {
			    		if(board[row][col].getPiece().getColor()=="w" && board[row][col].getPiece().getType()=="K" ) {
			    			wkposmoves = board[row][col].getPiece().movePiece(board);
			    		}
			    	}
			    }
			}
			
			for(int j = 0; j< wkposmoves.size(); j++) {
				rank= Math.abs(Character.getNumericValue(wkposmoves.get(j).charAt(1))-8);
   			  	file = Character.getNumericValue(wkposmoves.get(j).charAt(0))-10;
				pos_avoid = underchecked_checkmate(rank, file, whiteTurn);
				if(pos_avoid==false) {
					return false;
				}
			 }
		}
		
		
		return true;
	}
	
	/**
	 * Determines the position can be under checked
	 * @param RankTo for rank
	 * @param FileTo for file
	 * @param whiteTurn or not
	 * @return boolean if it is under checked position
	 */
	public boolean underchecked (int RankTo, int FileTo, boolean whiteTurn){// check this position can attack by opponent piece


		  ArrayList<String> posmoves1 = null;
		  int rank1;
		  int file1;
		  
		  Piece tmp;
		  
		  if(board[RankTo][FileTo].getPiece() == null) {
			  if (whiteTurn == false) {
				  tmp = new Pawn("b");
			  }else {
				  tmp = new Pawn("w");
			  }
			  board[RankTo][FileTo].setPiece(tmp);
		  
		  
		  
			  //get all the pieces 
			  for (int row=7; row>-1; row--){ 
			    for(int col=0; col<8; col++){ 
			    	if(board[row][col].getPiece()!=null) {
			    	  if(whiteTurn == false) { // black turn, need to check any white piece can attack black king
			    		  if(board[row][col].getPiece().getColor()=="w") {
			    			  
				    		  posmoves1 = board[row][col].getPiece().movePiece(board);
				    		  
				    		  for(int j = 0; j< posmoves1.size(); j++) {
				    			  rank1= Math.abs(Character.getNumericValue(posmoves1.get(j).charAt(1))-8);
				    			  file1 = Character.getNumericValue(posmoves1.get(j).charAt(0))-10;
				    			  
				    			  if(rank1 == RankTo && file1 == FileTo) {
				    				  board[RankTo][FileTo].setPiece(null);
									  return true; // black king is under attack
								  }
				    		  }
							  
			    		  }
			    	  } else { // white turn, need to check any black piece can attack white king
			    		  posmoves1 = board[row][col].getPiece().movePiece(board);
			    		  
			    		  for(int j = 0; j< posmoves1.size(); j++) {
			    			  rank1= Math.abs(Character.getNumericValue(posmoves1.get(j).charAt(1))-8);
			    			  file1 = Character.getNumericValue(posmoves1.get(j).charAt(0))-10;
			    			  
			    			  if(rank1 == RankTo && file1 == FileTo) {
			    				  board[RankTo][FileTo].setPiece(null);
								  return true; // white king is under attack
							  }
			    		  }
						  
		    		  }
			    	  
			    	}      
			    }
			    
			  } // end for loop
			  
			   board[RankTo][FileTo].setPiece(null);
			   return false;
		  } // if pieceTo is null end 
		  else { // if pieceTo is not null (= king is going to kill opponent pieces, check destination position is underchecked
			  for (int row=7; row>-1; row--){ 
				    for(int col=0; col<8; col++){ 
				    	if(board[row][col].getPiece()!=null) {
				    	  if(whiteTurn == false) { // black turn, need to check any white piece can attack black king
				    		  if(board[row][col].getPiece().getColor()=="w") {
					    		  //Wpieces.add(board[row][col].getPiece());
					    		  posmoves1 = board[row][col].getPiece().movePiece(board);
					    		  
					    		  for(int j = 0; j< posmoves1.size(); j++) {
					    			  rank1= Math.abs(Character.getNumericValue(posmoves1.get(j).charAt(1))-8);
					    			  file1 = Character.getNumericValue(posmoves1.get(j).charAt(0))-10;
					    			  
					    			  if(rank1 == RankTo && file1 == FileTo) {
					    				  //board[RankTo][FileTo].setPiece(null);
										  return true; // black king is under attack
									  }
					    		  }
								  
				    		  }
				    	  } else { // white turn, need to check any black piece can attack white king
				    		  posmoves1 = board[row][col].getPiece().movePiece(board);
				    		  
				    		  for(int j = 0; j< posmoves1.size(); j++) {
				    			  rank1= Math.abs(Character.getNumericValue(posmoves1.get(j).charAt(1))-8);
				    			  file1 = Character.getNumericValue(posmoves1.get(j).charAt(0))-10;
				    			  
				    			  if(rank1 == RankTo && file1 == FileTo) {
				    				  //board[RankTo][FileTo].setPiece(null);
									  return true; // white king is under attack
								  }
				    		  }
							  
			    		  }
				    	  
				    	}      
				    }
				    
				  } // end for loop
				  
				   //board[RankTo][FileTo].setPiece(null);
				   return false;
		  }
		  
		  
		  
		
	}
	
	/**
	 * Determines the position can be under checked for helping checkmate function
	 * @param RankTo for rank number
	 * @param FileTo for file number
	 * @param whiteTurn or not
	 * @return boolean if it is under checked position for helping checkmate function
	 */
	public boolean underchecked_checkmate (int RankTo, int FileTo, boolean whiteTurn){// check this position can attack by opponent piece


		  ArrayList<String> posmoves1 = null;
		  int rank1;
		  int file1;
		  
		  Piece tmp;
		  
		  if(board[RankTo][FileTo].getPiece() == null) {
			  if (whiteTurn == false) {
				  tmp = new Pawn("w");
			  }else {
				  tmp = new Pawn("b");
			  }
			  board[RankTo][FileTo].setPiece(tmp);
		  
		  
		  
			  //get all the pieces 
			  for (int row=7; row>-1; row--){ 
			    for(int col=0; col<8; col++){ 
			    	if(board[row][col].getPiece()!=null) {
			    	  if(whiteTurn == false) { // black turn, need to check the position is underattack by black piece
			    		  if(board[row][col].getPiece().getColor()=="b") {
			    			  
				    		  posmoves1 = board[row][col].getPiece().movePiece(board);
				    		  
				    		  for(int j = 0; j< posmoves1.size(); j++) {
				    			  rank1= Math.abs(Character.getNumericValue(posmoves1.get(j).charAt(1))-8);
				    			  file1 = Character.getNumericValue(posmoves1.get(j).charAt(0))-10;
				    			  
				    			  if(rank1 == RankTo && file1 == FileTo) {
				    				  board[RankTo][FileTo].setPiece(null);
									  return true; // black king is under attack
								  }
				    		  }
							  
			    		  }
			    	  } else { // white turn, need to check the position is underattack by white piece
			    		  if(board[row][col].getPiece().getColor()=="w") {
				    		  posmoves1 = board[row][col].getPiece().movePiece(board);
				    		  
				    		  for(int j = 0; j< posmoves1.size(); j++) {
				    			  rank1= Math.abs(Character.getNumericValue(posmoves1.get(j).charAt(1))-8);
				    			  file1 = Character.getNumericValue(posmoves1.get(j).charAt(0))-10;
				    			  
				    			  if(rank1 == RankTo && file1 == FileTo) {
				    				  board[RankTo][FileTo].setPiece(null);
									  return true; // white king is under attack
								  }
				    		  }
			    		  }
		    		  }
			    	  
			    	}      
			    }
			    
			  } // end for loop
			  
			   board[RankTo][FileTo].setPiece(null);
			   
		  } 
		  return false;
	}
	/**
	 * Determines opponent is in check 
	 * @param toPiece for wanted position piece
	 * @param fromPiece for piece you want to move
	 * @param rankTo for wanted position rank
	 * @param fileTo for wanted position file
	 * @param whiteTurn or not
	 *
	 */
	public void oppInCheck (Piece toPiece, Piece fromPiece, int rankTo, int fileTo, boolean whiteTurn) {
		
		ArrayList<String> OppPosmoves = board[rankTo][fileTo].getPiece().movePiece(board);
		

		// if white turn is true - white turn
		for(int i = 0; i< OppPosmoves.size(); i++) {
			String attackedPiece = OppPosmoves.get(i);
			int rank= Math.abs(Character.getNumericValue(attackedPiece.charAt(1))-8);
			int file = Character.getNumericValue(attackedPiece.charAt(0))-10;
			
			// find this attacked piece is king so we can set king is in check position
			if(board[rank][file].getPiece()!=null) {
				if (board[rank][file].getPiece().type == "K") {
					if (whiteTurn == true && board[rank][file].getPiece().color == "b") {
						// white turn, and ToPosition make black king under check
						BKpos = OppPosmoves.get(i);
						OppIsChecked = true;
						checkedBy = toPiece;
					} else if (whiteTurn == false && board[rank][file].getPiece().color == "w") {
						//then white king is under attack
						WKpos = OppPosmoves.get(i);
						OppIsChecked = true;
						checkedBy = toPiece;
					}
				}
			}
		}
		
		
	}
	
	/**
	 * Check there is checkmate on this board
	 * @return boolean variable of it have checkmate or not
	 *
	 */
	public boolean getOnCheckmate() {
		return this.onCheckmate;
	}
	
	/**
	 * printing out the our playing board
	 * 
	 */
	public void getBoard() {
		char[] file = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		char[] rank = {'8', '7', '6', '5', '4', '3', '2', '1'};
		
		//print colored board and rank
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				System.out.print(board[i][j].getSquare());
				if(j == 7) {
					System.out.print(" " + rank[i]+"\n");
				}
			}
		}
		
		//print file at the bottom of board
		for(int i = 0; i<7; i++) {
			System.out.print(" "+file[i]+" ");
		}
		System.out.println(" "+file[7]+"\n");
		
		if(onCheckmate == false && OppIsChecked==true) {
			System.out.println("Check\n");
			OppIsChecked = false;
		}
	}

}
