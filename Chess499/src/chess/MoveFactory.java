package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class MoveFactory {

	private static MoveFactory moveFactory;
	
	/**
	 * 
	 */
	private MoveFactory() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static MoveFactory getInstance() {
		if (moveFactory == null) {
			moveFactory = new MoveFactory();
		}
		return moveFactory;
	}
	
	/**
	 * 
	 * @param sourceSquare
	 * @param targetSquare
	 * @param chessBoard
	 * @param opponentsLastMove
	 * @return
	 */
	public Move createMove(int sourceSquare, int targetSquare, ChessBoard chessBoard, Move opponentsLastMove) {
		
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(sourceSquare);
		
		if (movingPiece == null) {
			return null;
		}
		
		ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(targetSquare);
		Move moveAttempt = null;
		
		if (movingPiece instanceof Pawn) {
			// Pawn is trying to reach the end of the board (PawnPromotion)
			if (targetSquare / 8 == movingPiece.getColor().getPerspectiveRow(7)) {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, true, false, false);
			} else if(opponentsLastMove != null && opponentsLastMove.getMovingPiece() instanceof Pawn  
					&& targetSquare == opponentsLastMove.getSource() - (8 * movingPiece.getColor().getBoardPerspective())
					&& opponentsLastMove.getDestination() == opponentsLastMove.getSource() - (16 * movingPiece.getColor().getBoardPerspective())) {
				// En Passant
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, chessBoard.getPieceOnSquare(targetSquare - (8 * movingPiece.getColor().getBoardPerspective())), false, true, false);
			} else {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false, false, false);
			}
		} else if (movingPiece instanceof King) {
			if (sourceSquare == movingPiece.getColor().getStartingKingPosition() && (targetSquare == movingPiece.getColor().getStartingKingPosition() + 2 || targetSquare == movingPiece.getColor().getStartingKingPosition() - 2)) {
				// attempting to castle
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, null, false, false, true);
			} else {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false, false, false);
			}
		} else {
			// movingPiece is an instance of Queen, Bishop, Rook or Knight (no special moves)
			moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false, false, false);
		} 	
		return moveAttempt;
	}
	
}
