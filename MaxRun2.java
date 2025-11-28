public class MaxRun2 {

    private final int[][] cost;   // Matriz de costos
    private final int rows;       // Numero de filas
    private final int cols;       // Numero de columnas
    private int maxCost;          // Costo máximo del camino

    // Constructor
    public MaxRun2(int[][] cost) {
        this.cost = cost;
        this.rows = cost.length;
        this.cols = cost[0].length;
        this.maxCost = 0;
    }

    // Devuelve la matriz solucion con:
    //  - 1: celdas del camino elegido (máximo costo)
    //  - 2: celdas libres no utilizadas
    //  - 0: no usado aqui (no hay obstáculos), se deja como relleno
    // Tambien fija maxCost con el valor optimo.
    public int[][] solve() {
        if (rows == 0 || cols == 0) {
            return null;
        }

        int[][] dp = new int[rows][cols];      // DP con costo maximo hasta cada celda
        int[][] prev = new int[rows][cols];    // 0: arriba, 1: izquierda, 2: diagonal, -1: sin previo

        // Inicializar prev con -1
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                prev[i][j] = -1;
            }
        }

        // Base: celda inicial
        dp[0][0] = cost[0][0];

        // Primera fila: solo podemos venir desde la izquierda
        for (int j = 1; j < cols; j++) {
            dp[0][j] = cost[0][j] + dp[0][j - 1];
            prev[0][j] = 1; // izquierda
        }

        // Primera columna: solo podemos venir desde arriba
        for (int i = 1; i < rows; i++) {
            dp[i][0] = cost[i][0] + dp[i - 1][0];
            prev[i][0] = 0; // arriba
        }

        // Resto de la tabla: max entre arriba, izquierda y diagonal
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                int fromTop = dp[i - 1][j];
                int fromLeft = dp[i][j - 1];
                int fromDiag = dp[i - 1][j - 1];

                // Elegir la mejor fuente
                int best = fromTop;
                int source = 0; // arriba

                if (fromLeft > best) {
                    best = fromLeft;
                    source = 1; // izquierda
                }
                if (fromDiag > best) {
                    best = fromDiag;
                    source = 2; // diagonal
                }

                dp[i][j] = cost[i][j] + best;
                prev[i][j] = source;
            }
        }

        // Guardar costo maximo del destino
        maxCost = dp[rows - 1][cols - 1];

        // Reconstruir un camino óptimo desde el final hacia el inicio
        int[][] solution = new int[rows][cols]; // 0 por defecto
        int i = rows - 1, j = cols - 1;
        while (true) {
            solution[i][j] = 1; // marcar parte del camino
            if (i == 0 && j == 0) break; // llegamos al inicio

            int p = prev[i][j];
            switch (p) {
                case 0 -> // arriba
                    i = i - 1;
                case 1 -> // izquierda
                    j = j - 1;
                case 2 -> {
                    // diagonal
                    i = i - 1;
                    j = j - 1;
                }
                default -> {
                    // Defensive: en teoria no deberia ocurrir
                    return null;
                }
            }
        }

        // Marcar celdas libres no usadas como 2
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (solution[r][c] == 0) {
                    solution[r][c] = 2;
                }
            }
        }

        return solution;
    }

    // Devuelve el costo maximo calculado por solve()
    public int getMaxCost() {
        return maxCost;
    }

    // Imprime una matriz con formato simple
    public static void printMatrix(int[][] matrix) {
        if (matrix == null) {
            System.out.println("Solution doesn't exist");
            return;
        }
        for (int[] row : matrix) {
            for (int v : row) {
                System.out.print(" " + v + " ");
            }
            System.out.println();
        }
        System.out.println("1 = camino optimo, 2 = celdas no usadas");
    }

    // Demo con la matriz del enunciado
    public static void main(String[] args) {
        int[][] costMatrix = {
            {4, 8, 1, 3, 4},
            {2, 1, 4, 2, 4},
            {3, 4, 0, 1, 1},
            {5, 7, 3, -1, 3},
            {0, 3, 5, 9, 1},
            {1, 4, 8, 0, 1}
        };

        MaxRun2 solver = new MaxRun2(costMatrix);
        int[][] solution = solver.solve();
        printMatrix(solution);
        System.out.println("Costo maximo del camino: " + solver.getMaxCost());
    }
}
