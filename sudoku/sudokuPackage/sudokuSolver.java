package sudoku.sudokuPackage;

import sudoku.tiles.*;

public class sudokuSolver {
    private sudokuTile[][] tiles;
    private int[][] inputFile;
    
    public sudokuSolver() {
	tiles = new sudokuTile [9][9];
    }
    
    public void compile() {
        sudokuTile tile;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (inputFile[i][j] == 0) {
                    tile = new emptyTile(new solvedTile(inputFile[i][j], (j / 3) + (i / 3) * 3, i, j));
                } else {
                    tile = new solvedTile(inputFile[i][j], (j / 3) + (i / 3) * 3, i, j);
		}
                tiles[i][j] = tile;
            }
        }
    }
    
    public sudokuTile[][] getSolution(int[][] input) {
	this.inputFile = input;
	compile();
	solveSudoku();
	return tiles;
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
    
    private boolean solveSudoku () {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tiles[i][j].getTile() == 0) {
                    for (int k = 1; k <= 9; k++) {
                        if (isValid(i, j, k)) {
                            sudokuTile tile = new solvedTile(k, (j / 3) + (i / 3) * 3, i, j);
                            tiles[i][j] = tile;
                            if (solveSudoku()) { // we start backtracking recursively
                                return true;
                            } else { // if not a solution, we empty the cell and we continue
                                tile = new emptyTile(new solvedTile(0, (j / 3) + (i / 3) * 3, i, j));
                                tiles[i][j] = tile;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true; // sudoku solved
    }
}
