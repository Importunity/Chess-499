package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ChessAppMenuBar;
import view.ChessBoardUI;
import view.MoveHistoryTable;
import view.UtilityPane;

/**
 * Main class launches the application.
 * 
 * @author Luke Newman
 *
 */
public class Main extends Application{
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// pane below the chessboard 
		ScrollPane logger = new ScrollPane();
		logger.setMaxWidth(480);
		
		// ChessPieceSet box to hold pieces
		GridPane chessPieceSet = new GridPane();
		
		// split the border pane into two separate panes
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		// Set up a GameController to manage coordination between the view and the model
		GameController gameController = new GameController(primaryStage);
		// get the Board UI to place in the scene
		ChessBoardUI board = gameController.getChessBoardView();
		MoveHistoryTable historyOfMoves = gameController.getMoveHistoryTable();
		UtilityPane utilityPane = gameController.getUtilityPane();
		
		// Set up left side of scene with Board and Logger
		vbox1.getChildren().add(board);
		vbox1.getChildren().add(logger);
		// Set up right side of scene with History of Moves table, undo/redo controls, and the chesspiece set box
		vbox2.getChildren().add(historyOfMoves);
		vbox2.getChildren().add(utilityPane);
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