# Resolvedor de Sudoku em Java

Este programa Java resolve um quebra-cabeça Sudoku usando backtracking. O código implementa um algoritmo de busca recursiva para encontrar a solução.

## Como funciona

O algoritmo funciona da seguinte maneira:

1. **Encontra uma célula vazia:** O programa procura por uma célula vazia no tabuleiro `sudoku`, representada por um valor `null`.
2. **Tenta um número:** Para cada célula vazia, o programa tenta preencher com um número de 1 a 9.
3. **Valida o número:** A função `validador(i, j, num)` verifica se o número é válido de acordo com as regras do Sudoku:
    - `verificadorLinha(i, num)`: Verifica se o número já existe na linha `i`.
    - `verificadorColuna(j, num)`: Verifica se o número já existe na coluna `j`.
    - `verificadorGrade(i, j, num)`: Verifica se o número já existe na grade 3x3 correspondente à célula.
4. **Avança ou retrocede:**
   - Se o número é válido, o programa o atribui à célula (`sudoku[i][j] = num`) e chama recursivamente `resolveOSudoku()` para tentar resolver o restante do tabuleiro. Se a chamada recursiva retornar `true`, significa que o Sudoku foi resolvido.
   - Se o número não é válido, o programa tenta outro número. Se nenhum número de 1 a 9 é válido, a função retorna `false`, indicando que a tentativa anterior deve ser desfeita (backtracking).
5. **Solução encontrada:** O programa continua esse processo até que todas as células estejam preenchidas com números válidos, o que significa que o Sudoku foi resolvido.


## Detalhes do código

- **Representação do Sudoku:** O Sudoku é representado por uma matriz de inteiros `sudoku[][]`, onde `null` representa uma célula vazia.
- **Cores no console:** O programa usa códigos ANSI para imprimir os números no console com cores:
    - `ANSI_GREEN`: Números corretos são impressos em verde.
    - `ANSI_RED`: Tentativas incorretas são impressas em vermelho.
    - `ANSI_RESET`: Redefine a cor para a padrão do console.
- **Visualização:** A função `printSudoku()` imprime o tabuleiro no console.
- **Limpeza da tela:** A função `limpaTela()` limpa o console a cada passo para uma melhor visualização.
- **Pausa:** A função `espera()` introduz um atraso para que você possa acompanhar o processo de resolução.

## Como executar

1. **Salve o código:** Salve o código como um arquivo `.java` (por exemplo, `SudokuSolver.java`).
2. **Compile o código:** Abra um terminal ou prompt de comando e compile o código usando o comando `javac SudokuSolver.java`.
3. **Execute o código:** Execute o código compilado usando o comando `java SudokuSolver`.

## Personalização

- **Dificuldade:** Você pode alterar a dificuldade do Sudoku modificando o tabuleiro inicial `sudoku` no código. Quanto mais números preenchidos inicialmente, mais fácil será o Sudoku.
- **Velocidade:** Você pode ajustar a velocidade de resolução alterando o tempo de espera na função `espera()`.

## Observações

- O programa foi projetado para resolver Sudokus com uma única solução.
- O programa pode levar algum tempo para resolver Sudokus muito difíceis.


## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
