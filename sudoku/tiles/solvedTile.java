package sudoku.tiles;
import sudoku.tiles.sudokuTile;
import java.awt.*;

public class solvedTile implements sudokuTile{
    
    private int sol;
    private int gridNum;
    private int row;
    private int col;
    private final Color defaultBackColor;
    
    public solvedTile(int sol, int gridNum, int row, int col) {
        this.sol = sol;
        this.gridNum = gridNum;
        this.row = row;
        this.col = col;
        this.defaultBackColor = new Color(235,235,235);
    }
    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }
    
    public int getTile() {
        return sol;
    }

    public int getGrid() {
        return gridNum;
    }
    
    public int getSolvedTile() {
        return sol;
    }
    
    @Override
    public String getNum(){
        return String.valueOf(sol);
    }

    public Color getDefaultBackColor() {
	return defaultBackColor;
    }

    @Override
    public boolean isFinal(){
        return false;
    }
}
