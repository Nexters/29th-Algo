import java.util.*

val INF = 1_000_000_000

class Solution {
    fun solution(N: Int, road: Array<IntArray>, k: Int): Int {
        // a -> (b, 가중치)
        val graph = Array(N + 1) { mutableListOf<Pair<Int, Int>>() }
        for ((a, b, c) in road) {
            graph[a].add(b to c)
            graph[b].add(a to c)
        }
        
        val distance = IntArray(N + 1) { INF }
        // dist, now 
        val pq = PriorityQueue<Pair<Int, Int>>(
            compareBy{ it.first }
        )        
        distance[1] = 0
        pq.add(0 to 1)
        
        // 다익스트라 알고리즘
        while(pq.isNotEmpty()) {
            val (dist, now) = pq.poll()
            if(distance[now] < dist) continue
            
            for ((next, c) in graph[now]) {
                val cost = c + distance[now]
                if(cost < distance[next]) {
                    distance[next] = cost
                    pq.add(cost to next)
                }
            }
        }
        
        return distance.count { it <= k }
    }
}

/**
 * 1부터 n까지 번호가 부여
 * 양방향
 * 가중치
 * N개의 마을 중 K시간 이하로 배달이 가능한 마을에서만 주문
 * 
 * 자기 자신 마을에 배달 가능하므로 1번 마을 포함 
 */