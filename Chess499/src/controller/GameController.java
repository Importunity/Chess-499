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
import javafx.scene.layout.StackPane;
import view.ChessAppMenuBar;
import view.ChessBoardUI;

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
				board.add(pieceImage, i);
			} else {
				board.clearImageFromSquare(i);
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
			StackPane square = (StackPane) event.getSource();
			int squareID = board.getSquareID(square);
			if (sourceSquare == -1) {
				if(game.hasAvailableMove(squareID)) {
					targetSquares = game.getAvailableTargetSquares(squareID);
					if (!targetSquares.isEmpty()) {
						sourceSquare = squareID;
						System.out.println(sourceSquare);
					}
				}
				
			}
			else {
				targetSquare = squareID;
				
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
			case "Flip Board":
				board.flipBoard();
				updateBoard();
				break;
			default:
				break;	
			}
			
		}
		
	}
}