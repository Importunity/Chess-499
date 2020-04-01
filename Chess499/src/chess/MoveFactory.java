package chess;

import java.util.ArrayList;

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
	
	/**
	 * 
	 * @param move
	 * @param chessBoard
	 * @param commonMoves
	 * @param checkMate
	 */
	public void notateMove(Move move, ChessBoard chessBoard, ArrayList<Move> commonMoves, boolean checkMate) {
		String notation = "";
		ChessPiece movingPiece = move.getMovingPiece();
		String destinationRank = String.valueOf(move.getDestination() / 8 + 1);
		String destinationFile = String.valueOf((char) (move.getDestination() % 8 + 97));
		String sourceFile = String.valueOf((char) (move.getSource() % 8 + 97));
		
		if (movingPiece instanceof Pawn) {
			
			notation += move.getCapturedPiece() == null? destinationFile + destinationRank: sourceFile + "x" + destinationFile + destinationRank;
			notation += move.isPawnPromotion() ? "=Q":"";
			
		} else if(movingPiece instanceof King){
			
			if (move.isCastling()) {
				if (move.getDestination() > move.getSource()) {
					notation += "O-O";
				} else {
					notation += "O-O-O";
				}
			} else {
				notation += "K";
				notation += move.getCapturedPiece() == null? destinationFile + destinationRank: "x" + destinationFile + destinationRank;
			}
		} else {
			
			notation += movingPiece.getNotation();
			String sourceRank = String.valueOf(move.getSource() / 8 + 1);
			boolean commonRank = false;
			boolean commonFile = false;
			if (!commonMoves.isEmpty()) {
				
				for(Move commonMove: commonMoves) {
					String sourceFileForCommonMove = String.valueOf( (char) (commonMove.getSource() % 8 + 97));
					if (sourceFile.contentEquals(sourceFileForCommonMove)) {
						commonFile = true;
					}
					
					String sourceRankForCommonMove = String.valueOf(commonMove.getSource() / 8 + 1);
					if (sourceRank.equals(sourceRankForCommonMove)) {
						commonRank = true;
					}
				}
				if (commonRank || commonFile) {
					notation += commonRank? sourceFile: "";
					notation += commonFile? sourceRank: "";
				} else {
					notation += sourceFile;
				}
				
				
			} 
			notation += move.getCapturedPiece() == null? destinationFile + destinationRank: "x" + destinationFile + destinationRank;
		}
		
		// check for check or checkmate
		if (GameRules.getInstance().isKingInCheck(movingPiece.getColor() == Color.BLACK? Color.WHITE:Color.BLACK, chessBoard)) {
			
			if (checkMate) {
				notation += "#";
			}else {
				notation += "+";
			}
		}
		move.setNotation(notation);
	}
}
