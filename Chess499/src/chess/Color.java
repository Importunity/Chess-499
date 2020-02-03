package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public enum Color {
	WHITE(0, 1, new int[] {7, 1}), BLACK(7, -1, new int []{3, 5});
	
	private int firstRow;
	private int boardPerspective;
	private int[] pawnCapturingDirections;
	
	/**
	 * 
	 * @param firstRow
	 * @param boardPerspective
	 * @param pawnCapturingDirections
	 */
	Color(int firstRow, int boardPerspective, int[] pawnCapturingDirections){
		this.firstRow = firstRow;
		this.boardPerspective = boardPerspective;
		this.pawnCapturingDirections = pawnCapturingDirections;
		
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
}
