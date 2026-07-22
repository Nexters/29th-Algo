from collections import deque

def solution(priorities, location):
    answer = 0
    q = deque()
    pq = deque(sorted(priorities, reverse = True))
    
    for i in range(0, len(priorities)):
        # 현재 인덱스, 우선순위
        q.append((i, priorities[i]))
        
    while(q):
        if pq[0] == q[0][1]:
            answer += 1
            if q[0][0] == location:
                return answer
            q.popleft()
            pq.popleft()
        else:
            q.append(q.popleft())
    
    return answer