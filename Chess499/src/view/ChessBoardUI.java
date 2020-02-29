package view;

import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
	Label[] ranks = {new Label("1"), new Label("2"), new Label("3"), new Label("4"), 
			new Label("5"), new Label("6"), new Label("7"), new Label("8")};
	Label[] files = {new Label("A"), new Label("B"), new Label("C"), new Label("D"), 
			new Label("E"), new Label("F"), new Label("G"), new Label("H")};
	/**
	 * 
	 * @param eventHandler
	 */
	public ChessBoardUI(EventHandler<MouseEvent> eventHandler) {
		setPrefSize(480,480);
		flipped = false;
		for (int i = 0; i < 64; i++) {
			StackPane square = new StackPane();
			square.setPrefSize(60, 60);
			square.setOnMouseClicked(eventHandler);
			square.setBackground((i/8) % 2 == 0 && i % 2 == 0 ? lightSquare : (i / 8) % 2 == 1 && i % 2 == 1 ? lightSquare : darkSquare );
			if (i % 8 == 0) {
				Label rank = ranks[7 - i/8];
				square.getChildren().add(rank);
				StackPane.setAlignment(rank, Pos.BOTTOM_LEFT);
				
			}
			if (i / 8 == 7) {
				Label file = files[i%8];
				square.getChildren().add(file);
				StackPane.setAlignment(file, Pos.BOTTOM_RIGHT);
			}
			
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
				return flipped?  8 * (i/8) + 7 - (i%8) : (7 - (i/8)) * 8 + (i%8);
			}
			i++;
		}
		return -1;
	}
	
	public void flipBoard() {
		if (flipped) {
			for (int i = 0; i < 8 ; i++) {
				StackPane rankSquare = getSquarePane(0, i);
				rankSquare.getChildren().remove(ranks[i]);
				rankSquare.getChildren().add(ranks[7 - i]);
				
				StackPane fileSquare = getSquarePane(i, 7);
				fileSquare.getChildren().remove(files[7 - i]);
				fileSquare.getChildren().add(files[i]);
			}
			flipped = false;
		}else {
			for (int i = 0; i < 8 ; i++) {
				StackPane rankSquare = getSquarePane(0, i);
				rankSquare.getChildren().remove(ranks[7 - i]);
				rankSquare.getChildren().add(ranks[i]);
				
				StackPane fileSquare = getSquarePane(i, 7);
				fileSquare.getChildren().remove(files[i]);
				fileSquare.getChildren().add(files[7 - i]);
			}
			
			flipped = true;
		}
	}
}
