import java.util.*

val INF = 1_000_000_000

class Solution {
    // 지점의 개수, 출발지점 s, 도착지 a, b, 가중치 b
    fun solution(n: Int, s: Int, a: Int, b: Int, fares: Array<IntArray>): Int {
        val graph = Array(n + 1) { IntArray(n + 1) { INF } }
        for (i in 1..n) graph[i][i] = 0
        for ((a, b, c) in fares) {
            graph[a][b] = c
            graph[b][a] = c
        }

        for (k in 1..n) {
            for (a in 1..n) {
                for (b in 1..n) {
                    graph[a][b] = minOf(graph[a][b], graph[a][k] + graph[k][b])
                }
            }
        }

        var answer = graph[s][a] + graph[s][b]
        for (i in 1..n) {
            if (graph[s][i] >= INF) continue

            // s -> i까지만 합승 후 갈라지는 경우 
            answer = minOf(answer, graph[s][i] + graph[i][a] + graph[i][b])
        }

        return answer
    }
}

/**
 * 플로이드 워셜을 통해서 전체 최단거리를 구한 후
 * s에서 도달할 수 있는 합승거리 i까지 + i에서 a까지 + i에서 b까지 중 최저값
 */