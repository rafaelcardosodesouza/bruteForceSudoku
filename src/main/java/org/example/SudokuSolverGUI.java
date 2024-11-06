package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolverGUI extends JFrame {
    private static final int GRID_SIZE = 9; // Tamanho do Sudoku 9x9
    private static Integer[][] sudoku = {
            {null, null, null, null, null, null, null, null, null},
            {null, null, 2, 9, null, null, null, 5, 3},
            {1, null, 7, null, null, null, 6, null, 2},
            {null, 6, null, null, 9, null, 5, null, null},
            {8, 3, null, null, null, 7, null, null, 6},
            {null, null, 1, 8, null, null, null, null, null},
            {null, null, null, 5, null, 1, null, 4, null},
            {null, null, null, null, 2, null, 7, null, null},
            {null, 4, null, null, null, null, null, null, null}
    };
    private JTextField[][] gridCells = new JTextField[GRID_SIZE][GRID_SIZE];

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        initializeGrid();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Inicia a resolução do Sudoku em uma nova thread para não travar a interface gráfica
        new Thread(this::solveSudoku).start();
    }

    private void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridCells[i][j] = new JTextField();
                gridCells[i][j].setHorizontalAlignment(JTextField.CENTER);
                gridCells[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                gridCells[i][j].setEditable(false);
                if (sudoku[i][j] != null) {
                    gridCells[i][j].setText(String.valueOf(sudoku[i][j]));
                    gridCells[i][j].setForeground(Color.GREEN);
                }
                add(gridCells[i][j]);
            }
        }
    }

    private boolean solveSudoku() {
        // Encontrar célula vazia com menos candidatos
        int minRow = -1, minCol = -1;
        int minCandidates = 10;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (sudoku[i][j] == null) {
                    List<Integer> candidates = getCandidates(i, j);
                    if (candidates.size() < minCandidates) {
                        minCandidates = candidates.size();
                        minRow = i;
                        minCol = j;
                    }
                }
            }
        }

        if (minRow == -1 && minCol == -1) return true; // Sudoku resolvido

        for (int num : getCandidates(minRow, minCol)) {
            if (isValid(minRow, minCol, num)) {
                sudoku[minRow][minCol] = num;
                gridCells[minRow][minCol].setText(String.valueOf(num));
                gridCells[minRow][minCol].setForeground(Color.BLUE);

                // Pausa para mostrar a tentativa
                try { Thread.sleep(10); } catch (InterruptedException ignored) {}

                if (solveSudoku()) return true;

                // Remove a tentativa se não funcionar
                sudoku[minRow][minCol] = null;
                gridCells[minRow][minCol].setText("");
            }
        }
        return false; // Não conseguiu resolver
    }

    private List<Integer> getCandidates(int row, int col) {
        List<Integer> candidates = new ArrayList<>();
        for (int num = 1; num <= GRID_SIZE; num++) {
            if (isValid(row, col, num)) candidates.add(num);
        }
        return candidates;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (sudoku[row][i] != null && sudoku[row][i] == num) return false;
            if (sudoku[i][col] != null && sudoku[i][col] == num) return false;
            int boxRow = 3 * (row / 3) + i / 3;
            int boxCol = 3 * (col / 3) + i % 3;
            if (sudoku[boxRow][boxCol] != null && sudoku[boxRow][boxCol] == num) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolverGUI::new);
    }
}
