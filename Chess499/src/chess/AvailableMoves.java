package chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
/**
 * This class contains a collection of ChessPieces mapped to an ArrayList of Moves.
 * 	It is updated after every move so that an attempted move can be validated against 
 * 	it. 
 * 
 * @author Luke Newman 2020
 *
 */
public class AvailableMoves implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Map<ChessPiece, ArrayList<Move>> availableMoves;
	
	/**
	 * Constructor initializes the array.
	 */
	public AvailableMoves() {
		availableMoves = new HashMap<ChessPiece, ArrayList<Move>>();
	}
	
	/**
	 * Updates the Move ArrayList for specified ChessPiece.
	 * 
	 * @param piece
	 * @param moves
	 */
	public void updateAvailableMoves(ChessPiece piece, ArrayList<Move> moves) {
		availableMoves.put(piece, moves);
	}
	
	/**
	 * Clears the ArrayList of Moves for the specified ChessPiece.
	 * 
	 * @param piece
	 */
	public void clearAvailableMovesForPiece(ChessPiece piece) {
		if(availableMoves.containsKey(piece)) {
			availableMoves.get(piece).clear();
		}
	}
	
	/**
	 * Determines whether or not the ChessPiece passed in as an argument 
	 * 	has any available moves at this point during the game. 
	 * 
	 * @param piece
	 * @return 			- true if the piece can make a move, false otherwise
	 */
	public boolean hasAvailable(ChessPiece piece) {
		if (availableMoves.get(piece) != null) {
			return !availableMoves.get(piece).isEmpty();
		}
		return false;
	}
	
	/**
	 * Gets the available moves for the ChessPiece passed in as an argument.
	 * 
	 * @param piece
	 * @return 			- an ArrayList of available Moves for the ChessPiece
	 */
	public ArrayList<Move> getMovesForPiece(ChessPiece piece){
		return availableMoves.get(piece);
	}
	
	/**
	 * Determines whether or not a specific Move is available.
	 * 
	 * @precondition 	- MovingPiece must not be null.
	 * @param move
	 * @return  		- true if that move is available, false otherwise
	 */
	public boolean isAvailable(Move move) {
		return availableMoves.get(move.getMovingPiece()).contains(move);
	}
	
	/**
	 * Gets all the available moves for Color.BLACK or Color.WHITE.
	 * 
	 * @param player 	- Color.BLACK or Color.WHITE
	 * @return 		 	- all the available moves for that player
	 */
	public ArrayList<Move> getAvailableMoves(Color player) {
		ArrayList<Move> playersAvailableMoves = new ArrayList<Move>();
		Set<ChessPiece> pieces = availableMoves.keySet();
		for (ChessPiece piece : pieces) {
			if (piece.getColor() == player) {
				playersAvailableMoves.addAll((getMovesForPiece(piece)));
			}
		}
		return playersAvailableMoves;
	}
	
	/**
	 * Gets all the moves that can be made with the same type of ChessPiece onto the same square for the same player.
	 * 	This method was created to support standard chess move notation.  If two or more knights, rooks, etc... of 
	 * 	the same color can move to the same square, and the player makes one of those moves, the program needs to 
	 * 	specify which one he/she actually moved. (e.g. Knight in the A file moves to C3, but another Knight in the 
	 * 	E file can also move to C3.  Which one did the player move? Nac3 or Nec3) 
	 * 	
	 * @param chessMove - the move that is being made
	 * @return			- the available moves for the player that are similar with regard to type and destination
	 */
	public ArrayList<Move> getCommonMovesEqualDestination(Move chessMove){
		ArrayList<Move> movesForDestinationSquare = new ArrayList<Move>();
		
		Set<ChessPiece> pieces = availableMoves.keySet();
		for (ChessPiece piece: pieces) {
			if (piece.matches(chessMove.getMovingPiece()) && piece != chessMove.getMovingPiece()) {
				for(Move move: availableMoves.get(piece)) {
					// I believe the second part of this statement may be eliminated (we already checked it in the above if statement)
					if (move.getDestination() == chessMove.getDestination() && move.getSource() != chessMove.getSource()) {
						movesForDestinationSquare.add(move);
					}
				}
			}
		}
		
		return movesForDestinationSquare;
	}
	
}
