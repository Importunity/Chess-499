package chess;

import java.util.Stack;
/**
 * 
 * @author Luke Newman
 *
 */
public class MoveHistory {
	
	private Stack<Move> historyOfMoves;
	private Stack<Move> undoneMoves;
	
	/**
	 * 
	 */
	public MoveHistory() {
		historyOfMoves = new Stack<Move>();
		undoneMoves = new Stack<>();
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
		if (!undoneMoves.empty()) {
			undoneMoves.clear();
		}
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
	 * 
	 */
	public void undoMove() {
		if(!historyOfMoves.isEmpty()) {
			Move moveUndone = historyOfMoves.pop();
			ChessPiece movingPiece = moveUndone.getMovingPiece();
			if(movingPiece instanceof King) {
				((King) movingPiece).unmotion(); 
			} else if (movingPiece instanceof Rook) {
				((Rook) movingPiece).unMotion();
			}
			undoneMoves.push(moveUndone);
		}
	}
	
	/**
	 * 
	 */
	public void redoMove() {
		if(!undoneMoves.isEmpty()) {
			Move moveRedone = undoneMoves.pop();
			ChessPiece movingPiece = moveRedone.getMovingPiece();
			if (movingPiece instanceof King) {
				((King) movingPiece).motion();
			} else if (movingPiece instanceof Rook) {
				((Rook) movingPiece).motion();
			}
			historyOfMoves.push(moveRedone);
		}
	}
	
	public Move getLastMoveUndone() {
		if(undoneMoves.empty()) {
			return null;
		}
		return undoneMoves.peek();
	}
}
