from collections import defaultdict


def solution(genres, plays):
    answer = []
    map = defaultdict(list)

    for i in range(len(genres)):
        map[genres[i]].append(i)

    sorted_map = dict(
        sorted(map.items(), key=lambda x: sum(plays[i] for i in x[1]), reverse=True)
    )

    for v in sorted_map.values():
        answer += sorted(v, key=lambda x: (-plays[x], x))[:2]

    return answer
