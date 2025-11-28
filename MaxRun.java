package paradigmas_asig_2;


public class MaxRun {
	public static void main(String[] args) {
		// The map that will be traveled.
		int[][] map = {
				{4, 8, 1, 3, 4},
				{2, 1, 4, 2, 4},
				{3, 4, 0, 1, 1},
				{5, 7, 3,-1, 3},
				{0, 3, 5, 9, 1},
				{1, 4, 8, 0, 1}
		};
		
		System.out.println("Original matrix:");
		printMatrix(map);
		System.out.println("Posible paths: " + posiblePaths(map));
		System.out.println("Shortest path:");
		printMatrix(shortestPath(map));
		System.out.println("1 = shortest path");
		
	}
	
	/**
	 * This method prints out a matrix.
	 * @param matrix - matrix to print out
	 */
	public static void printMatrix(int[][] matrix) {
        
        for (int[] row : matrix) {
        	System.out.print("{");
            for (int val : row) {
            	if (val >= 0) {
            		System.out.print(" ");
            	}
                System.out.print(val + " ");
            }
            System.out.print("}");
            System.out.println();
        }
    }
	/**
	 * Calculates the amount of possible paths from matrix[0][0] to matrix[m-1][n-1].
	 * Where m = columns and n = rows.
	 * @param matrix - Matrix to analysis
	 * @return Amount of possible paths.
	 */
	public static int posiblePaths(int[][] matrix) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		return factorial(cols + rows - 2) / (factorial(cols - 1) * factorial(rows-1));
	}
	/**
	 * Returns the factorial of a number.
	 * @param num - number to calculate
	 * @return factorial
	 */
	public static int factorial(int num) {
		int out=1;
		for (int i = out;i<=num;i++) {
			out *= i;
		}
		return out;
	}
	
	/**
	 * This method returns a matrix with 1s and 0s.
	 * 1 = path and 0 = not best path
	 * @param matrix - Matrix to find shortest path
	 * @return New matrix with solution
	 */
	public static int[][] shortestPath(int[][] matrix) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		int[][] solution = new int[rows][cols];
		// fills the solution matrix with 0.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                solution[r][c] = 0;
            }
        }
        
        return solution;
	}
}
