package chess;

import java.util.ArrayList;

/**
 * 
 * @author Luke Newman
 *
 */
public class ChessGame {
	
	private ChessBoard chessBoard;
	private int movesMade;
	private MoveHistory moveHistory;
	private AvailableMoves availableMoves;
	
	/**
	 * 
	 */
	public ChessGame() {
		chessBoard = new ChessBoard();
		chessBoard.initialize();
		moveHistory = new MoveHistory();
		availableMoves = new AvailableMoves();
		movesMade = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMovesMade() {
		return movesMade;
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	
	/**
	 * 
	 * @param sourceSquare
	 * @param targetSquare
	 * @return
	 */
	public boolean makeMove(int sourceSquare, int targetSquare) {
		
		Move move = MoveFactory.getInstance().createMove(sourceSquare, targetSquare, this);
		if (move == null) {
			return false;
		}
		
		if (availableMoves.isAvailable(move)) {
			ChessPiece movingPiece = move.getMovingPiece();
			if(movingPiece instanceof Pawn) {
				chessBoard.placePieceOnSquare(null, sourceSquare);
				if (move.isPawnPromotion()) {
					chessBoard.placePieceOnSquare(new Queen(movingPiece.getColor(), true), targetSquare);
				} else if(move.isEnPassant()){
					chessBoard.placePieceOnSquare(movingPiece, targetSquare);
					chessBoard.placePieceOnSquare(null, targetSquare - (8 * movingPiece.getColor().getBoardPerspective()));
				} else {
					chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				}
				moveHistory.addMove(move);
				movesMade++;
				return true;
			} else if (movingPiece instanceof King) {
				
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				chessBoard.placePieceOnSquare(null, sourceSquare);
				if (move.isCastling() && targetSquare > sourceSquare) {
					chessBoard.placePieceOnSquare(chessBoard.getPieceOnSquare(targetSquare + 1), sourceSquare + 1);
					chessBoard.placePieceOnSquare(null, targetSquare + 1);
				} else if (move.isCastling()){
					chessBoard.placePieceOnSquare(chessBoard.getPieceOnSquare(targetSquare - 2), sourceSquare - 1);
					chessBoard.placePieceOnSquare(null, targetSquare - 2);
				}
				moveHistory.addMove(move);
				movesMade++;
				return true;
			} else {
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				chessBoard.placePieceOnSquare(null, sourceSquare);
				moveHistory.addMove(move);
				movesMade++;
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public String lastMove() {
		return moveHistory.getLastMoveMade().toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public MoveHistory getMoveHistory() {
		return moveHistory;
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean kingInCheck(Color player) {
		return GameRules.kingInCheck(player, chessBoard);
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean isCheckmateOrStalemate(Color player) {

		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();
		ArrayList<Move> possibleMovesForPiece;
		for (int i = 0; i < 64; i++) {
			ChessPiece piece = chessBoard.getPieceOnSquare(i);
			if (piece != null) {
				if(piece.getColor() == player) {
					if (piece instanceof Pawn) {
						possibleMovesForPiece = GameRules.getPossiblePawnMoves(i, this);
						availableMoves.updateAvailableMoves(piece, possibleMovesForPiece);
						allPossibleMoves.addAll(possibleMovesForPiece);
					}
					else if(piece instanceof Knight) {
						possibleMovesForPiece = GameRules.getPossibleKnightMoves(i, this);
						availableMoves.updateAvailableMoves(piece, possibleMovesForPiece);
						allPossibleMoves.addAll(possibleMovesForPiece);
					}
					else if(piece instanceof Bishop || piece instanceof Rook || piece instanceof Queen) {
						possibleMovesForPiece = GameRules.getPossibleQRBMoves(i, this);
						availableMoves.updateAvailableMoves(piece, possibleMovesForPiece);
						allPossibleMoves.addAll(possibleMovesForPiece);
					}
					else if(piece instanceof King) {
						possibleMovesForPiece = GameRules.getPossibleKingMoves(i, this);
						availableMoves.updateAvailableMoves(piece, possibleMovesForPiece);
						allPossibleMoves.addAll(possibleMovesForPiece);
					}
				} else {
					// it is not the other players turn so we clear all available moves for their pieces
					availableMoves.clearAvailableMovesForPiece(piece);
				}
			}
		}
		if (allPossibleMoves.isEmpty()) {
			return true;
		}
		return false;
	}
	
}
