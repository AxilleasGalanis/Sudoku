package sudoku.sudokuPackage;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.awt.event.*; 
import sudoku.tiles.*;
import sudoku.color.*;

public class sudokuActions {
    private sudokuFrame frame;
    private sudokuGrid grid;
    private Deque<command> track;
    private final Color SELECTION = new Color(255, 255, 191);
    private final Color ERROR = new Color(255, 0, 0);
    private Map<JTextField, viewTile> textTileMap;
    private JTextField selected;
    private char keyChar = '\0';
    
    public sudokuActions(sudokuFrame frame, sudokuGrid grid) {
        this.frame = frame;
        this.grid = grid;
        
        frame.addEasyListener(new GameOptionListener("sudoku/level/easy.txt"));
        frame.addMediumListener(new GameOptionListener("sudoku/level/medium.txt"));
        frame.addHardListener(new GameOptionListener("sudoku/level/hard.txt"));
        frame.addFieldsListener(new FieldsListener());
        frame.addNumberListener(new NumbersListener());
        frame.addSolutionListener(new SolutionListener());
        frame.addEraseListener(new EraseActionListener());
        frame.addVerificationListener(new VerificationListener());
        frame.addUndoListener(new UndoListener());
    }
    
    private void updateColors() {
	textTileMap.values().forEach(viewTile -> viewTile.getText().setBackground(viewTile.getBackColor()));
    }
    
