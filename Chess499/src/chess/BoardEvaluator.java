package chess;

public class BoardEvaluator {

	private static BoardEvaluator boardEvaluator;
	
	private BoardEvaluator() {
		
	}
	
	public static BoardEvaluator getInstance() {
		if (boardEvaluator == null) {
			boardEvaluator = new BoardEvaluator();
		}
		return boardEvaluator;
	}
	
	
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
