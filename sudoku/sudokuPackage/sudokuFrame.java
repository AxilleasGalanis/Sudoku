package sudoku.sudokuPackage;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;

public class sudokuFrame {
    JMenuItem easy;
    JMenuItem medium;
    JMenuItem hard;
    JButton eraseButton;
    JButton undoButton;
    JCheckBox verifyButton;
    JButton solveButton;
    JButton[] buttons = new JButton[9];
    JTextField[][] squares = new JTextField[9][9];
    JFrame frame;

     public sudokuFrame() {
        frame = new JFrame("Sudoku");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);

	JMenuBar menuBar = new JMenuBar();
	menuBar.setVisible(true);
	frame.setJMenuBar(menuBar);

	JMenu newGame = new JMenu("New Game");
	menuBar.add(newGame);

    easy = new JMenuItem("Easy");
	medium = new JMenuItem("Intermediate");
	hard = new JMenuItem("Expert");

        newGame.add(easy);
	newGame.add(medium);
	newGame.add(hard);
        
        JPanel upperPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        upperPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        Border border = BorderFactory.createRaisedBevelBorder();
        frame.add(upperPanel);
        
        JPanel[] grid = new JPanel[9];
        for (int i = 0; i < 9; i++) {
            grid[i] = new JPanel(new GridLayout(3, 3, 1, 1));
            grid[i].setBorder(border);
            upperPanel.add(grid[i]);
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                squares[i][j] = new JTextField("");
                squares[i][j].setHorizontalAlignment(JTextField.CENTER);
                squares[i][j].setBackground(Color.WHITE);
                Font font = new Font("Arial", Font.BOLD, 20);
                squares[i][j].setFont(font);
                grid[(j / 3) + (i / 3) * 3].add(squares[i][j]);
                squares[i][j].setEditable(false);
                squares[i][j].setFocusable(false);
            }
        }
        
        JPanel lowerPanel = new JPanel(new GridBagLayout());
        lowerPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 20, 5));
        GridBagConstraints c = new GridBagConstraints();
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton(String.valueOf(i + 1));
            buttons[i].setPreferredSize(new Dimension(50, 30));
            buttons[i].setMnemonic(97 + i);
            buttons[i].setEnabled(false);
            c.insets = new Insets(4, 4, 4, 4);
            if (i<8) {
                c.gridx = i;
                c.gridy = 0;
            }
            else {
                c.gridx = i-8;
                c.gridy = 1;
            }
            lowerPanel.add(buttons[i],c);
        }
        
        eraseButton = new JButton();
        try {
            Image eraser = ImageIO.read(getClass().getResource("/sudoku/images/eraser.png")).getScaledInstance(10, 15, Image.SCALE_DEFAULT);
            eraseButton.setIcon(new ImageIcon(eraser));
            eraseButton.setPreferredSize(new Dimension(50, 30));
            c.gridx = 1;
            c.gridy = 1;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        eraseButton.setEnabled(false);
        lowerPanel.add(eraseButton, c);
        
        undoButton = new JButton();
        try {
            Image undo = ImageIO.read(getClass().getResource("/sudoku/images/undo.png")).getScaledInstance(10, 15, Image.SCALE_DEFAULT);
            undoButton.setIcon(new ImageIcon(undo));
            undoButton.setPreferredSize(new Dimension(50, 30));
            c.gridx = 2;
            c.gridy = 1;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        undoButton.setEnabled(false);
        lowerPanel.add(undoButton, c);
        
        verifyButton = new JCheckBox("Verify against solution");
        Font font = new Font("Arial", Font.BOLD, 16);
        verifyButton.setFont(font);
        verifyButton.setEnabled(false);
	verifyButton.setRolloverEnabled(false);
        c.gridwidth = 4; 
        c.gridx = 3;
        c.gridy = 1;
	lowerPanel.add(verifyButton, c);
        
        solveButton = new JButton();
        try {
            Image solve = ImageIO.read(getClass().getResource("/sudoku/images/rubik.png")).getScaledInstance(10, 15, Image.SCALE_DEFAULT);
            solveButton.setIcon(new ImageIcon(solve));
            solveButton.setPreferredSize(new Dimension(50, 30));
            c.gridwidth = 0;
            c.gridx = 7;
            c.gridy = 1;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        solveButton.setEnabled(false);
        lowerPanel.add(solveButton, c);
        
        frame.add(lowerPanel, BorderLayout.PAGE_END);
        frame.pack();
    }
     
    public void addEasyListener(ActionListener listener) {
        easy.addActionListener(listener);
    }

    public void addMediumListener(ActionListener listener) {
        medium.addActionListener(listener);
    }

    public void addHardListener(ActionListener listener) {
        hard.addActionListener(listener);
    }

    public void addEraseListener(ActionListener listener) {
        eraseButton.addActionListener(listener);
    }

    public void addUndoListener(ActionListener listener) {
        undoButton.addActionListener(listener);
    }

    public void addVerificationListener(ItemListener listener) {
        verifyButton.addItemListener(listener);
    }

    public void addSolutionListener(ActionListener listener) {
        solveButton.addActionListener(listener);
    }

    public void addNumberListener(ActionListener listener) {
        for (int i = 0; i < 9; i++) 
                buttons[i].addActionListener(listener);
    }

    public void addFieldsListener(FocusListener listener) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                squares[i][j].addFocusListener(listener);
            }
        }
    }  
}