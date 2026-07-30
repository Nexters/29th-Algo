import kotlin.math.*

class Solution {
    fun solution(
        n: Int,
        wires: Array<IntArray>,
    ): Int {
        var answer: Int = Int.MAX_VALUE

        val graph = Array<MutableList<Int>>(n + 1) { mutableListOf() }
        // 무방향 그래프이므로 양쪽을 추가
        for ((a, b) in wires) {
            graph[a].add(b)
            graph[b].add(a)
        }

        fun bfs(
            start: Int,
            ban: Int,
        ): Int {
            val visited = BooleanArray(n + 1)

            val q = ArrayDeque<Int>()
            q.add(start)
            visited[start] = true

            var result = 1
            while (q.isNotEmpty()) {
                val node = q.removeLast()

                for (next in graph[node]) {
                    // 다음 노드가 끊긴 곳이 아니거나 방문한 적이 없는 경우
                    if (next != ban && !visited[next]) {
                        result++
                        q.add(next)
                        visited[next] = true
                    }
                }
            }
            return result
        }

        for ((a, b) in wires) {
            val first = bfs(a, b)
            val second = bfs(b, a)

            answer = min(answer, abs(first - second))
        }

        return answer
    }
}

/**
 * 컨셉 : 엣지를 하나씩 자른 후, 나뉜 양쪽 그룹의 개수의 차를 비교해가며 풀이
 */
