def solution(n, times):
    answer = min(times) * n
    
    start = 1
    end = answer
    while start <= end:
        mid = (start + end) // 2
        cnt = sum(mid // time for time in times)
        
        if cnt < n:
            start = mid + 1
        else:
            answer = min(answer, mid)
            end = mid - 1

    return answer
