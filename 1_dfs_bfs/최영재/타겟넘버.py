def solution(numbers, target):
    return dfs(
        numbers=numbers,
        target=target,
        current_index=0,
        current_sum=0,
    )


def dfs(numbers, target, current_index, current_sum):
    is_decided = current_index == len(numbers)

    if is_decided:
        reached_target = current_sum == target
        if reached_target:
            return 1
        return 0

    current_number = numbers[current_index]

    ways_when_plus = dfs(
        numbers=numbers,
        target=target,
        current_index=current_index + 1,
        current_sum=current_sum + current_number,
    )

    ways_when_minus = dfs(
        numbers=numbers,
        target=target,
        current_index=current_index + 1,
        current_sum=current_sum - current_number,
    )

    return ways_when_plus + ways_when_minus


if __name__ == "__main__":
    print(solution([1, 1, 1, 1, 1], 3))  # 5
    print(solution([4, 1, 2, 1], 4))     # 2
