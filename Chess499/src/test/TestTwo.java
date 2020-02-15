package test;

import java.util.Scanner;

import chess.*;

/**
 * 
 * @author Luke Newman
 *
 */
public class TestTwo {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChessGame game = new ChessGame();
		ChessBoard board = game.getChessBoard();
		System.out.println(board.toString());
		
		Scanner input = new Scanner(System.in);
		
		String menu = "Enter an option ---- MakeMove: 1		UndoMove: 2		RedoMove: 3 \n";
		int moveCounter = 0;
		while (!game.isCheckmateOrStalemate(Color.values()[moveCounter%2])) {
			System.out.println(menu);
			
			int option = input.nextInt();
			if (option == 1) {
				System.out.println("Enter the square number of the piece you'd like to move.");
				int sourceSquare = input.nextInt();
				System.out.println("Enter the square number of the square you'd like to move it to.");
				int targetSquare = input.nextInt();
				while (!game.makeMove(sourceSquare, targetSquare)) {
					System.out.println("INVALID MOVE: Try Again.\n");
					System.out.println("Enter the square number of the piece you'd like to move.");
					sourceSquare = input.nextInt();
					System.out.println("Enter the square number of the square you'd like to move it to.");
					targetSquare = input.nextInt();
				}
				System.out.println(game.lastMove());
				System.out.println(board.toString());
				moveCounter++;
			} else if (option == 2) {
				if (game.undoMove()) {
					System.out.println(board.toString());
					moveCounter--;
				}else {
					System.out.println("Not an option at this time.");
				}
			} else if (option == 3){
				if (game.redoMove()) {
					System.out.println(board.toString());
					moveCounter++;
				} else {
					System.out.println("Not an option at this time.");
				}
			} else {
				System.out.println("Not an option.");
			}
			
		}
		if (game.isKingInCheck(Color.values()[moveCounter%2])) {
			System.out.println(Color.values()[(moveCounter - 1)%2] + " wins.");
		} else {
			System.out.println("Draw: Stalemate.");
		}
		input.close();
		
	}

}
