package controller;

import chess.Move;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import view.ChessAppMenuBar;
import view.ChessBoardUI;

public class Main extends Application{
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// pane below the chessboard 
		ScrollPane logger = new ScrollPane();
		logger.setMaxWidth(480);
		
		// All this block of code will be moved to a new class HistoryOfMovesUI in the view package
		TableView<String> historyOfMoves = new TableView<String>();
		historyOfMoves.setBorder(new Border(new BorderStroke(Paint.valueOf("Gold"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
		historyOfMoves.setVisible(true);
		TableColumn<String, Move> whiteMoves = new TableColumn<String, Move>("White");
		TableColumn<String, Move> blackMoves = new TableColumn<String, Move>("Black");
		whiteMoves.setPrefWidth(75);
		blackMoves.setPrefWidth(75);
		historyOfMoves.getColumns().add(whiteMoves);
		historyOfMoves.getColumns().add(blackMoves);
		historyOfMoves.setPrefSize(150, 200);
		
		// HBox for undo and redo moves, and score, and timer
		HBox hbox1 = new HBox();
		// ChessPieceSet box to hold pieces
		GridPane chessPieceSet = new GridPane();
		
		// split the border pane into two separate panes
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		// Set up a GameController to manage coordination between the view and the model
		GameController gameController = new GameController(primaryStage);
		// get the Board UI to place in the scene
		ChessBoardUI board = gameController.getChessBoardView();
		
		// Set up left side of scene with Board and Logger
		vbox1.getChildren().add(board);
		vbox1.getChildren().add(logger);
		// Set up right side of scene with History of Moves table, undo/redo controls, and the chesspiece set box
		vbox2.getChildren().add(historyOfMoves);
		vbox2.getChildren().add(hbox1);
		vbox2.getChildren().add(chessPieceSet);
		
		// get the menu bar
		ChessAppMenuBar menuBar = gameController.getMenuBar();
		// root pane of scene
		BorderPane bp = new BorderPane();
		
		bp.setCenter(vbox1);
		bp.setRight(vbox2);
		bp.setTop(menuBar);
		bp.setBottom(null);
		bp.setLeft(null);
		
		Scene scene = new Scene(bp, 700, 600);
		
		primaryStage.setTitle("ChessGuys");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}

}