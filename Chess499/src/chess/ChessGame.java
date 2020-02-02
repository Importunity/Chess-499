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
	
	public ChessGame() {
		chessBoard = new ChessBoard();
		initializeChessBoard();
		moveHistory = new MoveHistory();
		movesMade = 0;
	}
	
	private void initializeChessBoard() {
		for (int i = 0; i < 16; i++) {
			// place rooks
			if (i == 0 || i == 7) {
				// 0, 7, 56, 63 (i and 63 - i) 
				chessBoard.placePieceOnSquare(new Rook(Color.WHITE), i);
				chessBoard.placePieceOnSquare(new Rook(Color.BLACK), 63 - i);
			}
			// place knights
			else if (i == 1 || i == 6) {
				// 1, 6, 57, 62 (i and 63 - i)
				chessBoard.placePieceOnSquare(new Knight(Color.WHITE), i);
				chessBoard.placePieceOnSquare(new Knight(Color.BLACK), 63 - i);
			}
			//place bishops
			else if (i == 2 || i == 5) {
				// 2, 5, 58, 61 (i and 63 - i)
				chessBoard.placePieceOnSquare(new Bishop(Color.WHITE), i);
				chessBoard.placePieceOnSquare(new Bishop(Color.BLACK), 63 - i);
			}
			// place white queen and black king
			else if (i == 3) {
				chessBoard.placePieceOnSquare(new Queen(Color.WHITE), i);
				chessBoard.placePieceOnSquare(new King(Color.BLACK), 63 - i);
			}
			// place white king and black queen
			else if (i == 4) {
				chessBoard.placePieceOnSquare(new King(Color.WHITE), i);
				chessBoard.placePieceOnSquare(new Queen(Color.BLACK), 63 - i);
			}
			// place pawns
			else {
				// 8 - 15, 48 - 55 (i and 63 - i)
				chessBoard.placePieceOnSquare(new Pawn(Color.WHITE), i);
				chessBoard.placePieceOnSquare(new Pawn(Color.BLACK), 63 - i);
			}
			
		}
	}
	
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	
	public boolean makeMove(int sourceSquare, int targetSquare) {
		
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(sourceSquare);
		if (movingPiece == null) {
			return false;
		}
		if (movingPiece.getColor().ordinal() != movesMade % 2) {
			return false;
		}
		ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(targetSquare);
		Move moveAttempt;
		if (movingPiece instanceof Pawn) {
			if (targetSquare / 8 == movingPiece.getColor().getLastRow()) {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, true);
			} else {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false);
			}
			ArrayList<Move> possibleMoves = GameRules.getPossiblePawnMoves(sourceSquare, chessBoard);
			if(possibleMoves.contains(moveAttempt)) {
				chessBoard.placePieceOnSquare(null, sourceSquare);
				if (moveAttempt.isPawnPromotion()) {
					chessBoard.placePieceOnSquare(new Queen(movingPiece.getColor()), targetSquare);
				} else {
					chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				}
				moveHistory.addMove(moveAttempt);
				movesMade++;
				return true;
			}
		} else if (movingPiece instanceof Knight) {
			moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false);
			ArrayList<Move> possibleMoves = GameRules.getPossibleKnightMoves(sourceSquare, chessBoard);
			if(possibleMoves.contains(moveAttempt)) {
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				chessBoard.placePieceOnSquare(null, sourceSquare);
				moveHistory.addMove(moveAttempt);
				movesMade++;
				return true;
			}
		} else if (movingPiece instanceof Bishop || movingPiece instanceof Rook || movingPiece instanceof Queen) {
			moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false);
			ArrayList<Move> possibleMoves = GameRules.getPossibleQRBMoves(sourceSquare, chessBoard);
			if(possibleMoves.contains(moveAttempt)) {
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				chessBoard.placePieceOnSquare(null, sourceSquare);
				moveHistory.addMove(moveAttempt);
				movesMade++;
				return true;
			}
		} else if (movingPiece instanceof King){
			moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false);
			ArrayList<Move> possibleMoves = GameRules.getPossibleKingMoves(sourceSquare, chessBoard);
			if(possibleMoves.contains(moveAttempt)) {
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				chessBoard.placePieceOnSquare(null, sourceSquare);
				moveHistory.addMove(moveAttempt);
				movesMade++;
				return true;
			}
		} else {
			return false;
		}	
		return false;
	}

	/**
	 * This method may be deleted later on. I am only using it to test.
	 * @return
	 */
	public String lastMove() {
		return moveHistory.getLastMoveMade().toString();
	}
	
	public boolean kingInCheck(Color player) {
		return GameRules.kingInCheck(player, chessBoard);
	}
	
	public boolean isCheckmateOrStalemate(Color player) {
		return GameRules.isCheckmateOrStalemate(player, chessBoard);
	}
	
}
