from itertools import permutations


def is_prime(n):
    if n < 2:
        return False

    divisor = 2
    while divisor * divisor <= n:
        if n % divisor == 0:
            return False
        divisor += 1

    return True


def solution(numbers):
    candidates = set()

    for length in range(1, len(numbers) + 1):
        for picked in permutations(numbers, length):
            candidates.add(int("".join(picked)))
    print(candidates)
    return sum(1 for candidate in candidates if is_prime(candidate))


if __name__ == "__main__":
    print(solution("17"))       # 3  (7, 17, 71)
    print(solution("011"))      # 2  (11, 101)
    # print(solution("0"))        # 0  (만들 수 있는 수가 0 뿐)
    # print(solution("11"))       # 1  (1, 11 중 11만 소수)
    # print(solution("7"))        # 1  (7)
    # print(solution("1234567"))  # 1336  (최대 크기 입력)
