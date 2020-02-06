package chess;

import java.util.Stack;
/**
 * 
 * @author Luke Newman
 *
 */
public class MoveHistory {
	
	private Stack<Move> historyOfMoves;
	//private Stack<Move> undoneMoves;
	
	/**
	 * 
	 */
	public MoveHistory() {
		historyOfMoves = new Stack<Move>();
	}
	
	/**
	 * 
	 * @param move
	 */
	public void addMove(Move move) {
		ChessPiece movingPiece = move.getMovingPiece();
		if(movingPiece instanceof King) {
			((King) movingPiece).motion();
		} else if (movingPiece instanceof Rook) {
			((Rook) movingPiece).motion();
		}
		historyOfMoves.push(move);
		//undoneMoves.clear();
	}
	
	/**
	 * 
	 * @return
	 */
	public Move getLastMoveMade() {
		if(historyOfMoves.isEmpty()) {
			return null;
		}
		return historyOfMoves.peek();
	}
	/**
	public void undoMove() {
		if(!historyOfMoves.isEmpty()) {
			undoneMoves.push(historyOfMoves.pop());
		}
	}
	
	public void redoMove() {
		if(!undoneMoves.isEmpty()) {
			historyOfMoves.push(undoneMoves.pop());
		}
	}
	**/
}
