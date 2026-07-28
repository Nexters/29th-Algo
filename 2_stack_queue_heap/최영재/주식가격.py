def solution(prices):
    stack = []
    answer = [0] * len(prices)

    for i, price in enumerate(prices):
        print(i, price)
        for j in range(i+1, len(prices)):
            answer[i]+=1
            if prices[j] <price:
                break
    return answer


if __name__ == "__main__":
    print(solution([1, 2, 3, 2, 3]))      # [4, 3, 1, 1, 0]  (문제 예시)
    print(solution([1, 2, 3, 4, 5]))      # [4, 3, 2, 1, 0]  (계속 오름 -> 아무도 안 떨어짐)
    print(solution([5, 4, 3, 2, 1]))      # [1, 1, 1, 1, 0]  (계속 내림 -> 다음 초에 바로 떨어짐)
    print(solution([3, 3, 3]))            # [2, 1, 0]        (동일 가격은 "떨어진" 게 아님)
    print(solution([2, 1]))               # [1, 0]           (최소 길이)
