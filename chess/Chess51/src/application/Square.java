package application;
/**
 * set the each square
 * @author Yelin Shin,Claudia Pan
 */
public class Square {
	private Piece piece = null;
	private String sqColor;
	
	/**
	 * default constructor for Square
	 * 
	 */
	public Square() {	}
	
	/**
	 * set position for piece
	 * 
	 * @param row row in the board
	 * @param col column in the board
	 */
	public void setPos (int row, int col) {
		if (piece !=null) {
			this.piece.rank = row;
			this.piece.file = col;
		}
	}
	
		/**
		 * set the piece in that position  
		 * 
		 * @param piece chess piece
		 */
	public void setPiece (Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * get piece at that square
	 * 
	 * @return piece chess piece 
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * get piece at that square
	 * 
	 * @param sqColor color of the square
	 */
	public void setSqColor (String sqColor) {
		this.sqColor = sqColor;
	}
	
	
	/**
	 * setting the board as original chess board
	 * 
	 * @return String with specific color (black/white)
	 */
	public String getSquare () {
		if (piece == null) { // no piece is there, give color of sq
			if(sqColor == "w") {
				return "   ";
			}else {
				return "## ";
			}
		} else { //there is piece in the sq
			return piece.color + piece.getType() + " ";
		}
	}
}
