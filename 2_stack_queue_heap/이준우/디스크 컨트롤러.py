import heapq


# [(작업 요청 시간, 실행 시간)]
def solution(jobs):
    # (실행 시간, 요청 시간)
    pq = []

    jobs.sort()  # 요청 시간에 맞춰서 정렬

    answer = 0
    idx = 0
    current = 0

    while pq or idx < len(jobs):
        while idx < len(jobs) and jobs[idx][0] <= current:
            heapq.heappush(pq, (jobs[idx][1], jobs[idx][0]))
            idx += 1

        if not pq:
            current = jobs[idx][0]
        else:
            run_time, req_at = heapq.heappop(pq)
            current = current + run_time
            answer += current - req_at

    return answer // len(jobs)
