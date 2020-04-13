package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	
	/**
	 * 
	 * @param eventHandler
	 */
	public UtilityPane(EventHandler<ActionEvent> eventHandler) {
		setPrefSize(200, 180);
		setOrientation(Orientation.VERTICAL);
		setVgap(10);
		
		buttons = new HBox();
		score = new HBox();
		
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
		
		getChildren().add(buttons);
		getChildren().add(score);
		
	}
	
	public void setMargins() {
		FlowPane.setMargin(buttons, new Insets(1, 0, 0, 5));
		FlowPane.setMargin(score, new Insets(1, 0, 0, 5));
	}
	
	public void setScore(double value) {
		scoreValue.setText(Double.toString(value));
	}
}
