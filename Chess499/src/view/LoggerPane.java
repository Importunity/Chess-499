package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class LoggerPane extends ScrollPane{
	
	private TextArea text;
	
	public LoggerPane() {
		
		text = new TextArea();
		text.setWrapText(true);
		text.setEditable(false);
		text.prefWidthProperty().bind(widthProperty());
		setContent(text);
		
	}
	
	public void showLog(String info) {
		text.appendText(info + "\n");
	}
}

	
