package org.example;

import java.util.ArrayList;
import java.util.List;

public class Mainv2 {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

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

    public static int tentaticas = 0;  // Contador de tentativas global

    public static void main(String[] args) {
        printSudoku();
        if (resolveOSudoku()) {
            System.out.println("\nTa resolvido papai! Tentativas: " + tentaticas);  // Exibe o número total de tentativas
        } else {
            System.out.println("\nDeu ruim, não tem solução.");
        }
    }

    // Função para resolver o Sudoku priorizando as células com menos candidatos (mais preenchidas)
    public static boolean resolveOSudoku() {
        // Encontre a célula com menos candidatos
        int i = -1, j = -1;
        int minCandidatos = 10;  // Mais do que o máximo possível de candidatos (9)
        for (int linha = 0; linha < sudoku.length; linha++) {
            for (int coluna = 0; coluna < sudoku[linha].length; coluna++) {
                if (sudoku[linha][coluna] == null) {  // Só verificar células vazias
                    List<Integer> candidatos = calcularCandidatos(linha, coluna);
                    if (candidatos.size() < minCandidatos) {
                        minCandidatos = candidatos.size();
                        i = linha;
                        j = coluna;
                    }
                }
            }
        }

        // Se não tiver vazias, o Sudoku está resolvido
        if (i == -1 && j == -1) {
            System.out.println("resolvido com: " + tentaticas + " tentativas");  // Exibe o número total de tentativas
            return true;
        }

        // Tentar preencher a célula com os candidatos encontrados
        List<Integer> candidatos = calcularCandidatos(i, j);
        System.out.println("Candidatos para a posição (" + i + "," + j + "): " + candidatos);  // Exibe os candidatos

        for (int num : candidatos) {
            tentaticas++;  // Incrementa a tentativa
            if (!validador(i, j, num)) {  // Se o número não for válido, exibe ele
                System.out.println(ANSI_RED + "Número inválido: " + num + ANSI_RESET);
            } else {
                sudoku[i][j] = num;
                limpaTela();
                printSudoku();  // Imprime o estado atual do Sudoku

                espera();

                if (resolveOSudoku()) {  // Recursão para continuar resolvendo
                    return true;
                }

                espera();
                // Se não funcionar, desfaz a tentativa
                sudoku[i][j] = null;
            }
        }

        return false;  // Se não conseguiu resolver
    }

    // Função para calcular os candidatos válidos para a célula (i, j)
    public static List<Integer> calcularCandidatos(int i, int j) {
        List<Integer> candidatos = new ArrayList<>();
        for (int x = 1; x <= 9; x++) {
            if (validador(i, j, x)) {
                candidatos.add(x);
            }
        }
        return candidatos;
    }

    // Função para validar se o número pode ser colocado na posição (i, j)
    public static boolean validador(int i, int j, int num) {
        return !verificadorLinha(i, num) && !verificadorColuna(j, num) && !verificadorGrade(i, j, num);
    }

    // Verifica se o número já está na linha
    public static boolean verificadorLinha(int i, int num) {
        for (int x = 0; x < 9; x++) {
            if (sudoku[i][x] != null && sudoku[i][x] == num) {
                return true;  // Número encontrado na linha
            }
        }
        return false;
    }

    // Verifica se o número já está na coluna
    public static boolean verificadorColuna(int j, int num) {
        for (int x = 0; x < 9; x++) {
            if (sudoku[x][j] != null && sudoku[x][j] == num) {
                return true;  // Número encontrado na coluna
            }
        }
        return false;
    }

    // Verifica se o número já está na 3x3
    public static boolean verificadorGrade(int i, int j, int num) {
        int linhaInicial = i / 3 * 3;
        int colunaInicial = j / 3 * 3;

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

    // Função para limpar a tela
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

    // Função para simular uma pausa
    public static void espera() {
        try {
            Thread.sleep(0);  // Pausa de 500 milissegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
