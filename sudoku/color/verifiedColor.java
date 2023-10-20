package sudoku.color;

import java.awt.*;
import sudoku.tiles.*;

public class verifiedColor implements backColor {

    private final viewTile tile;

    public verifiedColor(viewTile tile) {
        this.tile = tile;
    }

    @Override
    public Color backColor() {
        if (tile.getTile() != 0 && tile.getTile() != tile.getSolvedTile())
            return Color.blue;
        
        return tile.getDefaultBackColor();
    }
}
