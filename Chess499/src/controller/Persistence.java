package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.logging.Logger;
import java.util.logging.Level;

import chess.ChessGame;

/**
 * 
 * @author Luke Newman
 *
 */
public class Persistence {
	
	private static Persistence persistence;
	
	/**
	 * 
	 */
	private Persistence() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static Persistence getInstance() {
		if (persistence == null) {
			persistence = new Persistence();
		}
		return persistence;
	}
	
	/**
	 * 
	 * @param fileToSave
	 * @param chessGame
	 */
	public void saveGame(File fileToSave, ChessGame chessGame) {
		try(	FileOutputStream fileOut = new FileOutputStream(fileToSave);
				ObjectOutputStream output = new ObjectOutputStream(fileOut);)
		{
			output.writeObject(chessGame);
		} 
		catch (Exception ex) {
			Logger.getLogger(ChessGame.class.getName()).log(Level.SEVERE, ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param fileToLoad
	 * @return
	 */
	public ChessGame loadGame(File fileToLoad) {
		try(	FileInputStream fileIn = new FileInputStream(fileToLoad);
				ObjectInputStream input = new ObjectInputStream(fileIn))
		{
			ChessGame game = (ChessGame) input.readObject();
			return game;
		}
		catch (Exception ex) {
			Logger.getLogger(ChessGame.class.getName()).log(Level.SEVERE, ex.getMessage());
			return null;
		}
		
	}
}
