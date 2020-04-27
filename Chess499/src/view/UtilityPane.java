package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import chess.ChessGame;

import controller.GameController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

/**
 * 
 * @author Luke Newman
 *
 */
public class UtilityPane extends FlowPane{
	
	private Button undoButton;
	private Button redoButton;
	private HBox buttons;
	
	private Label scoreLabel;
	private TextField scoreValue;
	private HBox score;
	
	private Label depthLabel;
	private ChoiceBox<Integer> depthOption;
	private ArrayList<Integer> options;
	private HBox depth;
	
	/**
	 * 
	 * @param eventHandler
	 */
	public UtilityPane(EventHandler<ActionEvent> eventHandler) {
		setPrefSize(200, 200);
		setOrientation(Orientation.VERTICAL);
		setVgap(10);
		
		buttons = new HBox();
		score = new HBox();
		depth = new HBox();
		
		undoButton = new Button("Undo");
		redoButton = new Button("Redo");
		undoButton.setOnAction(eventHandler);
		redoButton.setOnAction(eventHandler);
		buttons.getChildren().add(undoButton);
		buttons.getChildren().add(redoButton);
		
		scoreLabel = new Label("Score: ");
		scoreLabel.setStyle(" -fx-text-fill: red;"
				+ "-fx-font-weight: bold;");
		scoreValue = new TextField("");
		scoreValue.setEditable(false);
		score.getChildren().add(scoreLabel);
		score.getChildren().add(scoreValue);
		
		depthLabel = new Label("Depth: ");
		depthLabel.setStyle(" -fx-text-fill: red;"
				+ "-fx-font-weight: bold;");
		options = new ArrayList<Integer>();
		for (int i = ChessGame.MIN_DEPTH; i <= ChessGame.MAX_DEPTH; i++) {
			options.add(i);
		}
		depthOption = new ChoiceBox<Integer>(FXCollections.observableArrayList(
			    options)
			);
		
		depthOption.getSelectionModel().selectedIndexProperty().addListener( new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				GameController.getInstance().depthChanged(options.get(newValue.intValue()));
			}

			
		});
		depth.getChildren().add(depthLabel);
		depth.getChildren().add(depthOption);
		
		getChildren().add(buttons);
		getChildren().add(score);
		getChildren().add(depth);
		
		try (FileInputStream io = new FileInputStream(".\\Images\\ChessHead.png")){
			Image chessHead = new Image(io);
			setBackground(new Background( new BackgroundImage(
					chessHead, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					new BackgroundPosition(Side.LEFT, 0, false, Side.TOP, 10, false), 
					new BackgroundSize(getPrefWidth(),getPrefHeight(),false,false,false,false)))
			);
		}catch (FileNotFoundException ex) {
			Logger.getLogger(ChessGame.LOGGER_NAME).log(Level.SEVERE, ex.getMessage());
		}catch (IOException ex) {
			Logger.getLogger(ChessGame.LOGGER_NAME).log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void setMargins() {
		FlowPane.setMargin(buttons, new Insets(1, 0, 0, 5));
		FlowPane.setMargin(score, new Insets(1, 0, 0, 5));
		FlowPane.setMargin(depth, new Insets(1, 0, 0, 5));
	}
	
	public void setScore(double value) {
		scoreValue.setText(Double.toString(value));
	}
}
