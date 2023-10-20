package sudoku;

import sudoku.sudokuPackage.*;

public class Sudoku {
	private Sudoku() {
                sudokuSolver solver = new sudokuSolver();
                sudokuFrame frame = new sudokuFrame();
		sudokuGrid grid = new sudokuGrid(solver);
		new sudokuActions(frame, grid);
	}

	public static void main(String[] args) {
		new Sudoku();
	}

}