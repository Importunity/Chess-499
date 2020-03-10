package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class UtilityPane extends FlowPane{
	
	private Button undoButton;
	private Button redoButton;
	
	public UtilityPane(EventHandler<ActionEvent> eventHandler) {
		undoButton = new Button("Undo");
		redoButton = new Button("Redo");
		
		
		undoButton.setOnAction(eventHandler);
		redoButton.setOnAction(eventHandler);
		
		getChildren().add(undoButton);
		getChildren().add(redoButton);
		
	}
	
}
