import heapq
from collections import defaultdict

def solution(tickets):
    from_to = defaultdict(list)
    for ticket in tickets:
        heapq.heappush(from_to[ticket[0]], ticket[1])

    answer = []
    def dfs(curr):
        while(from_to[curr]):
            dfs(heapq.heappop(from_to[curr]))
        answer.insert(0, curr)

    dfs("ICN")
        
    return answer