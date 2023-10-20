package sudoku.tiles;

import java.awt.*;

public interface sudokuTile {
    int getRow();

    int getColumn();

    int getTile();

    int getGrid();

    int getSolvedTile();

    String getNum();

    Color getDefaultBackColor();

    boolean isFinal();
}

