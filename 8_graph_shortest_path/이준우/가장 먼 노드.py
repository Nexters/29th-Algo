import heapq

INF = int(1e9)


def solution(n, edge):
    graph = [[] for _ in range(n + 1)]
    distance = [INF] * (n + 1)

    for a, b in edge:
        graph[a].append(b)
        graph[b].append(a)

    pq = []
    heapq.heappush(pq, (0, 1))
    distance[1] = 0

    while pq:
        dist, now = heapq.heappop(pq)
        if distance[now] < dist:
            print(dist, distance[now])
            continue

        for next in graph[now]:
            cost = distance[now] + 1
            if distance[next] > cost:
                distance[next] = cost
                heapq.heappush(pq, (cost, next))

    max_value = max(list(filter(lambda x: x != INF, distance)))
    return distance.count(max_value)
