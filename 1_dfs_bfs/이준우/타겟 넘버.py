def solution(numbers, target):
    def dfs(idx, cur):
        if idx == len(numbers):
            return 1 if cur == target else 0

        return dfs(idx + 1, cur + numbers[idx]) + dfs(idx + 1, cur - numbers[idx])

    return dfs(0, 0)
