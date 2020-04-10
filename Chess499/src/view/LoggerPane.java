package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class LoggerPane extends ScrollPane{
	
	private TextArea text;
	
	public LoggerPane() {
		text = new TextArea();
		text.setWrapText(true);
		text.setEditable(false);
		text.prefWidthProperty().bind(widthProperty());
		//text.prefHeightProperty().bind(heightProperty());
		setContent(text);
	}
	
	public void showLog(String info) {
		text.appendText(info + "\n");
	}
}

	
