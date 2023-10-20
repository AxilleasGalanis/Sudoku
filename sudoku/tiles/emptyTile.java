package sudoku.tiles;
import java.awt.*;

public class emptyTile extends solvedTile {

    private final Color defaultBackColor = new Color(255, 255, 255);

    public emptyTile(sudokuTile sudokuTile) {
        super(sudokuTile.getSolvedTile(), sudokuTile.getGrid(), sudokuTile.getRow(), sudokuTile.getColumn());
    }

    @Override
    public String getNum() {
        return "";
    }

    @Override
    public int getTile() {
        return 0;
    }

    @Override
    public Color getDefaultBackColor() {
        return defaultBackColor;
    }

    @Override
    public boolean isFinal() {
        return true;
    }

}
