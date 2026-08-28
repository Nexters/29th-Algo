import heapq

INF = int(1e9)

def solution(N, road, K):
    graph = [[] for _ in range(N + 1)] 
    
    for a, b, c in road:
        graph[a].append((b, c))
        graph[b].append((a, c))
        
    distance = [INF] * (N + 1)
    pq = [(0, 1)]
    distance[1] = 0
    
    while pq:
        dist, now = heapq.heappop(pq)
        if distance[now] < dist: 
            continue
        
        for next, c in graph[now]:
            cost = distance[now] + c
            if distance[next] > cost:
                distance[next] = cost
                heapq.heappush(pq, (cost, next))


    return sum(1 for x in distance if x <= K)