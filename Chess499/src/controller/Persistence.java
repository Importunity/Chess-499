package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import chess.ChessGame;


public class Persistence {
	
	private static Persistence persistence;
	
	private Persistence() {
		
	}
	
	public static Persistence getInstance() {
		if (persistence == null) {
			persistence = new Persistence();
		}
		return persistence;
	}
	
	public void saveGame(File fileToSave, ChessGame chessGame) {
		try(	FileOutputStream fileOut = new FileOutputStream(fileToSave);
				ObjectOutputStream output = new ObjectOutputStream(fileOut);)
		{
			output.writeObject(chessGame);
		} 
		catch (Exception ex) {
			
		}
	}
	
	public ChessGame loadGame(File fileToLoad) {
		
		try(	FileInputStream fileIn = new FileInputStream(fileToLoad);
				ObjectInputStream input = new ObjectInputStream(fileIn))
		{
			ChessGame game = (ChessGame) input.readObject();
			return game;
		}
		catch (Exception ex) {
			return null;
		}
		
	}
}
