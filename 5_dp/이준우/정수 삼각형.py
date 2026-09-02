def solution(triangle):
    n = len(triangle)
    dp = [[0] * n for _ in range(n)]
    dp[0][0] = triangle[0][0]

    for i in range(1, n):
        for j in range(i + 1):
            dp[i][j] = triangle[i][j] + max(
                # j=i인 경우, 가장 오른쪽 -> [i - 1][i]는 존재하지 않는 값 따라서 i - 1이 최대임을 보장하기 위한 min
                dp[i - 1][min(j, i - 1)],
                # j=0인 경우, 가장 왼쪽이기 때문에 -1해도 0임을 보장하기 위한 max
                dp[i - 1][max(0, j - 1)],
            )

    return max(dp[n - 1])


"""
triangle[i][0 ~ i]

[[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]]	
"""
