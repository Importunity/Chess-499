package chess;

import java.util.ArrayList;
import java.util.Iterator;

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
	 * @param squareNumber
	 * @return
	 */
	public boolean hasAvailableMove(int squareNumber) {
		ChessPiece pieceAttemptingToMove = chessBoard.getPieceOnSquare(squareNumber);
		if (pieceAttemptingToMove == null) {
			return false;
		}
		if (availableMoves.hasAvailable(pieceAttemptingToMove)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param sourceSquare
	 * @return
	 */
	public ArrayList<Integer> getAvailableTargetSquares(int sourceSquare) {
		ChessPiece pieceAttemptingToMove = chessBoard.getPieceOnSquare(sourceSquare);
		ArrayList<Integer> targetSquares = new ArrayList<Integer>();
		if (pieceAttemptingToMove == null) {
			return targetSquares;
		}
		ArrayList<Move> movesAvailable = availableMoves.getMovesForPiece(pieceAttemptingToMove);
		for(Move move: movesAvailable) {
			targetSquares.add(move.getDestination());
		}
		return targetSquares;
	}
	
	/**
	 * This method accepts a source square and a target square which represents the move a user wants to make.
	 * 	It will make the move if the move is a valid move in the context of the current game.  
	 * 
	 * @param sourceSquare
	 * @param targetSquare
	 * @return	true or false, move made or move unable to be made
	 */
	public boolean makeMove(int sourceSquare, int targetSquare) {
		
		// create Move object
		Move move = MoveFactory.getInstance().createMove(sourceSquare, targetSquare, chessBoard, moveHistory.getLastMoveMade());
		if (move == null) {
			// sourceSquare didn't even have a piece on it
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
				chessBoard.placePieceOnSquare(null, sourceSquare);
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				// castling KingSide
				if (move.isCastling() && targetSquare > sourceSquare) {
					chessBoard.placePieceOnSquare(chessBoard.getPieceOnSquare(targetSquare + 1), sourceSquare + 1);
					chessBoard.placePieceOnSquare(null, targetSquare + 1);
				} 
				// castling QueenSide
				else if (move.isCastling()){
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
	 */
	public boolean undoMove() {
		Move moveToUndo = moveHistory.getLastMoveMade();
		if (moveToUndo == null) {
			return false;
		}
		ChessPiece movedPiece = moveToUndo.getMovingPiece();
		ChessPiece capturedPiece = moveToUndo.getCapturedPiece();
		int previousSourceSquare = moveToUndo.getSource();
		int previousDestinationSquare = moveToUndo.getDestination();
		
		if (movedPiece instanceof Pawn) {
			chessBoard.placePieceOnSquare(null, previousDestinationSquare);
			chessBoard.placePieceOnSquare(movedPiece, previousSourceSquare);
			if(moveToUndo.isEnPassant()) {
				chessBoard.placePieceOnSquare(capturedPiece, previousDestinationSquare - (8 * movedPiece.getColor().getBoardPerspective()));
			} else if(capturedPiece != null) {
				chessBoard.placePieceOnSquare(capturedPiece, previousDestinationSquare);
			}
			moveHistory.undoMove();
			movesMade--;
		} else if(movedPiece instanceof King) {
			chessBoard.placePieceOnSquare(capturedPiece, previousDestinationSquare);
			chessBoard.placePieceOnSquare(movedPiece, previousSourceSquare);
			// undoing a KingSide castle
			if (moveToUndo.isCastling() && previousDestinationSquare > previousSourceSquare) {
				chessBoard.placePieceOnSquare(chessBoard.getPieceOnSquare(previousDestinationSquare - 1), previousDestinationSquare + 1);
				chessBoard.placePieceOnSquare(null, previousDestinationSquare - 1);
			} 
			// undoing a QueenSide castle
			else if(moveToUndo.isCastling()) { 
				chessBoard.placePieceOnSquare(chessBoard.getPieceOnSquare(previousDestinationSquare + 1), previousDestinationSquare - 2);
				chessBoard.placePieceOnSquare(null, previousDestinationSquare + 1);
			}
			moveHistory.undoMove();
			movesMade--;
		} else {
			chessBoard.placePieceOnSquare(capturedPiece, previousDestinationSquare);
			chessBoard.placePieceOnSquare(movedPiece, previousSourceSquare);
			moveHistory.undoMove();
			movesMade--;
		}
		return true;
	}
	
	/**
	 * 
	 */
	public boolean redoMove() {
		Move moveToRedo = moveHistory.getLastMoveUndone();
		if (moveToRedo == null) {
			return false;
		}
		
		ChessPiece movingPiece = moveToRedo.getMovingPiece();
		int sourceSquare = moveToRedo.getSource();
		int targetSquare = moveToRedo.getDestination();
		
		if(movingPiece instanceof Pawn) {
			chessBoard.placePieceOnSquare(null, sourceSquare);
			if (moveToRedo.isPawnPromotion()) {
				chessBoard.placePieceOnSquare(new Queen(movingPiece.getColor(), true), targetSquare);
			} else if(moveToRedo.isEnPassant()){
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
				chessBoard.placePieceOnSquare(null, targetSquare - (8 * movingPiece.getColor().getBoardPerspective()));
			} else {
				chessBoard.placePieceOnSquare(movingPiece, targetSquare);
			}
			moveHistory.redoMove();
			movesMade++;
		} else if (movingPiece instanceof King) {
			chessBoard.placePieceOnSquare(null, sourceSquare);
			chessBoard.placePieceOnSquare(movingPiece, targetSquare);
			// castling KingSide
			if (moveToRedo.isCastling() && targetSquare > sourceSquare) {
				chessBoard.placePieceOnSquare(chessBoard.getPieceOnSquare(targetSquare + 1), sourceSquare + 1);
				chessBoard.placePieceOnSquare(null, targetSquare + 1);
			} 
			// castling QueenSide
			else if (moveToRedo.isCastling()){
				chessBoard.placePieceOnSquare(chessBoard.getPieceOnSquare(targetSquare - 2), sourceSquare - 1);
				chessBoard.placePieceOnSquare(null, targetSquare - 2);
			}
			moveHistory.redoMove();
			movesMade++;
		} else {
			chessBoard.placePieceOnSquare(movingPiece, targetSquare);
			chessBoard.placePieceOnSquare(null, sourceSquare);
			moveHistory.redoMove();
			movesMade++;
		}
		return true;
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
	public boolean isKingInCheck(Color player) {
		return GameRules.getInstance().isKingInCheck(player, chessBoard);
	}
	
	/**
	 * This method must be called called prior to each move.  
	 * It updates the available moves for the player and will also detect if the game is over.
	 * 
	 * @param player
	 * @return
	 */
	public boolean isCheckmateOrStalemate(Color player) {

		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();
		ArrayList<Move> possibleMovesForPiece;
		
		// For each square of chessboard
		for (int i = 0; i < 64; i++) {
			
			ChessPiece piece = chessBoard.getPieceOnSquare(i);
			if (piece != null) {
				if(piece.getColor() == player) {
					if (piece instanceof Pawn) {
						possibleMovesForPiece = GameRules.getInstance().getPossiblePawnMoves(i, chessBoard, moveHistory.getLastMoveMade());
						availableMoves.updateAvailableMoves(piece, possibleMovesForPiece);
						allPossibleMoves.addAll(possibleMovesForPiece);
					}
					else if(piece instanceof Knight) {
						possibleMovesForPiece = GameRules.getInstance().getPossibleKnightMoves(i, chessBoard);
						availableMoves.updateAvailableMoves(piece, possibleMovesForPiece);
						allPossibleMoves.addAll(possibleMovesForPiece);
					}
					else if(piece instanceof Bishop || piece instanceof Rook || piece instanceof Queen) {
						possibleMovesForPiece = GameRules.getInstance().getPossibleQRBMoves(i, chessBoard);
						availableMoves.updateAvailableMoves(piece, possibleMovesForPiece);
						allPossibleMoves.addAll(possibleMovesForPiece);
					}
					else if(piece instanceof King) {
						possibleMovesForPiece = GameRules.getInstance().getPossibleKingMoves(i, chessBoard);
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
		// IF YOU WANT TO PRINT OUT ALL AVAILABLE MOVES USE THIS CODE
		else {
			int row = 0;
			String output = "";
			Iterator<Move> it = allPossibleMoves.iterator();
			while (it.hasNext()) {
				if(row % 3 == 0){
					output += "\n";
				}
				output += "[ " + it.next() + " ]";
				row++;
			}
			output += "\n";
			System.out.println(output);
		}
		
		return false;
	}
	
}
