package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * 
 * @author Luke Newman
 *
 */
public class ChessAppMenuBar extends MenuBar{
	
	private Menu fileMenu;
	private Menu modeMenu;
	private Menu viewMenu;
	private Menu feedbackMenu;
	
	/**
	 * 
	 * @param eventHandler
	 */
	public ChessAppMenuBar(EventHandler<ActionEvent> eventHandler) {
		
		fileMenu = new Menu("File");
		modeMenu = new Menu("Mode");
		viewMenu = new Menu("View");
		feedbackMenu = new Menu("Contact Us");
		
		setMenuItems(eventHandler);
		getMenus().addAll(fileMenu, modeMenu, viewMenu, feedbackMenu);
		
	}
	
	/**
	 * 
	 * @param eventHandler
	 */
	private void setMenuItems(EventHandler<ActionEvent> eventHandler) {
		
		MenuItem newGame = new MenuItem("New Game");
		fileMenu.getItems().add(newGame);
		newGame.setOnAction(eventHandler);
		MenuItem saveGame = new MenuItem("Save Game");
		fileMenu.getItems().add(saveGame);
		saveGame.setOnAction(eventHandler);
		MenuItem loadGame = new MenuItem("Load Game");
		fileMenu.getItems().add(loadGame);
		loadGame.setOnAction(eventHandler);
		
		MenuItem feedback = new MenuItem("Give Feedback");
		feedbackMenu.getItems().add(feedback);
		feedback.setOnAction(eventHandler);
		
		Menu computerMode = new Menu("Play Computer");
		modeMenu.getItems().add(computerMode);
		MenuItem playAsWhite = new MenuItem("Play as White");
		computerMode.getItems().add(playAsWhite);
		playAsWhite.setOnAction(eventHandler);
		MenuItem playAsBlack = new MenuItem("Play as Black");
		computerMode.getItems().add(playAsBlack);
		playAsBlack.setOnAction(eventHandler);
		MenuItem humanMode = new MenuItem("Human Mode");
		modeMenu.getItems().add(humanMode);
		humanMode.setOnAction(eventHandler);
		
		MenuItem flipBoard = new MenuItem("Flip Board");
		viewMenu.getItems().add(flipBoard);
		flipBoard.setOnAction(eventHandler);
	}
}
