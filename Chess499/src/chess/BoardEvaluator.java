package chess;
/**
 * This class is a singleton and is used to evaluate the board.  
 * 
 * @author Luke Newman 2020
 *
 */
public class BoardEvaluator {

	private static BoardEvaluator boardEvaluator;
	
	/**
	 * private constructor
	 */
	private BoardEvaluator() {
		
	}
	
	/**
	 * 
	 * @return 		- the one and only instance of this class
	 */
	public static BoardEvaluator getInstance() {
		if (boardEvaluator == null) {
			boardEvaluator = new BoardEvaluator();
		}
		return boardEvaluator;
	}
	
	/**
	 * Right now all this method does is count up the value of the remaining pieces 
	 * 	on the board. We might get rid of Move as a parameter as well. The methods inside 
	 * 	the if blocks are future methods to be implemented. 
	 * 
	 * @param chessBoard
	 * @param move
	 * @return 		- a score of the board
	 */
	public int evaluate(ChessBoard chessBoard, Move move) {
		int score = 0;
		
		for (int i = 0; i < 64; i++) {
			ChessPiece chessPiece = chessBoard.getPieceOnSquare(i);
			if (chessPiece != null) {
				score += chessPiece.getValue();
				if (chessPiece instanceof Pawn) {
					//evalPawn()
				}
				else if(chessPiece instanceof Knight){
					//evalKnight()
				}
				else if (chessPiece instanceof Bishop) {
					//evalBishop()
				}
				else if (chessPiece instanceof Rook) {
					//evalRook()
				}
				else if (chessPiece instanceof Queen) {
					//evalQueen()
				}
				else {
					//evalKing()
				}
			}
		}
		
		return score;
	}
}
