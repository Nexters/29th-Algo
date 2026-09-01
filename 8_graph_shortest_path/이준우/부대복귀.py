import heapq

INF = int(1e9)


def solution(n, roads, sources, destination):
    graph = [[] for _ in range(n + 1)]
    for a, b in roads:
        graph[a].append((b, 1))
        graph[b].append((a, 1))

    pq = [(0, destination)]
    distance = [INF] * (n + 1)
    distance[destination] = 0
    while pq:
        dist, now = heapq.heappop(pq)
        if distance[now] < dist:
            continue
        
        for next, c in graph[now]:
            cost = c + distance[now]
            if distance[next] > cost:
                distance[next] = cost
                heapq.heappush(pq, (cost, next))
                
    answer = []
    for s in sources:
        answer.append(
            -1 if distance[s] >= INF else distance[s]
        )

    return answer
