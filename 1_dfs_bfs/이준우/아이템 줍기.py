from collections import deque

dx = [-1, 0, 1, 0]
dy = [0, -1, 0, 1]

def solution(rectangle, characterX, characterY, itemX, itemY):
    graph = [[-1] * 101 for _ in range(101)]
    distance = [[-1] * 101 for _ in range(101)]

    # graph의 둘레는 1, 둘레 내부는 0으로 채움
    # 1 -> 0은 가능하지만, 0 -> 1은 불가능하게 설계함으로써 둘레 내부의 1이 없게 함
    for r in rectangle:
        # 좌표를 2배 처리함으로써 (0, 0, 1, 1) 같은 케이스도 포함
        lx, ly, rx, ry = map(lambda x: x * 2, r)
        for x in range(lx, rx + 1):
            for y in range(ly, ry + 1):
                if lx < x < rx and ly < y < ry:
                    graph[x][y] = 0
                elif graph[x][y] != 0: # 내부가 아니고 0이 아닌 경우,
                    graph[x][y] = 1
                    
    # 좌표를 2배처리 한 것에 맞춰 계산
    startX, startY, desX, desY = characterX * 2, characterY * 2, itemX * 2, itemY * 2
    
    # BFS
    q = deque([(startX, startY)])
    distance[startX][startY] = 0
    while q: 
        x, y = q.popleft()
        for i in range(4):
            nx, ny = x + dx[i], y + dy[i]
            # graph 범위를 벗어나지 않고 둘레이고 아직 방문한적이 없는 경우 
            if 0 <= nx <= 100 and 0 <= ny <= 100 and graph[nx][ny] == 1 and distance[nx][ny] == -1:
                # 도달한 경우 조기 종료
                if nx == desX and ny == desY:
                    return (distance[x][y] + 1) // 2 # 2배로 늘렸었기에 나눔
                q.append((nx, ny))
                distance[nx][ny] = distance[x][y] + 1
        
        
    return (distance[x][y] + 1) // 2
