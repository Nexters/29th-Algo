# 그냥 O(N^2)의 풀이 <- 효율성에서 떨이질 줄 앎...
def solution(prices):
    answer = [0] * len(prices)

    for i in range(len(prices) - 1):
        cur = 0
        for j in range(i + 1, len(prices)):
            # j는 해당 for 문을 벗어나면 할당이 해제되기 때문에 cur에 할당
            cur = j
            if prices[i] > prices[j]:
                break
        # 현재 위치(j) - 고정 위치(i)를 통해 흐른 시간을 파악
        # 3 -> 2의 경우 1초 흐른 것으로 간주 -> 통과
        answer[i] = cur - i

    return answer


# Stack으로 풀이 O(N)
def solution(prices):
    n = len(prices)
    answer = [0] * len(prices)
    # 인덱스를 저장
    # prices의 값이 아닌 인덱스를 저장하여 풀이 <- 생각 못했음 dict으로 풀려했음
    stack = []

    for i in range(n):
        # stack이 비어있지 않으면서 가격이 떨어진 경우
        # peek를 기준으로 값이 가격이 떨어진 경우, peek에 값을 주입
        while stack and prices[stack[-1]] > prices[i]:
            j = stack.pop()
            answer[j] = i - j
        stack.append(i)

    # 모두 순회했음에도 stack에 값이 남아있는 경우
    # 즉 가격이 한 번도 떨어진 적이 없는 인덱스들 처리
    for j in stack:
        answer[j] = n - 1 - j

    return answer


"""
현재 인덱스의 값보다 떨어진 적이 있는가?

2중 for문 즉, O(N^2)는 너무 비싸지 않은가? 안비싼가보다.
"""