    public void readyGrid(sudokuTile[][] tiles) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                viewTile viewTile = new viewTile(frame.squares[i][j]);
                viewTile.setTile(tiles[i][j]);
                textTileMap.put(frame.squares[i][j], viewTile);
                frame.squares[i][j].setFocusable(true);
                textTileMap.put(frame.squares[i][j], viewTile);
            }
            frame.buttons[i].setEnabled(true);
        }

        frame.solveButton.setEnabled(true);
        frame.verifyButton.setSelected(false);
        frame.verifyButton.setEnabled(true);
    }
    
    private int[][] parseInput(InputStream input) {
        Scanner in = new Scanner(input);
        String s = in.nextLine();
        int counter = 0;
        char[] temp = s.toCharArray();
        int[][] array = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (temp[counter] == '.')
                    array[i][j] = 0;
                else
                    array[i][j] = Character.getNumericValue(temp[counter]);
                counter++;
            }
            counter++;
        }
       
        in.close();
        return array;
    }
    
    private class GameOptionListener implements ActionListener {
        private final String file;

        public GameOptionListener(String file) {
            this.file = file;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            int[][] input = parseInput(stream);
            track = new LinkedList<>();
            textTileMap = new HashMap<>();
            grid.putTilesInGrid(input);
            readyGrid(grid.getTiles());
        }
    }
    
    private class FieldsListener extends KeyAdapter implements FocusListener {
        
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_2:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_3:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_4:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_5:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_6:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_7:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_8:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_9:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_DELETE:keyChar = e.getKeyChar(); break;
                case KeyEvent.VK_BACK_SPACE:keyChar = e.getKeyChar(); break;
            }
            
            if (e.getKeyCode() == 127 || e.getKeyCode() == 8) {
                if (textTileMap.get(selected).getSudokuTile().isFinal()) {
                    command erase = new eraseTile(grid, textTileMap.get(selected));
                    int i = textTileMap.get(selected).getSudokuTile().getRow();
                    int j = textTileMap.get(selected).getSudokuTile().getColumn();
                    sudokuTile newTile = new emptyTile(new solvedTile(0, (j / 3) + (i / 3) * 3, i, j));
                    textTileMap.get(selected).setTile(newTile);
                    grid.replaceTile(i, j, newTile);
                    track.push(erase);
                    updateColors();
                } else 
                    selected.setBackground(ERROR);
            }
            else {
                if (keyChar == '\0')
                    return ;

                updateColors();
                if (selected == null) {
                    return;
                }
                int newVal = Character.getNumericValue(keyChar);

                if (grid.hasAnyConflicts(textTileMap.get(selected).getSudokuTile(), newVal)) {
                    command change = new changeTile(grid, textTileMap.get(selected), newVal);
                    int i = textTileMap.get(selected).getSudokuTile().getRow();
                    int j = textTileMap.get(selected).getSudokuTile().getColumn();
                    sudokuTile newTile = new tempTile(textTileMap.get(selected).getSudokuTile(), newVal);
                    textTileMap.get(selected).setTile(newTile);
                    grid.replaceTile(i, j, newTile);

                    if (track.isEmpty())
                        frame.undoButton.setEnabled(true);

                    track.push(change);

                    if (grid.isSolved()) {
                        JFrame victoryPopup = new JFrame("WIN!");
                        JTextField victoryMessage = new JTextField("WIN!");
                        victoryMessage.setEditable(false);
                        victoryPopup.add(victoryMessage);
                        victoryPopup.setSize(200, 100);
                        victoryPopup.setVisible(true);
                        lockInterface();
                    }
                }
                else {
                    sudokuTile [][]conflict = grid.getAllConflicts(textTileMap.get(selected).getSudokuTile(), newVal);
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (conflict[i][j].getTile() != 0)
                                frame.squares[conflict[i][j].getRow()][conflict[i][j].getColumn()].setBackground(ERROR);
                        }
                    } 
                    selected.setBackground(SELECTION);
                }
            }
            keyChar = '\0';
        }
         

        @Override
        public void focusGained(FocusEvent e) {
            sudokuTile temp[][];
            updateColors();
            selected = (JTextField) e.getComponent();
            selected.addKeyListener(this);
            selected.setBackground(SELECTION);
            if (!selected.getText().isEmpty()) {
                    frame.eraseButton.setEnabled(true);
                    temp = grid.getMatchingValue(textTileMap.get(selected).getSudokuTile());
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (temp[i][j].getTile() != 0)
                               frame.squares[i][j].setBackground(SELECTION); 
                        }
                    }
            } else 
                frame.eraseButton.setEnabled(false);   
        }

        @Override
        public void focusLost(FocusEvent e) {
        }

    }
    
     private void lockInterface() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    frame.squares[i][j].setFocusable(false);
            }
            frame.buttons[i].setEnabled(false);
        }
        frame.verifyButton.setEnabled(false);
        frame.solveButton.setEnabled(false);
        frame.undoButton.setEnabled(false);
        frame.eraseButton.setEnabled(false);
    }
    
     
    private class NumbersListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            updateColors();
            if (selected == null) {
                return;
            }
            int newVal = Integer.parseInt(e.getActionCommand());
            
            if (grid.hasAnyConflicts(textTileMap.get(selected).getSudokuTile(), newVal)) {
                command change = new changeTile(grid, textTileMap.get(selected), newVal);
                int i = textTileMap.get(selected).getSudokuTile().getRow();
                int j = textTileMap.get(selected).getSudokuTile().getColumn();
                sudokuTile newTile = new tempTile(textTileMap.get(selected).getSudokuTile(), newVal);
                textTileMap.get(selected).setTile(newTile);
                grid.replaceTile(i, j, newTile);

                if (track.isEmpty())
                    frame.undoButton.setEnabled(true);

                track.push(change);
                
                if (grid.isSolved()) {
                    JFrame victoryPopup = new JFrame("WIN!");
                    JTextField victoryMessage = new JTextField("WIN!");
                    victoryMessage.setEditable(false);
                    victoryPopup.add(victoryMessage);
                    victoryPopup.setSize(200, 100);
                    victoryPopup.setVisible(true);
                    lockInterface();
                }
            }
            else {
                sudokuTile [][]conflict = grid.getAllConflicts(textTileMap.get(selected).getSudokuTile(), newVal);
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (conflict[i][j].getTile() != 0)
                            frame.squares[conflict[i][j].getRow()][conflict[i][j].getColumn()].setBackground(ERROR);
                    }
                } 
                selected.setBackground(SELECTION);
            }
        }
    }
    
    private class SolutionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            readyGrid(grid.getSolvedTiles());
            lockInterface();
        }
    }
    
    private class EraseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (textTileMap.get(selected).getSudokuTile().isFinal()) {
                command erase = new eraseTile(grid, textTileMap.get(selected));
                int i = textTileMap.get(selected).getSudokuTile().getRow();
                int j = textTileMap.get(selected).getSudokuTile().getColumn();
                sudokuTile newTile = new emptyTile(new solvedTile(0, (j / 3) + (i / 3) * 3, i, j));
                textTileMap.get(selected).setTile(newTile);
                grid.replaceTile(i, j, newTile);
                track.push(erase);
                updateColors();
            } else {
                selected.setBackground(ERROR);
            }
        }
    }
    
    private class VerificationListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            textTileMap.values().forEach(viewTile -> {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    viewTile.setBackColor(new verifiedColor(viewTile));
                else
                    viewTile.setBackColor(new normalColor(viewTile));
            });
            updateColors();
        }
    }
    
    private class UndoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!track.isEmpty()) {
                command com = track.pop();
                com.undo();
                if (track.isEmpty()) frame.undoButton.setEnabled(false);
                updateColors();
            }
        }
    }
}
