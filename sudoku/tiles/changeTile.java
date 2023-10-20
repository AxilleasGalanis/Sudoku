package sudoku.tiles;
import sudoku.sudokuPackage.command;
import sudoku.sudokuPackage.sudokuGrid;

public class changeTile extends command {

	private final int newValue;

	public changeTile(sudokuGrid grid, viewTile viewTile, int newValue) {
            super(grid, viewTile);
            this.newValue = newValue;
            this.newTile = new tempTile(old, newValue);
	}

}
