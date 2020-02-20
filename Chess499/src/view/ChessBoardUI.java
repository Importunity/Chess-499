package view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ChessBoardUI extends GridPane{
	
	private Background lightSquare = new Background(new BackgroundFill(Color.PAPAYAWHIP, null, null));
	private Background darkSquare = new Background(new BackgroundFill(Color.BLUEVIOLET, null, null));
	
	public ChessBoardUI(EventHandler<MouseEvent> eventHandler) {
		setPrefSize(480,480);
		for (int i = 63; i > -1; i--) {
			SquarePane square = new SquarePane(i);
			square.setPrefSize(60, 60);
			square.setOnMouseClicked(eventHandler);
			square.setBackground((i/8) % 2 == 0 && i % 2 == 0 ? darkSquare : (i / 8) % 2 == 1 && i % 2 == 1 ? darkSquare : lightSquare );
			add(square, i%8, 7 - (i/8));
			
		}
	}
	
	/**
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public SquarePane getSquarePane(int column, int row) {
		for (Node square: this.getChildren()) {
			if (GridPane.getColumnIndex(square) == column && GridPane.getRowIndex(square) == row) {
				return (SquarePane) square;
			}
		}
		return null;
	}
	
	public class SquarePane extends StackPane {
		
		private int id;
		
		public SquarePane(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
	}
	
}
