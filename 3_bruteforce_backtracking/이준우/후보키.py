from itertools import combinations


def solution(relation):
    row_cnt = len(relation)
    col_cnt = len(relation[0])
    answer = []  # 후보키 list

    # 유니크한 값인지
    def isUnique(columns):
        tmp = set()
        for row in relation:
            tmp.add(",".join([row[col] for col in columns]))

        return len(tmp) == row_cnt

    # 최소성을 만족하는지
    # answer의 요소 중에 candidate의 부분집합이 존재하지 않아야 함
    def isMinimality(candidate):
        candidate_set = set(candidate)
        for a in answer:
            if candidate_set.issuperset(a):
                return False
        return True

    for size in range(1, col_cnt + 1):
        for candidate in combinations(list(range(col_cnt)), size):
            if isMinimality(candidate) and isUnique(candidate):
                answer.append(candidate)

    return len(answer)
