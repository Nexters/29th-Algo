def find(parent, a):
    if parent[a] != a:
        parent[a] = find(parent, parent[a])
    return parent[a]


def union(parent, a, b):
    a = find(parent, a)
    b = find(parent, b)

    if a < b:
        parent[b] = a
    else:
        parent[a] = b


def solution(n, costs):
    answer = 0

    costs.sort(key=lambda x: x[2])
    parent = list(range(n + 1))

    for a, b, c in costs:
        if find(parent, a) != find(parent, b):
            union(parent, a, b)
            answer += c

    return answer
