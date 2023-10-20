package sudoku.tiles;

import java.awt.*;

public class tempTile extends solvedTile {

	protected final int value;
	private final Color defaultBackgroundColor = new Color(235, 235, 235);

	public tempTile(sudokuTile tile, int newValue) {
            super(tile.getSolvedTile(), tile.getGrid(), tile.getRow(), tile.getColumn());
            this.value = newValue;
	}

        @Override
	public int getTile() {
            return value;
	}

	@Override
	public String getNum() {
            return String.valueOf(value);
	}

	@Override
	public Color getDefaultBackColor() {
            return defaultBackgroundColor;
	}

	@Override
	public boolean isFinal() {
            return true;
	}
}
