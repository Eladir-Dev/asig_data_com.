package paradigmas_asig_2;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a cost matrix (N x M) the program returns the maximum cost path 
 * to reach corner (N-1) x (M-1) starting at (0, 0).  Each cell has a cost 
 * that represents traversing through that cell.  Total cost of a path is 
 * the sum of all costs along the path.  It can only travel down, right 
 * from a given cell.  It cannot travel backup from a cell.  
 */
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
		System.out.println("Longest path:");
		int[][] solution = longestPath(map);
		printMatrix(solution);
		System.out.println("1 = longest path");
		System.out.println("pathCost: " + getPathCost(solution, map));
		
	}
	/**
	 * Returns the cost of a path
	 * @param solution - a matrix were "1" marks the path
	 * @param matrix - matrix with the travel cost
	 * @return path cost
	 */
	private static int getPathCost(int[][] solution, int[][] matrix) {
		int rows = solution.length;
		int cols = solution[0].length;
		int pathCost = 0;
		
		for (int i = 0;i<rows;i++) {
			for (int j = 0;j<cols;j++) {
				if (solution[i][j] == 1) {
					pathCost += matrix[i][j];
				}
			}
		}
		return pathCost;
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
        
        
        List<String> paths = new ArrayList<>(); // all possible paths
        getPaths(0,0,rows, cols, "", paths);
        
        int bestPathCost = Integer.MAX_VALUE;
        for (String path : paths) {
        		int i=0,
        			j=0,
        			pathCost=0;
        		char[] tempPath = path.toCharArray();
        		
        		// fills tempSolution with 0
        		int[][] tempSolution = new int[rows][cols];
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        tempSolution[r][c] = 0;
                    }
                }
                
                tempSolution[i][j] = 1;
             pathCost += matrix[i][j];

            // travels the determined path
        		for (char direction : tempPath) {
        			switch (direction) {
        			case 'R':
        				if (j != cols-1) {
        					j++;
        					tempSolution[i][j] = 1;
        					pathCost += matrix[i][j];
        				}
        				break;
        			case 'D':
        				if (i != rows-1) {
        					i++;
        					tempSolution[i][j] = 1;
	    					pathCost += matrix[i][j];
        				}
        				break;
        			default: // if path string contains something other than 'R' and 'D'.
        				System.err.println("Error: Their was a problem finding the solution.");
        				System.err.println("Error val: " + direction);
        				System.exit(1);
        			}
        		}
        		if (bestPathCost > pathCost) {
        			bestPathCost = pathCost;
        			solution = tempSolution;
        		}
        }
        
        return solution;
	}
	/**
	 * This method returns a matrix with 1s and 0s.
	 * 1 = path and 0 = not best path
	 * @param matrix - Matrix to find longest path
	 * @return New matrix with solution
	 */
	public static int[][] longestPath(int[][] matrix) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		int[][] solution = new int[rows][cols];
		// fills the solution matrix with 0.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                solution[r][c] = 0;
            }
        }        
        
        
        List<String> paths = new ArrayList<>(); // all possible paths
        getPaths(0,0,rows, cols, "", paths);
        
        int maxPathCost = 0;
        for (String path : paths) {
        		int i=0,
        			j=0,
        			pathCost=0;
        		char[] tempPath = path.toCharArray();
        		
        		// fills tempSolution with 0
        		int[][] tempSolution = new int[rows][cols];
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        tempSolution[r][c] = 0;
                    }
                }
                
                tempSolution[i][j] = 1;
             pathCost += matrix[i][j];

            // travels the determined path
        		for (char direction : tempPath) {
        			switch (direction) {
        			case 'R':
        				if (j != cols-1) {
        					j++;
        					tempSolution[i][j] = 1;
        					pathCost += matrix[i][j];
        				}
        				break;
        			case 'D':
        				if (i != rows-1) {
        					i++;
        					tempSolution[i][j] = 1;
	    					pathCost += matrix[i][j];
        				}
        				break;
        			default: // if path string contains something other than 'R' and 'D'.
        				System.err.println("Error: Their was a problem finding the solution.");
        				System.err.println("Error val: " + direction);
        				System.exit(1);
        			}
        		}
        		if (maxPathCost < pathCost) {
        			maxPathCost = pathCost;
        			solution = tempSolution;
        		}
        }
        
        return solution;
	}
	/**
	 * This method find all the possible paths in a matrix.
	 * 'R' = moved right and 'D' = moved down.
	 * @param row - current row
	 * @param col - current column
	 * @param rows - amount of rows
	 * @param cols - amount of columns
	 * @param path - string that represents the current path being taken
	 * @param paths - a List<String> that contains all the possible paths
	 */
    private static void getPaths(int row, int col, int rows, int cols, String path, List<String> paths) {
        // Base case: reached bottom-right
        if (row == rows - 1 && col == cols - 1) {
            paths.add(path); // add the taken path to paths.
            return;
        }
        
        // Move Right
        if (col + 1 < cols) {
        		getPaths(row, col + 1, rows, cols, path + "R", paths);
        }
        
        // Move Down
        if (row + 1 < rows) {
        		getPaths(row + 1, col, rows, cols, path + "D", paths);
        }
    }

}
