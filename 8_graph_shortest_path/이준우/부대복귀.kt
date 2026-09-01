import java.util.*

val INF = 1_000_000_000

class Solution {
    /*
     * n : 총 지역 수
     * roads : 두 지역을 왕복할 수 있는 길 정보를 담은 배열
     * sources : 각 부대원이 위치한 서로 다른 지역들을 나타내는 정수 배열
     * destination : 강철부대의 지역
     * 
     * sources의 원소 순서대로 강철 부대로 복귀할 수 있는 최단시간을 담은 배열을 리턴
     * 복귀가 불가능한 경우 -1
     */
    fun solution(n: Int, roads: Array<IntArray>, sources: IntArray, destination: Int): IntArray {
        val graph = Array(n + 1) { mutableListOf<Pair<Int, Int>>() }
        // 양방향
        for ((a, b) in roads) {
            graph[a].add(b to 1)
            graph[b].add(a to 1)
        }

        val pq = PriorityQueue<Pair<Int, Int>>(
            compareBy { it.first }
        )
        // destination을 출발지로 하여서 역산 -> 한 번의 탐색으로 정답을 도출
        val distance = IntArray(n + 1) { INF }
        distance[destination] = 0
        pq.add(0 to destination)

        while (pq.isNotEmpty()) {
            val (dist, now) = pq.poll()
            if (distance[now] < dist) continue

            for ((next, c) in graph[now]) {
                val cost = distance[now] + c
                if (cost < distance[next]) {
                    distance[next] = cost
                    pq.add(cost to next)
                }
            }
        }

        val answer = IntArray(sources.size)
        for (i in sources.indices) {
            answer[i] = if (distance[sources[i]] >= INF) -1 else distance[sources[i]]
        }

        return answer
    }
}

/**
 * 두 지역 간의 길을 통과하는데 걸린 시간은 모두 1
 * 
 * distance를 출발지로 하고 탐색
 */
