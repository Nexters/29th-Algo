def solution(distance, rocks, n):
    answer = 0

    rocks.append(distance)
    rocks.sort()
    left = 0
    right = distance

    while left <= right:
        min_gap = (left + right) // 2

        current = removed = 0
        for rock in rocks:
            if rock - current < min_gap:
                removed += 1
            else:
                current = rock

        if removed > n:
            right = min_gap - 1
        else:
            left = min_gap + 1
            answer = min_gap

    return answer
