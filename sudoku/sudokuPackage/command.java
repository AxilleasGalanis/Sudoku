package sudoku.sudokuPackage;

import sudoku.tiles.*;

public abstract class command extends RuntimeException {

	protected sudokuGrid grid;
	protected viewTile viewTile;
	protected sudokuTile old;
	protected sudokuTile newTile;

	protected command(sudokuGrid grid, viewTile viewTile) {
            this.grid = grid;
            this.viewTile = viewTile;
            old = viewTile.getSudokuTile();
	}

	public void undo() {
            viewTile.setTile(old);
            grid.replaceTile(newTile.getRow(), newTile.getColumn(), old);
	}

}
