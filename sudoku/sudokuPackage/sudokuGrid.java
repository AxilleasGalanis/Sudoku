package sudoku.sudokuPackage;

import sudoku.tiles.*;

public class sudokuGrid {
    public sudokuSolver solver;
    private sudokuTile[][] tiles = new sudokuTile [9][9];
    
    public sudokuGrid(sudokuSolver solver) {
	this.solver = solver;
    }
    
    public void putTilesInGrid(int input[][]) {
        sudokuTile tile;
        sudokuTile[][] solutionTiles = solver.getSolution(input);
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == 0) {
                    tile = new emptyTile(solutionTiles[i][j]);
                } else {
                    tile = solutionTiles[i][j];
		}
                tiles[i][j] = tile;
            }
        }
    }
    
    public void replaceTile(int row, int column, sudokuTile newTile) {
	tiles[row][column] = newTile;
    }
    
    public sudokuTile[][] getTiles() {
	return tiles;
    }
    
    public sudokuTile[][] getSolvedTiles () {
        sudokuTile [][] temp = new sudokuTile [9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { 
                temp[i][j] = new solvedTile(tiles[i][j].getSolvedTile(), tiles[i][j].getGrid(), tiles[i][j].getRow(), tiles[i][j].getColumn());
            }
        }
        return temp;
    }
    
    public sudokuTile[][] getMatchingValue(sudokuTile tile) {
        sudokuTile [][] temp = new sudokuTile[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { 
                if (tiles[i][j].getTile() == tile.getTile()) 
                    temp[i][j] = tile;
                else
                    temp[i][j] = new emptyTile(new solvedTile(0, (j / 3) + (i / 3) * 3, i, j));
            }
        }
        
        return temp;
    }
    
    private boolean Row(int row, int number) {
        for (int i = 0; i < 9; i++) {  
            if (tiles[row][i].getTile() == number)
                return true;
        }
        return false;
    }

    // we check if a possible number is already in a column
    private boolean Col(int col, int number) {
        for (int i = 0; i < 9; i++)
            if (tiles[i][col].getTile() == number)
                return true;

        return false;
    }

    // we check if a possible number is in its 3x3 box
    private boolean Grid(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (tiles[i][j].getTile() == number)
                    return true;

        return false;
    }
        
    private boolean isValid(int row, int column, int number) {
        return !Row(row, number)  &&  !Col(column, number)  &&  !Grid(row, column, number);
    }
    
    public sudokuTile [][] getAllConflicts(sudokuTile tile, int val) {
	sudokuTile [][] temp = new sudokuTile[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { 
                temp[i][j] = new emptyTile(new solvedTile(0, (j / 3) + (i / 3) * 3, i, j));
            }
        }

        for (int i = 0; i < 9; i++)
            if (tiles[i][tile.getColumn()].getTile() == val) 
                temp[i][tile.getColumn()] = new tempTile(new solvedTile(val, (tile.getColumn() / 3) + (i / 3) * 3, i, tile.getColumn()), val);
 
            
            
        for (int i = 0; i < 9; i++)  
            if (tiles[tile.getRow()][i].getTile() == val)
                temp[tile.getRow()][i] = new tempTile(new solvedTile(val, (i / 3) + (tile.getRow() / 3) * 3, tile.getRow(), i), val);
        
        int r = tile.getRow() - tile.getRow() % 3;
        int c = tile.getColumn() - tile.getColumn() % 3;

        
        for (int k = r; k < r + 3; k++)
            for (int l = c; l < c + 3; l++)
                if (tiles[k][l].getTile() == val)
                    temp[k][l] = new tempTile(new solvedTile(val, (l / 3) + (k / 3) * 3, k, l), val);
            
        return temp;
    }
    
    public boolean hasAnyConflicts(sudokuTile tile, int val) {
        if (isValid(tile.getRow(), tile.getColumn(), val))
            return true;
   
        return false;
    }
    
    public boolean isSolved() {
	for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { 
                if (tiles[i][j].getTile() == tiles[i][j].getSolvedTile()) {
                    
                }
                else 
                    return false;
            }
        }
        return true;
    }
}
