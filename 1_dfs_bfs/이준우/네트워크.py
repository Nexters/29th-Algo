from collections import deque

def solution(n, computers):
    answer = 0
    visited = [False] * n
    
    def bfs(start):
        q = deque([start])
        visited[start] = True
        
        while q:
            cur = q.popleft()
            
            for i in range(n):
                if computers[cur][i] == 1 and not visited[i]:
                    q.append(i)
                    visited[i] = True
    
    
    for i in range(n):
        if not visited[i]:
            answer += 1
            bfs(i)
    
    return answer