INF = int(1e9)


def solution(n, s, a, b, fares):
    graph = [[INF] * (n + 1) for _ in range(n + 1)]
    for i in range(1, n + 1):
        graph[i][i] = 0

    for x, y, c in fares:
        graph[x][y] = c
        graph[y][x] = c

    for k in range(1, n + 1):
        for x in range(1, n + 1):
            for y in range(1, n + 1):
                graph[x][y] = min(graph[x][y], graph[x][k] + graph[k][y])

    answer = graph[s][a] + graph[s][b]
    for i in range(1, n + 1):
        if graph[s][i] >= INF:
            continue

        answer = min(answer, graph[s][i] + graph[i][a] + graph[i][b])

    return answer
