class Solution {
    val size = 'Z' - 'A' + 1

    fun solution(name: String): Int {
        // 위, 아래 중 최소값의 합 + 최소 경로
        return name.sumOf { minOf((it - 'A'), size - (it - 'A')) } + move(name)
    }

    fun move(name: String): Int {
        val n = name.length
        var best = n - 1  //초깃값 : 오른쪽으로만 쭉 갔을 경우 -> A를 모두 밞았을 경우

        for (i in name.indices) {
            var next = i + 1
            while (next < n && name[next] == 'A') next++

            // i와 next 사이를 회피하는 방법
            // 0 -> i, i -> 0, 0 -> next or 0 -> next, next -> 0, 0 -> i
            best = minOf(best, i * 2 + (n - next), (n - next) * 2 + i)
        }
        return best
    }
}

/**
 * 컨셉 : 가장 가까운 인덱스(왼 or 오)로, 가장 가까운 단어로(위 or 아래))
 * 경로와 단어 변환 비용은 각각 독립적
 *  - 가장 큰 'A' 덩어리를 최대한 피하는 식의 경로 문제
 *  - 0~i 까지 순차 탐색 후 next까지 'A' 덩어리를 탐색
 *  - 0~i 까지 'A'가 아닌 값이 있는지 탐색 후 'A' 덩어리를 탐색
 *  - 위 둘 다 시간 복잡도 O(n^2)
 *
 * 수학적으로 접근하자
 */