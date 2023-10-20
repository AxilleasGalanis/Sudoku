package sudoku.tiles;
import sudoku.sudokuPackage.command;
import sudoku.sudokuPackage.sudokuGrid;

public class eraseTile extends command {

	public eraseTile(sudokuGrid grid, viewTile viewTile) {
            super(grid, viewTile);
            this.newTile = new emptyTile(old);
	}

}
