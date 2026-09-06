from collections import deque

MOD = 1_000_000_007

dx = [1, 0]
dy = [0, 1]

def solution(m, n, puddles):
    dp = [[0] * (m + 1) for _ in range(n + 1)]
    
    q = deque([(1, 1)])
    dp[1][1] = 1
    
    while q:
        x, y = q.popleft()
        
        for i in range(2):
            nx, ny = x + dx[i], y + dy[i]
            # 2중 배열은 [행 = y][열 = x]로 표현되기 때문에 puddles를 검사할 때는 뒤집어서 검사
            if [ny, nx] not in puddles and 0 < nx <= n and 0 < ny <= m:
                if dp[nx][ny] == 0:
                    q.append((nx, ny))
                dp[nx][ny] += dp[x][y]
    
    return dp[n][m] % MOD
