from collections import deque

dx = [-1, 0, 1, 0]
dy = [0, -1, 0, 1]


def solution(maps):
    # 그래프의 크기
    n = len(maps)
    m = len(maps[0])

    # 방문하지 않은 경우 -1, 그 외에는 방문한 경우
    visited = [[-1] * m for _ in range(n)]
    
    # 출발지
    q = deque([(0, 0)])
    visited[0][0] = 1
    while q: 
        x, y = q.popleft()
        
        for i in range(4):
            nx, ny = x + dx[i], y + dy[i]
            if 0 <= nx < n and 0 <= ny < m and visited[nx][ny] == -1 and maps[nx][ny] == 1:
                if nx == (n - 1) and ny == (m - 1):
                    return visited[x][y] + 1

                q.append((nx, ny))
                visited[nx][ny] = visited[x][y] + 1
                
    
    return -1
