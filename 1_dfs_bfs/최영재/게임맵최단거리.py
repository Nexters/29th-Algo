from collections import deque

def solution(maps):
    n = len(maps)
    m = len(maps[0])

    dx = [-1, 1, 0, 0]
    dy = [0, 0, -1, 1]

    queue = deque()
    # 시작 칸 (x, y, 거리)
    queue.append((0, 0, 1))

    visited = [[False] * m for _ in range(n)]
    while queue:
        # 큐에서 칸 하나 꺼내기
        x, y, dist = queue.popleft()

        # 도착 칸이면 거리 반환
        if x == n - 1 and y == m - 1:
            return dist

        for i in range(4):
            nx = x + dx[i] 
            ny = y + dy[i]

            # 맵 밖이면 스낍
            if nx< 0 or nx>=n or ny <0 or ny >=m:
                continue

            # 벽이면 스낍
            if maps[nx][ny] == 0:
                continue

            # 이미 방문한 칸이면 스낍
            if visited[nx][ny]:
                continue

            visited[nx][ny] = True
            queue.append((nx, ny, dist + 1))

    return -1


if __name__ == "__main__":
    maps1 = [
        [1, 0, 1, 1, 1],
        [1, 0, 1, 0, 1],
        [1, 0, 1, 1, 1],
        [1, 1, 1, 0, 1],
        [0, 0, 0, 0, 1],
    ]
    maps2 = [
        [1, 0, 1, 1, 1],
        [1, 0, 1, 0, 1],
        [1, 0, 1, 1, 1],
        [1, 1, 1, 0, 0],
        [0, 0, 0, 0, 1],
    ]
    print(solution(maps1))  # 11
    print(solution(maps2))  # -1
