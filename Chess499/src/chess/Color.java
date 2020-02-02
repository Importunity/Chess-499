package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public enum Color {
	WHITE(7), BLACK(0);
	
	private int lastRow;
	
	Color(int lastRow){
		this.lastRow = lastRow;
	}
	
	public int getLastRow() {
		return lastRow;
	}
}
