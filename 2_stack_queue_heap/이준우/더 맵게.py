import heapq

def solution(scoville, K):
    answer = 0
    
    # 힙 자료구조를 사용하기 전, 힙 구조를 만족시켜야 함 
    # heapq.heapify()나 sort() 필수
    heapq.heapify(scoville)
    while scoville:
        min = heapq.heappop(scoville)
        
        # 가장 낮은 음식이 K 이상인 경우 통과
        if min >= K:
            return answer
        
        # 더 이상 새로운 음식을 못만드는 경우 -1
        if not scoville:
            return -1
        
        min2 = heapq.heappop(scoville)
        new = min + (min2 * 2)
        heapq.heappush(scoville, new)
        
        answer += 1
    
    return answer if min(scoville) >= K else -1
