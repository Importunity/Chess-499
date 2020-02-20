package controller;

import java.util.ArrayList;
import java.util.List;

import chess.ChessGame;
import chess.ChessPiece;
import chess.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.ChessAppMenuBar;
import view.ChessBoardUI;
import view.ChessBoardUI.SquarePane;

public class GameController {


	private ChessGame game;
	private ChessBoardUI board;
	private ChessAppMenuBar menu;
	
	public GameController() {
		// the model
		game = new ChessGame();
		game.isCheckmateOrStalemate(Color.WHITE);
		// the view
		board = new ChessBoardUI(new ChessBoardController());
		menu = new ChessAppMenuBar(new MenuBarController());
		ChessPieceImages.setImages();
		
		updateBoard();
	}
	
	public ChessBoardUI getChessBoardView() {
		return board;
	}
	
	public ChessAppMenuBar getMenuBar() {
		return menu;
	}
	
	public void updateBoard() {
		ChessPiece piece;
		for (int i = 0; i < 64; i++) {
			if ((piece = game.getChessBoard().getPieceOnSquare(i)) != null) {
				ImageView pieceImage = ChessPieceImages.getImageView(piece.toString());
				SquarePane pane = board.getSquarePane(i % 8, 7 - (i / 8));
				List<Node> list = pane.getChildren();
				Node capturedPiece = null;
				for (Node node: list) {
					if(node instanceof ImageView) {
						capturedPiece = node;
					}
				}
				if (capturedPiece != null) {
					pane.getChildren().remove(capturedPiece);
				}
				pane.getChildren().add(pieceImage);
				
			} else {
				SquarePane pane = board.getSquarePane(i % 8, 7 - (i / 8));
				pane.getChildren().clear();
				pane.getChildren().add(new Label(pane.getID() + ""));
				pane.getChildren().get(0).setVisible(false);
			}
		}
	}
	
	
	public class ChessBoardController implements EventHandler<MouseEvent>{
		
		private int sourceSquare;
		private int targetSquare;
		ArrayList<Integer> targetSquares = new ArrayList<Integer>();
		
		public ChessBoardController() {
			sourceSquare = -1;
			targetSquare = -1;
		}
		
		@Override
		public void handle(MouseEvent event) {
			SquarePane square = (SquarePane) event.getSource();
			if (sourceSquare == -1) {
				if(game.hasAvailableMove(square.getID())) {
					targetSquares = game.getAvailableTargetSquares(square.getID());
					if (!targetSquares.isEmpty()) {
						sourceSquare = square.getID();
						System.out.println(sourceSquare);
					}
				}
				
			}
			else {
				targetSquare = square.getID();
				
				if (targetSquares.contains( (Integer) targetSquare)) {
					System.out.println(targetSquare);
					if(game.makeMove(sourceSquare, targetSquare)) {
						sourceSquare = -1;
						targetSquare = -1;
						game.isCheckmateOrStalemate(Color.values()[game.getMovesMade()%2]);
						updateBoard();
					}
				}else {
					sourceSquare = -1;
					targetSquare = -1;
				}
				
			}
			
			
		}
		
	}
	
	public class MenuBarController implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			
			MenuItem sourceObject = (MenuItem) event.getSource();
			switch (sourceObject.getText()) {
			case "New Game":
				game = new ChessGame();
				game.isCheckmateOrStalemate(Color.WHITE);
				updateBoard();
				break;
			default:
				break;	
			}
			
		}
		
	}
}
