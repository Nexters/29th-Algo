from collections import defaultdict

INF = int(1e9)


def solution(strs, t):
    words = defaultdict(list)

    for str in strs:
        words[str[0]].append(str)

    n = len(t)
    dp = [INF] * (n + 1)
    dp[0] = 0

    for idx in range(n):
        # 이미 방문했었던 경우
        if dp[idx] == INF:
            continue
        for word in words[t[idx]]:
            if t[idx: idx + len(word)] == word:
                nxt = idx + len(word)
                dp[nxt] = min(dp[nxt], dp[idx] + 1)

    return dp[n] if dp[n] != INF else -1


"""
그냥 쌩으로 전체 탐색하면 타임아웃이 발생할 것 같음

str을 조립해서 t에 맞춰가는 법?
    str을 words['첫 단어'] = list('단어').sort(len('단어'))

t를 토큰화해서 str과 대조하는 법?

dp 문제 특히 다익스트라 문제와 동일
    해당 노드(문자)에 도달 할 수 있으면, 기존의 값이 작은지 현재 루트가 작은지 비교
"""
