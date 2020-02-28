package view;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ChessBoardUI extends GridPane{
	
	private Background lightSquare = new Background(new BackgroundFill(Color.PAPAYAWHIP, null, null));
	private Background darkSquare = new Background(new BackgroundFill(Color.BLUEVIOLET, null, null));
	private boolean flipped;
	
	/**
	 * 
	 * @param eventHandler
	 */
	public ChessBoardUI(EventHandler<MouseEvent> eventHandler) {
		setPrefSize(480,480);
		flipped = false;
		for (int i = 63; i > -1; i--) {
			StackPane square = new StackPane();
			square.setPrefSize(60, 60);
			square.setOnMouseClicked(eventHandler);
			square.setBackground((i/8) % 2 == 0 && i % 2 == 0 ? lightSquare : (i / 8) % 2 == 1 && i % 2 == 1 ? lightSquare : darkSquare );
			add(square, i%8, i/8);
			
		}
	}
	
	/**
	 * 
	 * @param imageView
	 * @param squareNumber
	 */
	public void add(ImageView imageView, int squareNumber) {
		clearImageFromSquare(squareNumber);
		StackPane square = getSquarePane(flipped? 7 - (squareNumber%8) : squareNumber%8, flipped? squareNumber/8 : 7 - (squareNumber/8));
		square.getChildren().add(imageView);
	}
	
	/**
	 * 
	 * @param squareNumber
	 */
	public void clearImageFromSquare(int squareNumber) {
		StackPane square = getSquarePane(flipped? 7 - (squareNumber%8) : squareNumber%8, flipped? squareNumber/8 : 7 - (squareNumber/8));
		List<Node> list = square.getChildren();
		Node imageToClear = null;
		for (Node node: list) {
			if(node instanceof ImageView) {
				imageToClear = node;
			}
		}
		if (imageToClear != null) {
			square.getChildren().remove(imageToClear);
		}
	}
	
	/**
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public StackPane getSquarePane(int column, int row) {
		for (Node square: this.getChildren()) {
			if (GridPane.getColumnIndex(square) == column && GridPane.getRowIndex(square) == row) {
				return (StackPane) square;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param square
	 * @return
	 */
	public int getSquareID(StackPane square) {
		int i = 0;
		
		for (Node stackPane: this.getChildren()) {
			if (stackPane == square) {
				return flipped? (7 - (i/8)) * 8 + (i%8) : 8 * (i/8) + 7 - (i%8);
			}
			i++;
		}
		return -1;
	}
	
	public void flipBoard() {
		if (flipped) {
			flipped = false;
		}else {
			flipped = true;
		}
	}
}
