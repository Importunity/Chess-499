package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class LoggerPane extends ScrollPane{
	
	private TextArea text;
	
	public LoggerPane() {
		setMaxWidth(480);
		text = new TextArea();
		text.setWrapText(true);
		text.setEditable(false);
		setContent(text);
	}
	
	public void showLog(String info) {
		text.appendText(info);
	}
}

	
