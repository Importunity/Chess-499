package chess;

import java.util.ArrayList;
/**
 * 
 * @author Luke Newman
 *
 */
public class GameRules {
	
	/**
	 * 
	 * @param sourceSquare
	 * @param targetSquare
	 * @param chessGame
	 * @return
	 */
	public static Move validateMove(int sourceSquare, int targetSquare, ChessGame chessGame) {
		
		ChessBoard chessBoard = chessGame.getChessBoard();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(sourceSquare);
		
		if (movingPiece == null) {
			return null;
		}
		
		if (chessBoard.getPieceOnSquare(sourceSquare).getColor().ordinal() != chessGame.getMovesMade() % 2) {
			return null;
		} 
		
		ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(targetSquare);
		Move moveAttempt;
		Move opponentsLastMove;
		if (movingPiece instanceof Pawn) {
			// Pawn is trying to reach the end of the board (PawnPromotion)
			if (targetSquare / 8 == movingPiece.getColor().getPerspectiveRow(7)) {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, true, false, false);
			} else if((opponentsLastMove = chessGame.getMoveHistory().getLastMoveMade()) != null && opponentsLastMove.getMovingPiece() instanceof Pawn  
					&& targetSquare == opponentsLastMove.getSource() - (8 * movingPiece.getColor().getBoardPerspective())
					&& opponentsLastMove.getDestination() == opponentsLastMove.getSource() - (16 * movingPiece.getColor().getBoardPerspective())) {
				// En Passant
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, chessBoard.getPieceOnSquare(targetSquare - (8 * movingPiece.getColor().getBoardPerspective())), false, true, false);
			} else {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false, false, false);
			}
			// Later I will create a RemainingPieces class that will be updated with available moves for each piece
			ArrayList<Move> possibleMoves = getPossiblePawnMoves(sourceSquare, chessGame);
			if(possibleMoves.contains(moveAttempt)) {
				return moveAttempt;
			}
		} else if (movingPiece instanceof Knight) {
			moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false, false, false);
			ArrayList<Move> possibleMoves = getPossibleKnightMoves(sourceSquare, chessGame);
			if(possibleMoves.contains(moveAttempt)) {
				return moveAttempt;
			}
		} else if (movingPiece instanceof Bishop || movingPiece instanceof Rook || movingPiece instanceof Queen) {
			moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false, false, false);
			ArrayList<Move> possibleMoves = getPossibleQRBMoves(sourceSquare, chessGame);
			if(possibleMoves.contains(moveAttempt)) {
				return moveAttempt;
			}
		} else { // movingPiece is a King
			if (sourceSquare == movingPiece.getColor().getStartingKingPosition() && (targetSquare == movingPiece.getColor().getStartingKingPosition() + 2 || targetSquare == movingPiece.getColor().getStartingKingPosition() - 2)) {
				// attempting to castle
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, null, false, false, true);
			} else {
				moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece, false, false, false);
			}
			ArrayList<Move> possibleMoves = getPossibleKingMoves(sourceSquare, chessGame);
			if(possibleMoves.contains(moveAttempt)) {
				return moveAttempt;
			}
		} 	
		return null;
	}
	
	/**
	 * 
	 * @param currentSquare
	 * @param chessGame
	 * @return
	 */
	public static ArrayList<Move> getPossibleQRBMoves(int currentSquare, ChessGame chessGame){
		
		ChessBoard chessBoard = chessGame.getChessBoard();
		int originalSquare = currentSquare;
		int destinationSquare;
		Move possibleMove;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
	
		int startingDirection = 0;
		int incrementValue = 1;
		if(movingPiece instanceof Queen) {
			// keep startingDirection and incrementValue the same
		} else if (movingPiece instanceof Rook){
			// 
			incrementValue = 2;
		} else if (movingPiece instanceof Bishop) {
			//
			startingDirection = 1;
			incrementValue = 2;
		} else {
			return possibleMoves;
		}
	
		// for each direction (8 total directions)
		for (int i = startingDirection; i < 8; i = i+incrementValue) {
		
			while((destinationSquare = AdjacentSquares.get(currentSquare, i)) != -1 && 
					chessBoard.getPieceOnSquare(destinationSquare) == null) {
				
				if(!abandoningKing(originalSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
					possibleMove = new Move(originalSquare, destinationSquare, movingPiece, null, false, false, false);
					possibleMoves.add(possibleMove);
				}
				
				// advance to the next square in same direction
				currentSquare = AdjacentSquares.get(currentSquare, i);
			}
				
			// if it is not the end of the board there is either a black or white piece there
			if(destinationSquare != -1) {
			
				if(chessBoard.getPieceOnSquare(destinationSquare).getColor() != movingPiece.getColor()) {
					// possible capture
					ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i));
					if(!abandoningKing(originalSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
						possibleMove = new Move(originalSquare, destinationSquare, movingPiece, possibleCapturedPiece, false, false, false);
						possibleMoves.add(possibleMove);
					}
					
				}
				
			}
			
			currentSquare = originalSquare;
			
		}
	
		return possibleMoves;
	}
	
	/**
	 * 
	 * @param currentSquare
	 * @param chessGame
	 * @return
	 */
	public static ArrayList<Move> getPossibleKnightMoves(int currentSquare, ChessGame chessGame){
		
		ChessBoard chessBoard = chessGame.getChessBoard();
		Move possibleMove;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
		int destinationSquare;
		
		for(int i = 0; i < 8; i++) {
		
			try{
				if ((destinationSquare = AdjacentSquares.get(AdjacentSquares.get(currentSquare, i), (i + 1)%8)) != -1) {
					ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
					if (possibleCapturedPiece == null) {
						if(!abandoningKing(currentSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
							possibleMove = new Move(currentSquare, destinationSquare, movingPiece, null, false, false, false);
							possibleMoves.add(possibleMove);
						}
						
					} else if (possibleCapturedPiece.getColor() != movingPiece.getColor()) {
						if(!abandoningKing(currentSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
							possibleMove = new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece, false, false, false);
							possibleMoves.add(possibleMove);
						}
						
					}
				}
			} catch (IndexOutOfBoundsException ex) {
				// out of range off the board
			}
		
		}
	
		return possibleMoves;
	}
	
	/**
	 * 
	 * @param currentSquare
	 * @param chessGame
	 * @return
	 */
	public static ArrayList<Move> getPossibleKingMoves(int currentSquare, ChessGame chessGame){
		
		ChessBoard chessBoard = chessGame.getChessBoard();
		Move possibleMove;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
	
		for (int i = 0; i < 8; i++) {
			int destinationSquare = AdjacentSquares.get(currentSquare, i);
			
			if (destinationSquare != -1 && kingInBorderingSquare(destinationSquare, i, chessBoard) == false) {
				ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
				if(possibleCapturedPiece == null) {
					if (!abandoningKing(currentSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
						possibleMove = new Move(currentSquare, destinationSquare, movingPiece, null, false, false, false);
						possibleMoves.add(possibleMove);
					}
				} else if(possibleCapturedPiece.getColor() != movingPiece.getColor()){
					if (!abandoningKing(currentSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
						possibleMove = new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece, false, false, false);
						possibleMoves.add(possibleMove);
					}
				}
			}
		
		}
		ChessPiece kingSideRook;
		ChessPiece queenSideRook;
		int startingKingPosition = movingPiece.getColor().getStartingKingPosition();
		if(currentSquare == startingKingPosition) {
			if ( movingPiece instanceof King && ((King) movingPiece).getMotioned() == 0) {
				
				if (!kingInCheck(movingPiece.getColor(), chessBoard)) {
					// KingSide Castling
					if (chessBoard.getPieceOnSquare(startingKingPosition + 1) == null && chessBoard.getPieceOnSquare(startingKingPosition + 2) == null) {
						if ((kingSideRook = chessBoard.getPieceOnSquare(startingKingPosition + 3)) instanceof Rook) {
							if ( ((Rook) kingSideRook).getMotioned() == 0) {
								
								chessBoard.placePieceOnSquare(null, startingKingPosition);
								chessBoard.placePieceOnSquare(movingPiece, startingKingPosition + 1);
								if (!kingInCheck(movingPiece.getColor(), chessBoard) && !kingInBorderingSquare(startingKingPosition + 1, 2, chessBoard)) {
									chessBoard.placePieceOnSquare(null, startingKingPosition + 1);
									chessBoard.placePieceOnSquare(movingPiece, startingKingPosition + 2);
									if(!kingInCheck(movingPiece.getColor(), chessBoard) && !kingInBorderingSquare(startingKingPosition + 2, 2, chessBoard)) {
										Move castleKingSideMove = new Move(startingKingPosition, startingKingPosition + 2, movingPiece, null, false, false, true);
										possibleMoves.add(castleKingSideMove);
									}
									chessBoard.placePieceOnSquare(null, startingKingPosition + 2);
									chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
								}
								chessBoard.placePieceOnSquare(null, startingKingPosition + 1);
								chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
							}
						}
					}
					// QueenSide Castling
					if (chessBoard.getPieceOnSquare(startingKingPosition - 1) == null && chessBoard.getPieceOnSquare(startingKingPosition - 2) == null && chessBoard.getPieceOnSquare(startingKingPosition - 3) == null ) {
						if((queenSideRook = chessBoard.getPieceOnSquare(startingKingPosition - 4)) instanceof Rook) {
							if (((Rook) queenSideRook).getMotioned() == 0) {
								chessBoard.placePieceOnSquare(null, startingKingPosition);
								chessBoard.placePieceOnSquare(movingPiece, startingKingPosition - 1);
								if (!kingInCheck(movingPiece.getColor(), chessBoard) && !kingInBorderingSquare(startingKingPosition - 1, 6, chessBoard)) {
									chessBoard.placePieceOnSquare(null, startingKingPosition - 1);
									chessBoard.placePieceOnSquare(movingPiece, startingKingPosition - 2);
									if(!kingInCheck(movingPiece.getColor(), chessBoard) && !kingInBorderingSquare(startingKingPosition - 2, 6, chessBoard)) {
										Move castleQueenSideMove = new Move(startingKingPosition, startingKingPosition - 2, movingPiece, null, false, false, true);
										possibleMoves.add(castleQueenSideMove);
									}
									chessBoard.placePieceOnSquare(null, startingKingPosition - 2);
									chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
								}
								chessBoard.placePieceOnSquare(null, startingKingPosition - 1);
								chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
							}
						}
						
					}
					
				}
				
			}
		}
		
		return possibleMoves;
	
	}
	
	/**
	 * 
	 * @param currentSquare
	 * @param chessGame
	 * @return
	 */
	public static ArrayList<Move> getPossiblePawnMoves(int currentSquare, ChessGame chessGame){
		
		ChessBoard chessBoard = chessGame.getChessBoard();
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
		Color player = movingPiece.getColor();
		int destinationSquare;
		
		if (chessBoard.getPieceOnSquare(currentSquare + (8 * player.getBoardPerspective())) == null) {
			if(!abandoningKing(currentSquare, currentSquare + (8 * player.getBoardPerspective()), player, chessBoard, false)) {
				possibleMoves.add(new Move(currentSquare, currentSquare + (8 * player.getBoardPerspective()), movingPiece, null, (currentSquare + (8 * player.getBoardPerspective()) / 8) == movingPiece.getColor().getPerspectiveRow(7), false, false));
			}
			if (currentSquare / 8 ==  player.getPerspectiveRow(1) && chessBoard.getPieceOnSquare(currentSquare + (16 * player.getBoardPerspective())) == null) {
				if(!abandoningKing(currentSquare, currentSquare + (16 * player.getBoardPerspective()), player, chessBoard, false)) {
					possibleMoves.add(new Move(currentSquare, currentSquare + (16 * player.getBoardPerspective()), movingPiece, null, false, false, false));
				}
			}
		}
		ChessPiece possibleCapturedPiece;
		int[] directions = player.getPawnCapturingDirections();
		for(int i: directions) {
			if ((destinationSquare = AdjacentSquares.get(currentSquare, i)) != -1) {
				Move opponentsLastMove = chessGame.getMoveHistory().getLastMoveMade();
				
				if(opponentsLastMove != null && opponentsLastMove.getMovingPiece() instanceof Pawn  
						&& destinationSquare == opponentsLastMove.getSource() - (8 * player.getBoardPerspective())
						&& opponentsLastMove.getDestination() == opponentsLastMove.getSource() - (16 * player.getBoardPerspective())) {
					
					possibleCapturedPiece = chessBoard.getPieceOnSquare(opponentsLastMove.getDestination());
					if(!abandoningKing(currentSquare, destinationSquare, player, chessBoard, true)) {
						Move enPaesant = new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece, false, true, false);
						possibleMoves.add(enPaesant);
					}
					
				}else {
					possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
					if (possibleCapturedPiece != null && possibleCapturedPiece.getColor() != player) {
						if(!abandoningKing(currentSquare, destinationSquare, player, chessBoard, false)) {
							possibleMoves.add(new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece, destinationSquare / 8 == movingPiece.getColor().getPerspectiveRow(7), false, false));
						}
					}
				}
			}
		}
		return possibleMoves;
	}
	
	/**
	 * 
	 * @param originalSquare
	 * @param destinationSquare
	 * @param player
	 * @param chessBoard
	 * @param enPaesant
	 * @return
	 */
	private static boolean abandoningKing(int originalSquare, int destinationSquare, Color player, ChessBoard chessBoard, boolean enPaesant) {
		
		boolean abandoningKing = false;
		
		
		ChessPiece possibleCapturedPiece = enPaesant? chessBoard.getPieceOnSquare(destinationSquare - (8 * player.getBoardPerspective())): chessBoard.getPieceOnSquare(destinationSquare);
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(originalSquare);
		
		chessBoard.placePieceOnSquare(null, originalSquare);
		chessBoard.placePieceOnSquare(movingPiece, destinationSquare);
		if(enPaesant) {
			chessBoard.placePieceOnSquare(null, destinationSquare - (8 * player.getBoardPerspective()));
		}
		
		if (kingInCheck(player, chessBoard)) {
			abandoningKing = true;
		}
		
		chessBoard.placePieceOnSquare(possibleCapturedPiece, enPaesant? destinationSquare - (8 * player.getBoardPerspective()):destinationSquare);
		chessBoard.placePieceOnSquare(movingPiece, originalSquare);
		if(enPaesant) {
			chessBoard.placePieceOnSquare(null, destinationSquare);
		}
		return abandoningKing;
	}
	
	/**
	 * 
	 * @param destinationSquare
	 * @param directionMoved
	 * @param chessBoard
	 * @return
	 */
	private static boolean kingInBorderingSquare(int destinationSquare, int directionMoved, ChessBoard chessBoard) {
		if(directionMoved % 2 == 0) {
			// 7 8 9
			for (int i = 7; i < 10; i++){
				if (AdjacentSquares.get(destinationSquare, (i + directionMoved) % 8) != -1) {
					if (chessBoard.getPieceOnSquare(AdjacentSquares.get(destinationSquare, (i + directionMoved) % 8)) instanceof King ) {
						return true;
					}
				}
			}
		} else {
			// 6 7 8 9 10
			for (int i = 6; i < 11; i++){
				if (AdjacentSquares.get(destinationSquare, (i + directionMoved) % 8) != -1) {
					if (chessBoard.getPieceOnSquare(AdjacentSquares.get(destinationSquare, (i + directionMoved) % 8)) instanceof King ) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param squareNumber
	 * @param chessBoard
	 * @return
	 */
	public static boolean kingInCheck(Color player, ChessBoard chessBoard) {
		
		int kingLocation;
		
		if (player == Color.WHITE) {
			kingLocation = chessBoard.getWhiteKingPosition();
		} else {
			kingLocation = chessBoard.getBlackKingPosition();
		}
		
		int currentSquare = kingLocation;
		ChessPiece theKing = chessBoard.getPieceOnSquare(kingLocation);
		// check to see if there is an attacking Queen, Rook, or Bishop
		for (int i = 0; i < 8; i++) {
			if (i % 2 == 0) {
				// check to see if there is an attacking Queen or Rook
				while (AdjacentSquares.get(currentSquare, i) != -1 && chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i)) == null) {
					currentSquare = AdjacentSquares.get(currentSquare, i);
				}
				if (AdjacentSquares.get(currentSquare, i) != -1) {
					ChessPiece piece = chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i));
					if (piece.getColor() != theKing.getColor() && (piece instanceof Rook || piece instanceof Queen)) {
						return true;
					}
				}
			} else {
				// check to see if there is an attacking Queen or Bishop
				while (AdjacentSquares.get(currentSquare, i) != -1 && chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i)) == null) {
					currentSquare = AdjacentSquares.get(currentSquare, i);
				}
				if (AdjacentSquares.get(currentSquare, i) != -1) {
					ChessPiece piece = chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i));
					if (piece.getColor() != theKing.getColor() && (piece instanceof Bishop || piece instanceof Queen)) {
						return true;
					}
				}
			}
			currentSquare = kingLocation;
		}
		// check to see if there is an attacking knight
		ChessPiece knight;
		for (int i = 0; i < 8; i++) {
			if ((knight = chessBoard.getPieceOnSquare(AdjacentSquares.get(AdjacentSquares.get(currentSquare, i), (i + 1) % 8))) instanceof Knight) {
				if (knight.getColor() != theKing.getColor()) {
					return true;
				}
			}
		}
		// check to see if there is an attacking pawn
		// an attacking pawn will be coming from the NorthEast or NorthWest
		ChessPiece pawn;
		int[] directionsOfAttackingPawns = theKing.getColor().getPawnCapturingDirections();
		for (int direction: directionsOfAttackingPawns) {
			if (AdjacentSquares.get(kingLocation, direction) != -1 && (pawn = chessBoard.getPieceOnSquare(AdjacentSquares.get(kingLocation, direction))) instanceof Pawn) {
				if (pawn.getColor() != theKing.getColor()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 * @param chessGame
	 * @return
	 */
	public static boolean isCheckmateOrStalemate(Color player, ChessGame chessGame) {
		
		ChessBoard chessBoard = chessGame.getChessBoard();
		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();
		ArrayList<Move> possibleMovesForPiece;
		for (int i = 0; i < 64; i++) {
			ChessPiece piece = chessBoard.getPieceOnSquare(i);
			if (piece != null && piece.getColor() == player) {
				if (piece instanceof Pawn) {
					possibleMovesForPiece = getPossiblePawnMoves(i, chessGame);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
				else if(piece instanceof Knight) {
					possibleMovesForPiece = getPossibleKnightMoves(i, chessGame);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
				else if(piece instanceof Bishop || piece instanceof Rook || piece instanceof Queen) {
					possibleMovesForPiece = getPossibleQRBMoves(i, chessGame);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
				else if(piece instanceof King) {
					possibleMovesForPiece = getPossibleKingMoves(i, chessGame);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
			}
		}
		if (allPossibleMoves.isEmpty()) {
			return true;
		}
		return false;
	}
}
