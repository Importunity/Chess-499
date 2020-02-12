package chess;

import java.util.ArrayList;
/**
 * 
 * @author Luke Newman
 *
 */
public class GameRules {
	
	private static GameRules gameRules;
	
	/**
	 * 
	 */
	private GameRules() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static GameRules getInstance() {
		if (gameRules == null) {
			gameRules = new GameRules();
		}
		return gameRules;
	}
	
	/**
	 * 
	 * @param currentSquare
	 * @param chessGame
	 * @return
	 */
	public ArrayList<Move> getPossibleQRBMoves(int currentSquare, ChessBoard chessBoard){
		
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
					// old code - possibleMove = new Move(originalSquare, destinationSquare, movingPiece, null, false, false, false);
					possibleMove = MoveFactory.getInstance().createMove(originalSquare, destinationSquare, chessBoard, null);
					possibleMoves.add(possibleMove);
				}
				// advance to the next square in same direction
				currentSquare = AdjacentSquares.get(currentSquare, i);
				
			}	
			// if it is not the end of the board there is either a black or white piece there
			if(destinationSquare != -1) {
				
				if(chessBoard.getPieceOnSquare(destinationSquare).getColor() != movingPiece.getColor()) {
					// possible capture
					if(!abandoningKing(originalSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
						possibleMove = MoveFactory.getInstance().createMove(originalSquare, destinationSquare, chessBoard, null);
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
	public ArrayList<Move> getPossibleKnightMoves(int currentSquare, ChessBoard chessBoard){
		
		Move possibleMove;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
		int destinationSquare;
		// 8 total directions
		for(int i = 0; i < 8; i++) {
		
			if ((destinationSquare = AdjacentSquares.get(AdjacentSquares.get(currentSquare, i), (i + 1)%8)) != -1) {
					
					ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
					if (possibleCapturedPiece == null || possibleCapturedPiece.getColor() != movingPiece.getColor()) {
						if(!abandoningKing(currentSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
							possibleMove = MoveFactory.getInstance().createMove(currentSquare, destinationSquare, chessBoard, null);
							possibleMoves.add(possibleMove);
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
	public ArrayList<Move> getPossibleKingMoves(int currentSquare, ChessBoard chessBoard){
		
		Move possibleMove;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
	
		for (int i = 0; i < 8; i++) {
			int destinationSquare = AdjacentSquares.get(currentSquare, i);
			
			if (destinationSquare != -1 && kingInBorderingSquare(destinationSquare, i, chessBoard) == false) {
				
				ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
				if (possibleCapturedPiece == null || possibleCapturedPiece.getColor() != movingPiece.getColor()) {
					if (!abandoningKing(currentSquare, destinationSquare, movingPiece.getColor(), chessBoard, false)) {
						possibleMove = MoveFactory.getInstance().createMove(currentSquare, destinationSquare, chessBoard, null);
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
										chessBoard.placePieceOnSquare(null, startingKingPosition + 2);
										chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
										// old code - Move castleKingSideMove = new Move(startingKingPosition, startingKingPosition + 2, movingPiece, null, false, false, true);
										Move castleKingSideMove = MoveFactory.getInstance().createMove(startingKingPosition, startingKingPosition + 2, chessBoard, null);
										possibleMoves.add(castleKingSideMove);
									}else {
										chessBoard.placePieceOnSquare(null, startingKingPosition + 2);
										chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
									}
								}else {
									chessBoard.placePieceOnSquare(null, startingKingPosition + 1);
									chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
								}
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
										chessBoard.placePieceOnSquare(null, startingKingPosition - 2);
										chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
										//Move castleQueenSideMove = new Move(startingKingPosition, startingKingPosition - 2, movingPiece, null, false, false, true);
										Move castleQueenSideMove = MoveFactory.getInstance().createMove(startingKingPosition, startingKingPosition - 2, chessBoard, null);
										possibleMoves.add(castleQueenSideMove);
									} else {
										chessBoard.placePieceOnSquare(null, startingKingPosition - 2);
										chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
									}
								} else {
									chessBoard.placePieceOnSquare(null, startingKingPosition - 1);
									chessBoard.placePieceOnSquare(movingPiece, startingKingPosition);
								}
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
	public ArrayList<Move> getPossiblePawnMoves(int currentSquare, ChessBoard chessBoard, Move opponentsLastMove){
		
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
		Color player = movingPiece.getColor();
		int destinationSquare;
		
		if (chessBoard.getPieceOnSquare(currentSquare + (8 * player.getBoardPerspective())) == null) {
			if(!abandoningKing(currentSquare, currentSquare + (8 * player.getBoardPerspective()), player, chessBoard, false)) {
				possibleMoves.add(MoveFactory.getInstance().createMove(currentSquare, currentSquare + (8 * player.getBoardPerspective()), chessBoard, opponentsLastMove));
			}
			if (currentSquare / 8 ==  player.getPerspectiveRow(1) && chessBoard.getPieceOnSquare(currentSquare + (16 * player.getBoardPerspective())) == null) {
				if(!abandoningKing(currentSquare, currentSquare + (16 * player.getBoardPerspective()), player, chessBoard, false)) {
					possibleMoves.add(MoveFactory.getInstance().createMove(currentSquare, currentSquare + (16 * player.getBoardPerspective()), chessBoard, opponentsLastMove));
				}
			}
		}
		ChessPiece possibleCapturedPiece;
		int[] directions = player.getPawnCapturingDirections();
		for(int i: directions) {
			if ((destinationSquare = AdjacentSquares.get(currentSquare, i)) != -1) {
				
				if(opponentsLastMove != null && opponentsLastMove.getMovingPiece() instanceof Pawn  
						&& destinationSquare == opponentsLastMove.getSource() - (8 * player.getBoardPerspective())
						&& opponentsLastMove.getDestination() == opponentsLastMove.getSource() - (16 * player.getBoardPerspective())) {
					
					possibleCapturedPiece = chessBoard.getPieceOnSquare(opponentsLastMove.getDestination());
					if(!abandoningKing(currentSquare, destinationSquare, player, chessBoard, true)) {
						Move enPassant = MoveFactory.getInstance().createMove(currentSquare, destinationSquare, chessBoard, opponentsLastMove);
						possibleMoves.add(enPassant);
					}
				}else {
					possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
					if (possibleCapturedPiece != null && possibleCapturedPiece.getColor() != player) {
						if(!abandoningKing(currentSquare, destinationSquare, player, chessBoard, false)) {
							possibleMoves.add(MoveFactory.getInstance().createMove(currentSquare, destinationSquare, chessBoard, opponentsLastMove));
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
	 * @param enPassant
	 * @return
	 */
	private boolean abandoningKing(int originalSquare, int destinationSquare, Color player, ChessBoard chessBoard, boolean enPassant) {
		
		boolean abandoningKing = false;
		
		ChessPiece possibleCapturedPiece = enPassant? chessBoard.getPieceOnSquare(destinationSquare - (8 * player.getBoardPerspective())): chessBoard.getPieceOnSquare(destinationSquare);
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(originalSquare);
		
		chessBoard.placePieceOnSquare(null, originalSquare);
		chessBoard.placePieceOnSquare(movingPiece, destinationSquare);
		if(enPassant) {
			chessBoard.placePieceOnSquare(null, destinationSquare - (8 * player.getBoardPerspective()));
		}
		
		if (kingInCheck(player, chessBoard)) {
			abandoningKing = true;
		}
		
		chessBoard.placePieceOnSquare(possibleCapturedPiece, enPassant? destinationSquare - (8 * player.getBoardPerspective()):destinationSquare);
		chessBoard.placePieceOnSquare(movingPiece, originalSquare);
		if(enPassant) {
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
	private boolean kingInBorderingSquare(int destinationSquare, int directionMoved, ChessBoard chessBoard) {
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
	public boolean kingInCheck(Color player, ChessBoard chessBoard) {
		
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

}
