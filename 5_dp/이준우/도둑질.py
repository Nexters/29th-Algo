def solution(money):
    n = len(money)

    if n == 3:
        return max(money)

    answer = 0
    # 첫 번째 집을 선택한 경우, 마지막 집은 선택 못함
    dp = [0] * (n - 1)
    dp[0] = money[0]
    dp[1] = max(money[0], money[1])
    for i in range(2, n - 1):
        dp[i] = max(dp[i - 1], dp[i - 2] + money[i])

    answer = dp[-1]

    # 첫 번재 집을 선택하지 않은 경우
    dp = [0] * n
    dp[1] = money[1]
    for i in range(2, n):
        dp[i] = max(dp[i - 1], dp[i - 2] + money[i])

    return max(answer, dp[-1])


"""
원형
인접한 집을 터는 경우 경보가 울림
    - OXXO와 같이 터는 것이 가능
"""
