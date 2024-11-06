package org.example;

public class Main {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    //
    public static Integer[][] sudoku = {
            {5, null, 1, 7, null, 2, null, 8, 4},
            {null, null, 9, 3, null, 1, null, 5, 7},
            {8, 2, null, 4, null, null, 6, null, null},
            {4, null, 6, null, 1, 7, 8, null, null},
            {2, 1, null, null, null, 6, null, null, null},
            {null, 5, null, null, null, null, null, 9, 6},
            {null, 6, null, 1, 4, null, null, null, null},
            {1, null, null, 6, null, null, 4, 2, null},
            {null, 7, 4, 2, 9, null, 5, null, null}
    };

    public static void main(String[] args) {
        printSudoku();
        if (resolveOSudoku()) {
            System.out.println("\nTa resolvido papai!");
        } else {
            System.out.println("\nDeu ruim, não tem solução.");
        }
    }

    public static boolean resolveOSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (sudoku[i][j] == null) { // Se a celula for vazia
                    for (int num = 1; num <= 9; num++) { // Tentar números de 1 a 9
                        if (validador(i, j, num)) { // Verificar se o numero e valido
                            sudoku[i][j] = num;  // Atribui o número

                            if (resolveOSudoku()) {  // Recursão: tentar resolver o Sudoku
                                return true; // Se resolver, retorna true
                            }

                            sudoku[i][j] = null; // Se nao for valido, desfaz a tentativa
                        } else {
                            // Exibe a tentativa incorreta em vermelho
                            System.out.print(ANSI_RED + num + ANSI_RESET + " | ");
                            espera();
                        }
                    }
                    return false;  // Se não encontrar uma soluçao válida, retorna false
                }
            }
            limpaTela();
            printSudoku();
            espera();
        }
        return true;  // Se todas as células foram preenchidas corretamente
    }

    // Função para validar se o número pode ser colocado na posição (i, j)
    public static boolean validador(int i, int j, int num) {
        return !verificadorLinha(i, num) && !verificadorColuna(j, num) && !verificadorGrade(i, j, num);
    }

    // Verifica se o número já está na linha
    public static boolean verificadorLinha(int i, int num) {
        for (int x = 0; x < 9; x++) {
            if (sudoku[i][x] != null && sudoku[i][x] == num) {
                return true; // Número encontrado na linha
            }
        }
        return false;
    }

    // Verifica se o número já está na coluna
    public static boolean verificadorColuna(int j, int num) {
        for (int x = 0; x < 9; x++) {
            if (sudoku[x][j] != null && sudoku[x][j] == num) {
                return true; // Número encontrado na coluna
            }
        }
        return false;
    }

    // Verifica se o número já está na 3x3 garante que o numero nao esta repedido na mesma grade
    public static boolean verificadorGrade(int i, int j, int num) {
        int linhaInicial = i / 3 * 3; // Determina a linha inicial da subgrade
        int colunaInicial = j / 3 * 3; // Determina a coluna inicial da subgrade

        for (int linha = linhaInicial; linha < linhaInicial + 3; linha++) {
            for (int coluna = colunaInicial; coluna < colunaInicial + 3; coluna++) {
                if (sudoku[linha][coluna] != null && sudoku[linha][coluna] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    // Função para imprimir o Sudoku
    public static void printSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (sudoku[i][j] != null) {
                    System.out.print(ANSI_GREEN + sudoku[i][j] + ANSI_RESET + " | ");
                } else {
                    System.out.print("  | ");
                }
            }
            System.out.println();
        }
    }

    public static void limpaTela() {
        try {
            String sistema = System.getProperty("os.name");

            if (sistema.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Erro ao limpar o terminal: " + e.getMessage());
        }
    }

    public static void espera(){
        try {
            // Espera de 500 milissegundos (meio segundo)
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
