def dp_longest_path(matrix):
    rows, cols = len(matrix), len(matrix[0])

    dp = [[float("-inf")] * cols for _ in range(rows)]
    parent = [[None] * cols for _ in range(rows)]

    dp[0][0] = matrix[0][0]

    for r in range(rows):
        for c in range(cols):

            # From up
            if r > 0 and dp[r][c] < dp[r-1][c] + matrix[r][c]:
                dp[r][c] = dp[r-1][c] + matrix[r][c]
                parent[r][c] = (r-1, c)

            # From left
            if c > 0 and dp[r][c] < dp[r][c-1] + matrix[r][c]:
                dp[r][c] = dp[r][c-1] + matrix[r][c]
                parent[r][c] = (r, c-1)

    # ---- SAFE PATH RECONSTRUCTION ----
    path_matrix = [[0] * cols for _ in range(rows)]
    r, c = rows - 1, cols - 1

    while True:
        path_matrix[r][c] = 1

        if parent[r][c] is None:
            break  # reached the start safely

        r, c = parent[r][c]

    return dp[-1][-1], path_matrix


def print_matrix(mat):
    for row in mat:
        print(row)

matrix = [
    [4, 8, 1, 3, 4],
    [2, 1, 4, 2, 4],
    [3, 4, 0, 1, 1],
    [5, 7, 3,-1, 3],
    [0, 3, 5, 9, 1],
    [1, 4, 8, 0, 1]
]

cost_long,  path_long  = dp_longest_path(matrix)

print("\nLongest DP Path:")
print_matrix(path_long)
print("Cost:", cost_long)
print("\n")


