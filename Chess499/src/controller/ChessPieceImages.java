package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessPieceImages {
	

	public static Image WHITE_PAWN;
	public static Image BLACK_PAWN;
	public static Image WHITE_ROOK;
	public static Image BLACK_ROOK;
	public static Image WHITE_KNIGHT;
	public static Image BLACK_KNIGHT;
	public static Image WHITE_BISHOP;
	public static Image BLACK_BISHOP;
	public static Image WHITE_QUEEN;
	public static Image BLACK_QUEEN;
	public static Image WHITE_KING;
	public static Image BLACK_KING;
	
	public static void setImages() {
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\whitePawn.png")){
			WHITE_PAWN = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\blackPawn.png")){
			BLACK_PAWN = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\whiteRook.png")){
			WHITE_ROOK = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\blackRook.png")){
			BLACK_ROOK = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\whiteKnight.png")){
			WHITE_KNIGHT = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\blackKnight.png")){
			BLACK_KNIGHT = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\whiteBishop.png")){
			WHITE_BISHOP = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\blackBishop.png")){
			BLACK_BISHOP = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\whiteQueen.png")){
			WHITE_QUEEN = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\blackQueen.png")){
			BLACK_QUEEN = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\whiteKing.png")){
			WHITE_KING = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
		
		try (FileInputStream io = new FileInputStream(".\\chessPieceIcons\\blackKing.png")){
			BLACK_KING = new Image(io);
		}catch (FileNotFoundException ex) {
			
		}catch (IOException ex) {
			
		}
	}
	
	public static ImageView getImageView(String piece) {
		switch(piece) {
		case "whitePawn":
			return new ImageView(WHITE_PAWN);
		case "blackPawn":
			return new ImageView(BLACK_PAWN);
		case "whiteKnight":
			return new ImageView(WHITE_KNIGHT);
		case "blackKnight":
			return new ImageView(BLACK_KNIGHT);
		case "whiteBishop":
			return new ImageView(WHITE_BISHOP);
		case "blackBishop":
			return new ImageView(BLACK_BISHOP);
		case "whiteRook":
			return new ImageView(WHITE_ROOK);
		case "blackRook":
			return new ImageView(BLACK_ROOK);
		case "whiteQueen":
			return new ImageView(WHITE_QUEEN);
		case "blackQueen":
			return new ImageView(BLACK_QUEEN);
		case "whiteKing":
			return new ImageView(WHITE_KING);
		case "blackKing":
			return new ImageView(BLACK_KING);
		default:
			return null;
		}
	}
	
	
}
