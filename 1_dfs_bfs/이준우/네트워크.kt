class Solution {
    fun solution(n: Int, computers: Array<IntArray>): Int {
        val visited = BooleanArray(n)
        var answer = 0

        fun bfs(start: Int) {
            val q = ArrayDeque<Int>()
            q.add(start)
            visited[start] = true

            while(q.isNotEmpty()) {
                val cur = q.removeLast()

                for(i in 0..<n) {
                    // 연결이 있으면서, 방문하지 않은 경우
                    if(computers[cur][i] == 1 && !visited[i]) {
                        visited[i] = true
                        q.add(i)
                    }
                }
            }
        }

        repeat(n) {
            if(!visited[it]) {
                answer += 1
                bfs(it)
            }
        }

        return answer
    }
}
