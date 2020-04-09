package controller;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import view.LoggerPane;

public class ChessLogHandler extends Handler {
	
	private LoggerPane pane;
	private static ChessLogHandler handler;
	
	private ChessLogHandler(LoggerPane pane) {
		this.pane = pane;
	}
	
	public static ChessLogHandler getInstance() {
		if (handler == null) {
			handler = new ChessLogHandler(GameController.getInstance().getLoggerPane());
		}
		return handler;
	}
	
	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void publish(LogRecord record) {
		
		pane.showLog(record.getMessage());
	}

}
