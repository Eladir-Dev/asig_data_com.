
public class BunnyMaze {

    private final int[][] maze;
    private final int rows;
    private final int cols;

    public BunnyMaze(int[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
    }

    // Devuelve la matriz solución con:
    //  - 0: obstáculo
    //  - 1: celdas del camino elegido
    //  - 2: celdas libres no utilizadas
    // Si no hay camino, devuelve null.
    public int[][] solve() {
        if (rows == 0 || cols == 0 || maze[0][0] == 0) {
            return null;
        }

        boolean[][] dp = new boolean[rows][cols];
        // prev[i][j] = 0 (viene de arriba), 1 (viene de izquierda), -1 (no definido)
        int[][] prev = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                prev[i][j] = -1;
            }
        }

        dp[0][0] = (maze[0][0] == 1);

        // Primera fila (solo podemos venir de la izquierda)
        for (int j = 1; j < cols; j++) {
            if (maze[0][j] == 1 && dp[0][j - 1]) {
                dp[0][j] = true;
                prev[0][j] = 1; // izquierda
            }
        }

        // Primera columna (solo podemos venir de arriba)
        for (int i = 1; i < rows; i++) {
            if (maze[i][0] == 1 && dp[i - 1][0]) {
                dp[i][0] = true;
                prev[i][0] = 0; // arriba
            }
        }

        // Resto de la tabla
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (maze[i][j] == 1) {
                    if (dp[i - 1][j]) {
                        dp[i][j] = true;
                        prev[i][j] = 0; // viene de arriba
                    } else if (dp[i][j - 1]) {
                        dp[i][j] = true;
                        prev[i][j] = 1; // viene de izquierda
                    }
                    // Si ninguna es true, dp[i][j] queda false
                }
            }
        }

        if (!dp[rows - 1][cols - 1]) {
            return null; // no hay camino
        }

        // Reconstruir un camino desde (rows-1, cols-1) hacia (0,0)
        int[][] solution = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                solution[i][j] = (maze[i][j] == 0) ? 0 : 0; // por claridad
            }
        }
        int i = rows - 1, j = cols - 1;
        while (i != 0 || j != 0) {
            solution[i][j] = 1;
            switch (prev[i][j]) {
                case 0 -> // arriba
                    i = i - 1;
                case 1 -> // izquierda
                    j = j - 1;
                default -> {
                    // Defensive: por si algo raro pasó
                    return null;
                }
            }
        }
        solution[0][0] = 1;

        // Marcar celdas libres no usadas como 2
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (maze[r][c] == 1 && solution[r][c] == 0) {
                    solution[r][c] = 2;
                }
            }
        }

        return solution;
    }

    public static void print(int[][] matrix) {
        if (matrix == null) {
            System.out.println("Solution doesn't exist");
            return;
        }
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(" " + val + " ");
            }
            System.out.println();
        }
        System.out.println("1 = camino, 0 = obstaculo, 2 = libre no usado");
    }

    public static void main(String[] args) {
        int[][] rabbitDen = {
            {1, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 1, 0},
            {0, 1, 1, 1},
            {0, 0, 0, 1}
        };

        BunnyMaze solver = new BunnyMaze(rabbitDen);
        int[][] solution = solver.solve();
        print(solution);
    }
}

