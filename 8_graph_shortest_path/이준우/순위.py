def solution(n, results):
    graph = [[False] * (n + 1) for _ in range(n + 1)]
    for i in range(1, n + 1):
        graph[i][i] = True

    for a, b in results:
        graph[a][b] = True

    for k in range(1, n + 1):
        for a in range(1, n + 1):
            for b in range(1, n + 1):
                if graph[a][k] and graph[k][b]:
                    graph[a][b] = True

    answer = 0
    for i in range(1, n + 1):
        tmp = 0
        for j in range(1, n + 1):
            tmp += 1 if graph[i][j] or graph[j][i] else 0
        answer += 1 if tmp == n else 0

    return answer
