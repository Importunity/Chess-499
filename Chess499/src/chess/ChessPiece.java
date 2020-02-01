package chess;

public abstract class ChessPiece {
	
	protected Color color;
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
