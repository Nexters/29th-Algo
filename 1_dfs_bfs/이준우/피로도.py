def solution(k, dungeons):
    if k <= 0 or not dungeons:
        return 0

    answer = 0
    for i, d in enumerate(dungeons):
        least, consume = d
        if k >= least:
            answer = max(
                answer, 1 + solution(k - consume, dungeons[:i] + dungeons[i + 1 :])
            )

    return answer
