package chess;

import java.util.ArrayList;

public class ChessGame {
	
	private ChessBoard chessBoard;
	private int movesMade;
	
	public ChessGame() {
		chessBoard = new ChessBoard();
		initializeChessBoard();
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
		System.out.println(movingPiece.getColor().ordinal());
		if (movingPiece.getColor().ordinal() != movesMade % 2) {
			return false;
		}
		ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(targetSquare);
		if (movingPiece != null) {
			if (movingPiece instanceof Pawn) {
				Move moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece);
				ArrayList<Move> possibleMoves = getPossiblePawnMoves(sourceSquare);
				if(possibleMoves.contains(moveAttempt)) {
					chessBoard.placePieceOnSquare(null, sourceSquare);
					if(targetSquare > 55 && movingPiece.getColor() == Color.WHITE) {
						chessBoard.placePieceOnSquare(new Queen(Color.WHITE), targetSquare);
					}
					else if (targetSquare < 8 && movingPiece.getColor() == Color.BLACK) {
						chessBoard.placePieceOnSquare(new Queen(Color.BLACK), targetSquare);
					} else {
						chessBoard.placePieceOnSquare(movingPiece, targetSquare);
					}
					movesMade++;
					return true;
				}
			} else if (movingPiece instanceof Knight) {
				Move moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece);
				ArrayList<Move> possibleMoves = getPossibleKnightMoves(sourceSquare);
				if(possibleMoves.contains(moveAttempt)) {
					chessBoard.placePieceOnSquare(movingPiece, targetSquare);
					chessBoard.placePieceOnSquare(null, sourceSquare);
					movesMade++;
					return true;
				}
			} else if (movingPiece instanceof Bishop || movingPiece instanceof Rook || movingPiece instanceof Queen) {
				Move moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece);
				ArrayList<Move> possibleMoves = getPossibleQRBMoves(sourceSquare);
				if(possibleMoves.contains(moveAttempt)) {
					chessBoard.placePieceOnSquare(movingPiece, targetSquare);
					chessBoard.placePieceOnSquare(null, sourceSquare);
					movesMade++;
					return true;
				}
			} else if (movingPiece instanceof King){
				Move moveAttempt = new Move(sourceSquare, targetSquare, movingPiece, possibleCapturedPiece);
				ArrayList<Move> possibleMoves = getPossibleKingMoves(sourceSquare);
				if(possibleMoves.contains(moveAttempt)) {
					chessBoard.placePieceOnSquare(movingPiece, targetSquare);
					chessBoard.placePieceOnSquare(null, sourceSquare);
					movesMade++;
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}
	
	private boolean legalMove(int originalSquare, int destinationSquare, Color player) {
		
		boolean legalMove = false;
		
		ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(originalSquare);
		
		chessBoard.placePieceOnSquare(null, originalSquare);
		chessBoard.placePieceOnSquare(movingPiece, destinationSquare);
		
		if (!kingInCheck(player)) {
			legalMove = true;
		}
		
		chessBoard.placePieceOnSquare(possibleCapturedPiece, destinationSquare);
		chessBoard.placePieceOnSquare(movingPiece, originalSquare);
		
		return legalMove;
	}
	
	public ArrayList<Move> getPossibleQRBMoves(int currentSquare){
		
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
				
				if(legalMove(originalSquare, destinationSquare, movingPiece.getColor())) {
					possibleMove = new Move(originalSquare, destinationSquare, movingPiece, null);
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
					if(legalMove(originalSquare, destinationSquare, movingPiece.getColor())) {
						possibleMove = new Move(originalSquare, destinationSquare, movingPiece, possibleCapturedPiece);
						possibleMoves.add(possibleMove);
					}
					
				}
				
			}
			
			currentSquare = originalSquare;
			
		}
	
		return possibleMoves;
	}
	
	public ArrayList<Move> getPossibleKnightMoves(int currentSquare){
		
		Move possibleMove;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
		int destinationSquare;
		
		for(int i = 0; i < 8; i++) {
		
			try{
				if ((destinationSquare = AdjacentSquares.get(AdjacentSquares.get(currentSquare, i), (i + 1)%8)) != -1) {
					ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
					if (possibleCapturedPiece == null) {
						if(legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
							possibleMove = new Move(currentSquare, destinationSquare, movingPiece, null);
							possibleMoves.add(possibleMove);
						}
						
					} else if (possibleCapturedPiece.getColor() != movingPiece.getColor()) {
						if(legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
							possibleMove = new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece);
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
	
	public ArrayList<Move> getPossibleKingMoves(int currentSquare){
		
		Move possibleMove;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
	
		for (int i = 0; i < 8; i++) {
			int destinationSquare = AdjacentSquares.get(currentSquare, i);
			
			if (destinationSquare != -1 && kingInBorderingSquare(destinationSquare, i) == false) {
				ChessPiece possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
				if(possibleCapturedPiece == null) {
					if (legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
						possibleMove = new Move(currentSquare, destinationSquare, movingPiece, null);
						possibleMoves.add(possibleMove);
					}
				} else if(possibleCapturedPiece.getColor() != movingPiece.getColor()){
					if (legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
						possibleMove = new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece);
						possibleMoves.add(possibleMove);
					}
				}
			}
		
		}
	
		// to be implemented (castling)
	
		return possibleMoves;
	
	}
	
	public ArrayList<Move> getPossiblePawnMoves(int currentSquare){
		
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ChessPiece movingPiece = chessBoard.getPieceOnSquare(currentSquare);
		int destinationSquare;
		
		if (movingPiece.getColor() == Color.WHITE) {
			// pawns will be promoted once they reach the end of the board 
			// we do not have to worry about going off the board in the North direction
			if (chessBoard.getPieceOnSquare(currentSquare + 8) == null) {
				
				if(legalMove(currentSquare, currentSquare + 8, movingPiece.getColor())) {
					possibleMoves.add(new Move(currentSquare, currentSquare + 8, movingPiece, null));
				}
				if (currentSquare < 16) {
					if(legalMove(currentSquare, currentSquare + 16, movingPiece.getColor())) {
						possibleMoves.add(new Move(currentSquare, currentSquare + 16, movingPiece, null));
					}
				}
				
			}
			// capturing with pawns (direction is NorthEast and NorthWest for white pawns)
			ChessPiece possibleCapturedPiece;
			if ((destinationSquare = AdjacentSquares.get(currentSquare, 1)) != -1) {
				possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
				if (possibleCapturedPiece != null && possibleCapturedPiece.getColor() == Color.BLACK) {
					if(legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
						possibleMoves.add(new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece));
					}
				}
			}
			
			if ((destinationSquare = AdjacentSquares.get(currentSquare, 7)) != -1) {
				possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
				if (possibleCapturedPiece != null && possibleCapturedPiece.getColor() == Color.BLACK) {
					if(legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
						possibleMoves.add(new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece));
					}
				}
			}
		} else { // black pawns
			// pawns will be promoted once they reach the end of the board 
			// we do not have to worry about going off the board in the South direction
			if (chessBoard.getPieceOnSquare(currentSquare - 8) == null) {
				if(legalMove(currentSquare, currentSquare - 8, movingPiece.getColor())) {
					possibleMoves.add(new Move(currentSquare, currentSquare - 8, movingPiece, null));
				}
				if (currentSquare > 47) {
					if(legalMove(currentSquare, currentSquare - 16, movingPiece.getColor())) {
						possibleMoves.add(new Move(currentSquare, currentSquare - 16, movingPiece, null));
					}
				}
				
			}
			// capturing with pawns (direction is SouthEast and SouthWest for Black pawns)
			ChessPiece possibleCapturedPiece;
			if ((destinationSquare = AdjacentSquares.get(currentSquare, 3)) != -1) {
				possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
				if (possibleCapturedPiece != null && possibleCapturedPiece.getColor() == Color.WHITE) {
					if(legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
						possibleMoves.add(new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece));
					}
					
				}
			}
			if ((destinationSquare = AdjacentSquares.get(currentSquare, 5)) != -1) {
				possibleCapturedPiece = chessBoard.getPieceOnSquare(destinationSquare);
				if (possibleCapturedPiece != null && possibleCapturedPiece.getColor() == Color.WHITE) {
					if(legalMove(currentSquare, destinationSquare, movingPiece.getColor())) {
						possibleMoves.add(new Move(currentSquare, destinationSquare, movingPiece, possibleCapturedPiece));
					}
					
				}
			}
		}
		return possibleMoves;
	}

	public boolean kingInCheck(Color player) {
		
		if (player == Color.WHITE) {
			int whiteKingLocation = chessBoard.getWhiteKingPosition();
			int currentSquare = whiteKingLocation;
			// check to see if there is an attacking Queen, Rook, or Bishop
			for (int i = 0; i < 8; i++) {
				if (i % 2 == 0) {
					// check to see if there is an attacking Queen or Rook
					while (AdjacentSquares.get(currentSquare, i) != -1 && chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i)) == null) {
						currentSquare = AdjacentSquares.get(currentSquare, i);
					}
					if (AdjacentSquares.get(currentSquare, i) != -1) {
						ChessPiece piece = chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i));
						if (piece.getColor() == Color.BLACK && (piece instanceof Rook || piece instanceof Queen)) {
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
						if (piece.getColor() == Color.BLACK && (piece instanceof Bishop || piece instanceof Queen)) {
							return true;
						}
					}
				}
				currentSquare = whiteKingLocation;
			}
			// check to see if there is an attacking knight
			ChessPiece knight;
			for (int i = 0; i < 8; i++) {
				try{
					if ((knight = chessBoard.getPieceOnSquare(AdjacentSquares.get(i, (i + 1)%8))) instanceof Knight) {
						if (knight.getColor() == Color.BLACK) {
							return true;
						}
					}
				} catch (IndexOutOfBoundsException ex) {
					// location is off the board
				}
			}
			// check to see if there is an attacking pawn
			// an attacking pawn will be coming from the NorthEast or NorthWest
			ChessPiece pawn;
			if (AdjacentSquares.get(whiteKingLocation, 1) != -1 && (pawn = chessBoard.getPieceOnSquare(AdjacentSquares.get(whiteKingLocation, 1))) instanceof Pawn) {
				if (pawn.getColor() == Color.BLACK) {
					return true;
				}
			}
			
			if (AdjacentSquares.get(whiteKingLocation, 7) != -1 && (pawn = chessBoard.getPieceOnSquare(AdjacentSquares.get(whiteKingLocation, 7))) instanceof Pawn) {
				if (pawn.getColor() == Color.BLACK) {
					return true;
				}
			}
		} else {
			int blackKingLocation = chessBoard.getBlackKingPosition();
			int currentSquare = blackKingLocation;
			// check to see if there is an attacking Queen, Rook, or Bishop
			for (int i = 0; i < 8; i++) {
				if (i % 2 == 0) {
					// check to see if there is an attacking Queen or Rook
					while (AdjacentSquares.get(currentSquare, i) != -1 && chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i)) == null) {
						currentSquare = AdjacentSquares.get(currentSquare, i);
					}
					if (AdjacentSquares.get(currentSquare, i) != -1) {
						ChessPiece piece = chessBoard.getPieceOnSquare(AdjacentSquares.get(currentSquare, i));
						if (piece.getColor() == Color.WHITE && (piece instanceof Rook || piece instanceof Queen)) {
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
						if (piece.getColor() == Color.WHITE && (piece instanceof Bishop || piece instanceof Queen)) {
							return true;
						}
					}
				}
				currentSquare = blackKingLocation;
			}
			// check to see if there is an attacking knight
			ChessPiece knight;
			for (int i = 0; i < 8; i++) {
				try{
					if ((knight = chessBoard.getPieceOnSquare(AdjacentSquares.get(AdjacentSquares.get(currentSquare, i), (i + 1)%8))) instanceof Knight) {
						if (knight.getColor() == Color.WHITE) {
							return true;
						}
					}
				} catch (IndexOutOfBoundsException ex) {
					// location is off the board
				}
			}
			// check to see if there is an attacking pawn
			// an attacking pawn will be coming from the SouthEast or SouthWest
			ChessPiece pawn;
			if (AdjacentSquares.get(blackKingLocation, 3) != -1 && (pawn = chessBoard.getPieceOnSquare(AdjacentSquares.get(blackKingLocation, 3))) instanceof Pawn) {
				if (pawn.getColor() == Color.WHITE) {
					return true;
				}
			}
			
			if (AdjacentSquares.get(blackKingLocation, 5) != -1 && (pawn = chessBoard.getPieceOnSquare(AdjacentSquares.get(blackKingLocation, 5))) instanceof Pawn) {
				if (pawn.getColor() == Color.WHITE) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean kingInBorderingSquare(int destinationSquare, int directionMoved) {
		if(directionMoved % 2 == 0) {
			// 7 8 9
			for (int i = 7; i < 10; i++){
				if (AdjacentSquares.get(destinationSquare, i % 8) != -1) {
					if (chessBoard.getPieceOnSquare(AdjacentSquares.get(destinationSquare, i % 8)) instanceof King ) {
						return true;
					}
				}
			}
		} else {
			// 6 7 8 9 10
			for (int i = 6; i < 11; i++){
				if (AdjacentSquares.get(destinationSquare, i % 8) != -1) {
					if (chessBoard.getPieceOnSquare(AdjacentSquares.get(destinationSquare, i % 8)) instanceof King ) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkMate(Color player) {
		
		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();
		ArrayList<Move> possibleMovesForPiece;
		for (int i = 0; i < 64; i++) {
			ChessPiece piece = chessBoard.getPieceOnSquare(i);
			if (piece != null && piece.getColor() == player) {
				if (piece instanceof Pawn) {
					possibleMovesForPiece = getPossiblePawnMoves(i);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
				else if(piece instanceof Knight) {
					possibleMovesForPiece = getPossibleKnightMoves(i);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
				else if(piece instanceof Bishop || piece instanceof Rook || piece instanceof Queen) {
					possibleMovesForPiece = getPossibleQRBMoves(i);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
				else if(piece instanceof King) {
					possibleMovesForPiece = getPossibleKingMoves(i);
					allPossibleMoves.addAll(possibleMovesForPiece);
				}
			}
		}
		if (kingInCheck(player) && allPossibleMoves.isEmpty()) {
			return true;
		}
		return false;
	}
	
}
