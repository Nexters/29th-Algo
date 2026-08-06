def solution(routes):
    routes.sort()

    # 초깃값 routes[1]을 선택
    left, right = routes[0]
    answer = 1
    for l, r in routes:
        # 핵심 풀이 : 하나의 단속 카메라가 많은 차량을 커버하기 위한 범위를 좁혀나감
        # l만 체크하는 이유 : l순으로 정렬되어 있기 때문에 모든 수 커버 가능
        if left <= l <= right:
            left = max(left, l)
            right = min(right, r)
        else:
            answer += 1
            left = l
            right = r

    return answer


"""
컨셉 : 하나의 단속 카메라가 많은 차량을 커버하기 위해 범위를 좁혀나감
"""
