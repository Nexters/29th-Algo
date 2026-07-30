from collections import deque


def solution(n, wires):
    answer = int(1e9)

    graph = [[] for _ in range(n + 1)]
    for a, b in wires:
        graph[a].append(b)
        graph[b].append(a)

    def bfs(start, ban):
        visited = [False] * (n + 1)

        q = deque([start])
        visited[start] = True
        result = 1
        while q:
            node = q.popleft()

            for next in graph[node]:
                if next != ban and not visited[next]:
                    q.append(next)
                    visited[next] = True
                    result += 1

        return result

    for a, b in wires:
        answer = min(answer, abs(bfs(a, b) - bfs(b, a)))

    return answer
