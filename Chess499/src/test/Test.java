package test;

import java.util.Scanner;

import chess.*;

/**
 * 
 * @author Luke Newman
 *
 */
public class Test {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChessGame game = new ChessGame();
		ChessBoard board = game.getChessBoard();
		System.out.println(board.toString());
		
		Scanner input = new Scanner(System.in);
		
		int moveCounter = 0;
		while (!game.isCheckmateOrStalemate(Color.values()[moveCounter%2])) {
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
		}
		if (game.isKingInCheck(Color.values()[moveCounter%2])) {
			System.out.println(Color.values()[(moveCounter - 1)%2] + " wins.");
		} else {
			System.out.println("Draw: Stalemate.");
		}
		input.close();
		
	}

}
