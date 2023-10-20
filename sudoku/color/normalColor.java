package sudoku.color;
import java.awt.*;
import sudoku.tiles.viewTile;

public class normalColor implements backColor {

    private final viewTile tile;

    public normalColor(viewTile tile) {
        this.tile = tile;
    }

    @Override
    public Color backColor() {
        return tile.getDefaultBackColor();
    }
}
