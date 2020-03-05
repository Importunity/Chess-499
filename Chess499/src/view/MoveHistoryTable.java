package view;

import javafx.scene.control.TextArea;


public class MoveHistoryTable extends TextArea{
	
	private int numberOfMoves;
	public MoveHistoryTable() {
		numberOfMoves = 0;
	}
	
	public void addMove(String move) {
		
		if (numberOfMoves % 2 == 0) {
			appendText(String.valueOf(numberOfMoves / 2 + 1) + ". " + move);
		} else {
			appendText("   " + move + "\n");
		}
		numberOfMoves++;
	}
	
}


