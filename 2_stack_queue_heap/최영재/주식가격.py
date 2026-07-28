def solution(prices):
    # 아직 "떨어지는 순간" 을 못 만난 시점들의 인덱스를 쌓아두는 스택
    stack = []
    answer = [0] * len(prices)

    for i, price in enumerate(prices):
        print(i, price)
        for j in range(i+1, len(prices)):
            answer[i]+=1
            if prices[j] <price:
                break

        # TODO: 스택 맨 위 시점의 가격보다 현재 가격이 낮으면(= 떨어졌으면) 꺼낸다
        # TODO: 꺼낸 시점 j 의 버틴 기간은 i - j -> answer[j] 에 기록
        # TODO: 더 이상 떨어뜨릴 게 없으면 현재 인덱스 i 를 스택에 쌓는다
        pass

    # TODO: 끝까지 스택에 남은 시점들은 마지막까지 안 떨어진 것
    #       각각의 기간은 (마지막 인덱스 - 자기 인덱스)

    return answer


if __name__ == "__main__":
    print(solution([1, 2, 3, 2, 3]))      # [4, 3, 1, 1, 0]  (문제 예시)
    # print(solution([1, 2, 3, 4, 5]))      # [4, 3, 2, 1, 0]  (계속 오름 -> 아무도 안 떨어짐)
    # print(solution([5, 4, 3, 2, 1]))      # [1, 1, 1, 1, 0]  (계속 내림 -> 다음 초에 바로 떨어짐)
    # print(solution([3, 3, 3]))            # [2, 1, 0]        (동일 가격은 "떨어진" 게 아님)
    # print(solution([2, 1]))               # [1, 0]           (최소 길이)
