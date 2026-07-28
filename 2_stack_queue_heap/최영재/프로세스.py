from collections import deque


def solution(priorities, location):
    queue = deque()

    for i, priority in enumerate(priorities):
        queue.append((i, priority))
    print(queue)

    executed_count = 0
    while queue:
        cur = queue.popleft()

        isExecute = True
        for i in queue:
            if i[1]>cur[1]:
                isExecute= False
                break
        
        if not isExecute:
            queue.append(cur)
        else:
            executed_count +=1
            if cur[0] ==location:
                break


    return executed_count


if __name__ == "__main__":
    print(solution([2, 1, 3, 2], 2))           # 1
    print(solution([1, 1, 9, 1, 1, 1], 0))     # 5
    print(solution([1, 2, 2], 1))              # 1  (동점은 밀어내지 못함)
    print(solution([5, 5, 5, 5], 2))           # 3  (전부 동점이면 큐 순서 그대로)
    print(solution([1, 2, 3, 4], 0))           # 4  (오름차순 최악 케이스)
    print(solution([7], 0))                    # 1  (원소 1개)

