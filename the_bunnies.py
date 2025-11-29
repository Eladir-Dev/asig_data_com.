import random
import math

def generate_matrix(rows, cols, obstacle_rate=0.45):
    matrix = [[0]*cols for _ in range(rows)]
    for r in range(rows):
        for c in range(cols):
            if random.random() < obstacle_rate and not (r == 0 and c == 0):
                matrix[r][c] = 1
    return matrix

def dynamic_programming_path(matrix):
    rows = len(matrix)
    cols = len(matrix[0])

    dp = [[math.inf]*cols for _ in range(rows)]
    dp[0][0] = 0  # starting point

    # Build the DP table
    for r in range(rows):
        for c in range(cols):
            if matrix[r][c] == 1: 
                continue  # obstacle → skip

            if r > 0:
                dp[r][c] = min(dp[r][c], dp[r-1][c] + 1)
            if c > 0:
                dp[r][c] = min(dp[r][c], dp[r][c-1] + 1)
            if r > 0 and c > 0:
                dp[r][c] = min(dp[r][c], dp[r-1][c-1] + 1)

    # If destination is unreachable
    if dp[rows-1][cols-1] == math.inf:
        return None, dp

    # Reconstruct path
    path = []
    r, c = rows-1, cols-1

    while not (r == 0 and c == 0):
        path.append((r,c))
        moves = []

        if r > 0: moves.append((dp[r-1][c], r-1, c))
        if c > 0: moves.append((dp[r][c-1], r, c-1))
        if r > 0 and c > 0: moves.append((dp[r-1][c-1], r-1, c-1))

        best = min(moves, key=lambda x: x[0])
        r, c = best[1], best[2]

    path.append((0,0))
    path.reverse()

    return path, dp

# -----------------------------
# Example Usage
# -----------------------------

rows, cols = 5, 4
matrix = generate_matrix(rows, cols)

print("\nInitial matrix:")
for row in matrix:
    print(row)

path, dp = dynamic_programming_path(matrix)

if path is None:
    print("\nNo optimal path exists ❌")
else:
    print("\nOptimal Path :")
    print(path)
