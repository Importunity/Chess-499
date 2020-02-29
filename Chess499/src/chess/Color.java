package chess;

import java.io.Serializable;

/**
 * 
 * @author Luke Newman
 *
 */
public enum Color implements Serializable{
	
	WHITE(0, 1, new int[] {7, 1}, 4), BLACK(7, -1, new int []{3, 5}, 60);
	
	private int firstRow;
	private int boardPerspective;
	private int[] pawnCapturingDirections;
	private int startingKingPosition;
	
	/**
	 * 
	 * @param firstRow
	 * @param boardPerspective
	 * @param pawnCapturingDirections
	 */
	Color(int firstRow, int boardPerspective, int[] pawnCapturingDirections, int startingKingPosition){
		this.firstRow = firstRow;
		this.boardPerspective = boardPerspective;
		this.pawnCapturingDirections = pawnCapturingDirections;
		this.startingKingPosition = startingKingPosition;
	}
	
	/**
	 * 
	 * @param row
	 * @return
	 */
	public int getPerspectiveRow(int row) {
		return firstRow + (row * boardPerspective);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBoardPerspective() {
		return boardPerspective;
	}
	
	/**
	 * 
	 * @return
	 */
	public int[] getPawnCapturingDirections() {
		return pawnCapturingDirections;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getStartingKingPosition() {
		return startingKingPosition;
	}
}
