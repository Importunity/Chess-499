package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import view.ChessAppMenuBar;
import view.ChessBoardUI;
import view.LoggerPane;
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
		
		
		// ChessPieceSet box to hold pieces
		GridPane chessPieceSet = new GridPane();
		
		// split the border pane into two separate panes
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		// Set up a GameController to manage coordination between the view and the model
		GameController gameController = GameController.getInstance();
		gameController.setStage(primaryStage);
		// get the Board UI to place in the scene
		ChessBoardUI board = gameController.getChessBoardView();
		MoveHistoryTable historyOfMoves = gameController.getMoveHistoryTable();
		UtilityPane utilityPane = gameController.getUtilityPane();
		LoggerPane loggerPane = gameController.getLoggerPane();
		gameController.setLogger();
		// Set up left side of scene with Board and Logger
		vbox1.getChildren().add(board);
		//vbox1.getChildren().add(loggerPane);
		// Set up right side of scene with History of Moves table, undo/redo controls, and the chesspiece set box
		vbox2.getChildren().add(historyOfMoves);
		vbox2.getChildren().add(utilityPane);
		vbox2.getChildren().add(chessPieceSet);
		
		// get the menu bar
		ChessAppMenuBar menuBar = gameController.getMenuBar();
		// root pane of scene
		BorderPane bp = new BorderPane();
		HBox hbox = new HBox();
		hbox.getChildren().add(loggerPane);
		hbox.setMinHeight(80);
		hbox.setMaxHeight(200);
		loggerPane.prefWidthProperty().bind(hbox.widthProperty());
		loggerPane.prefHeightProperty().bind(hbox.heightProperty());
		
		bp.setCenter(vbox1);
		bp.setRight(vbox2);
		bp.setTop(menuBar);
		bp.setBottom(hbox);
		bp.setLeft(null);
		
		Scene scene = new Scene(bp, 700, 600);
		bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());
        bp.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, null, null)));
		primaryStage.setTitle("ChessGuys");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		
	}

}