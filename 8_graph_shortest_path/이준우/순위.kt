val INF = 1_000_000_000

class Solution {
    fun solution(n: Int, results: Array<IntArray>): Int {
        val graph = Array(n + 1) { BooleanArray(n + 1) }
        for ((a, b) in results) graph[a][b] = true

        // 플로이드 워셜
        for (k in 1..n) {
            for (a in 1..n) {
                for (b in 1..n) {
                    if(graph[a][k] && graph[k][b]) graph[a][b] = true
                }
            }
        }

        var answer = 0
        for(i in 1..n) {
            var tmp = 0
            for (j in 1..n) {
                // 팬인, 팬아웃 모두 검사
                tmp += if(graph[i][j] || graph[j][i]) 1 else 0
            }
            answer += if((n - 1) == tmp) 1 else 0
        }

        return answer
    }
}

/**
 * 1~n명의 격투 선수
 * 
 * A선수가 B선수보다 실력이 좋다면 A선수는 B선수를 항상 이긴다.
 * 정확하게 순위를 매길 수 있는 선수의 수
 * 
 * 팬인과 팬아웃을 전부 검사, 둘 중 하나라도 만족한다면 count
 */