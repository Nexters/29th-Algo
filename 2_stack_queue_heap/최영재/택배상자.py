def solution(order):
    sub_belt = []
    next_box = 1
    loaded_count = 0

    for target in order:
        while next_box <= len(order) and (not sub_belt or sub_belt[-1] != target):
            sub_belt.append(next_box)
            next_box += 1

        if sub_belt and sub_belt[-1] == target:
            sub_belt.pop()
            loaded_count += 1
        else:
            break

    return loaded_count


if __name__ == "__main__":
    print(solution([4, 3, 1, 2, 5]))    # 2  (문제 예시: 1번이 2번 아래 갇힘)
    print(solution([5, 4, 3, 2, 1]))    # 5  (전부 보조에 쌓고 역순으로 꺼냄)
    print(solution([1, 2, 3, 4, 5]))    # 5  (순서가 같으므로 전부 바로 트럭)
    print(solution([3, 1, 2]))          # 1  (3 싣고 나면 1이 2 아래 갇힘)
    print(solution([1]))                # 1  (상자 1개)
