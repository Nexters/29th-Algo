func solution(_ m: Int, _ n: Int, _ puddles: [[Int]]) -> Int {
    var dp = [[Int]](repeating: [Int](repeating: 0, count: n + 1), count: m + 1)

    for puddle in puddles {
        dp[puddle[0]][puddle[1]] = -1
    }

    dp[1][1] = 1

    for i in 1...m {
        for j in 1...n {
            if i == 1 && j == 1 {
                continue
            }

            if dp[i][j] == -1 {
                continue
            }

            let up = dp[i - 1][j] == -1 ? 0 : dp[i - 1][j]
            let left = dp[i][j - 1] == -1 ? 0 : dp[i][j - 1]

            dp[i][j] = (up + left) % 1000000007
        }
    }

    return dp[m][n]
}
