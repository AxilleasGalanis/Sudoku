package sudoku.tiles;
import sudoku.color.*;
import javax.swing.*;
import java.awt.*;

public class viewTile {
    
    private final JTextField textField;
    backColor state;
    private sudokuTile tile;

    public viewTile(JTextField textField) {
        this.textField = textField;
        state = new normalColor(this);
    }

    public int getTile() {
        return tile.getTile();
    }

    public int getSolvedTile() {
        return tile.getSolvedTile();
    }

    public String getNum() {
        return tile.getNum();
    }

    public Color getDefaultBackColor() {
        return tile.getDefaultBackColor();
    }

    public Color getBackColor() {
        return state.backColor();
    }

    public void setBackColor(backColor state) {
        this.state = state;
    }

    public void setTile(sudokuTile tile) {
        this.tile = tile;
        textField.setText(this.getNum());
        textField.setBackground(this.getBackColor());
    }

    public sudokuTile getSudokuTile() {
        return tile;
    }

    public JTextField getText() {
        return textField;
    }
}