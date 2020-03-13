package view;

import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.MoveHistoryTable.ChessMove;

/**
 * 
 * @author Luke Newman
 *
 */
public class MoveHistoryTable extends TableView<ChessMove>{
	
	private int numberOfMoves;
	TableColumn<ChessMove, String> whiteMovesColumn;
	TableColumn<ChessMove, String> blackMovesColumn;
	ObservableList<ChessMove> gameMoves;
	
	/**
	 * 
	 */
	public MoveHistoryTable() {
		
		whiteMovesColumn = new TableColumn<ChessMove, String>("White Moves");
		blackMovesColumn = new TableColumn<ChessMove, String>("Black Moves");
		gameMoves = FXCollections.observableArrayList();
		setItems(gameMoves);
		whiteMovesColumn.setCellValueFactory(new PropertyValueFactory<ChessMove, String>("whiteMove"));
		blackMovesColumn.setCellValueFactory(new PropertyValueFactory<ChessMove, String>("blackMove"));
		getColumns().add(whiteMovesColumn);
		getColumns().add(blackMovesColumn);
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setMaxWidth(200);
		setMinWidth(100);
		setPrefSize(200,300);
		numberOfMoves = 0;
	}
	
	/**
	 * 
	 * @param move
	 */
	public void addMove(String move) {
		if (numberOfMoves % 2 == 0) {
			ChessMove newMove = new ChessMove();
			newMove.setWhiteMove(move);
			gameMoves.add(newMove);
		} else {
			String whitesMove = gameMoves.get(gameMoves.size() - 1).getWhiteMove();
			gameMoves.remove(gameMoves.size() - 1);
			ChessMove newMove = new ChessMove();
			newMove.setWhiteMove(whitesMove);
			newMove.setBlackMove(move);
			gameMoves.add(newMove);
		}
		numberOfMoves++;
	}
	
	/**
	 * 
	 */
	public void undoMove() {
		if (numberOfMoves % 2 == 0) {
			String whitesMove = gameMoves.remove(gameMoves.size() - 1).getWhiteMove();
			ChessMove updatedMove = new ChessMove();
			updatedMove.setWhiteMove(whitesMove);
			gameMoves.add(updatedMove);
		} else {
			gameMoves.remove(gameMoves.size() - 1);
		}
		numberOfMoves--;
	}
	
	/**
	 * 
	 */
	public void clear() {
		numberOfMoves = 0;
		gameMoves.clear();
	}
	
	/**
	 * 
	 * @param moves
	 */
	public void loadMoves(Stack<String> moves) {
		clear();
		while (!moves.isEmpty()) {
			addMove(moves.pop());
		}
	}
	
	/**
	 * 
	 * @author Luke Newman
	 *
	 */
	public class ChessMove{
		
		private String whiteMove;
		private String blackMove;
		
		/**
		 * 
		 * @return
		 */
		public String getWhiteMove() {
			return whiteMove;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getBlackMove() {
			return blackMove;
		}
		
		/**
		 * 
		 * @param whiteMove
		 */
		public void setWhiteMove(String whiteMove) {
			this.whiteMove = whiteMove;
		}
		
		/**
		 * 
		 * @param blackMove
		 */
		public void setBlackMove(String blackMove) {
			this.blackMove = blackMove;
		}
		
	}
	
}


