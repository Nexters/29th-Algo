def solution(name):
    size = ord("Z") - ord("A") + 1

    return sum(
        [min(ord(c) - ord("A"), size - (ord(c) - ord("A"))) for c in name]
    ) + move(name)


def move(name):
    n = len(name)
    best = n - 1

    for i in range(n):
        next = i + 1
        while next < n and name[next] == "A":
            next += 1

        best = min(best, i * 2 + (n - next), (n - next) * 2 + i)

    return best
