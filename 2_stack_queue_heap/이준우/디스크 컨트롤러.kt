import java.util.PriorityQueue

class Solution {
    // 요청 시간, 실행 시간
    fun solution(jobs: Array<IntArray>): Int {
        jobs.sortBy { it[0] }
        val answer = mutableListOf<Int>()

        val pq =
            PriorityQueue<IntArray>(
                compareBy<IntArray> { it[1] },
            )

        var total = 0
        var currentTime = 0
        var idx = 0

        while (idx < jobs.size || pq.isNotEmpty()) {
            // 중간에 들어온 작업들을 큐에 삽입
            while (idx < jobs.size && jobs[idx][0] <= currentTime) {
                pq.add(jobs[idx])
                idx++
            }

            // 우선순위 큐가 비어 있는 경우, 가장 가까운 작업의 시간대로 이동
            if (pq.isEmpty()) {
                currentTime = jobs[idx][0]
            }
            // 우선순위가 높은 작업을 처리
            else {
                val (requestAt, runTime) = pq.poll()
                currentTime += runTime
                total += currentTime - requestAt
            }
        }

        return total / jobs.size
    }
}

/*
 * id는 계산에 필요 없는 값
 * - 요청 시각이랑 실행 시간이 같은 경우, 어차피 하나는 뒤로 밀리게 되는데 누가 밀리든 계산에는 영향을 안줌
 */