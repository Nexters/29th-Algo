class Solution {
    fun solution(n: Int, edge: Array<IntArray>): Int {
        val distance = IntArray(n + 1) { -1 }
        val graph = Array(n + 1) { mutableListOf<Int>() }
        for (e in edge) {
            val (a, b) = e
            graph[a].add(b)
            graph[b].add(a)
        }

        val q = ArrayDeque<Int>()
        q.add(1)
        distance[1] = 0

        while (q.isNotEmpty()) {
            val cur = q.removeFirst()

            for (next in graph[cur]) {
                if (distance[next] == -1) {
                    distance[next] = distance[cur] + 1
                    q.add(next)
                }
            }
        }

        val maxValue = distance.maxOrNull() ?: 0
        return distance.count { it == maxValue }
    }
}

/**
 * 가장 멀리 떨어진 노드의 갯수
 * 
 * BFS -> 처음에 목적지에 도달할 경우 그것이 최단거리
 */