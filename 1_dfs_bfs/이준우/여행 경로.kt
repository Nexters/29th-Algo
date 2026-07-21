class Solution {
    fun solution(tickets: Array<Array<String>>): Array<String> {
        // from, to 
        // to의 경우 이름 순 저장을 위해 우선순위 큐를 사용
        val fromToMap = mutableMapOf<String, PriorityQueue<String>>()
        for (ticket in tickets) {
            fromToMap.getOrPut(ticket[0]) { PriorityQueue<String>() }.add(ticket[1])
        }

        val answer =  mutableListOf<String>()
        fun dfs(curr: String,) {
            while(fromToMap[curr] != null && fromToMap[curr]!!.isNotEmpty()) {
                val next = fromToMap[curr]!!.poll()
                dfs(next)
            }
            
            // 현재 경로를 맨 앞에 추가
            // 탐색이 끝난 순으로 노드를 추가할 경우, 이는 경로의 반대 방향
            answer.add(0, curr)
        }

        dfs("ICN")

        return answer.toTypedArray()
    }
}

/*
진입 시점에 기록하면, 중간 경로가 누락될 수 있다.

현재 방식대로 기록하면, 한 노드에서 갈라지는 여러 갈래길을 전부 탐색할 수 있다.

"모든 도시를 방문할 수 없는 경우는 주어지지 않습니다."
-> [[ICN, AFO], [ICN, JFK]] 와 같이 되돌아올 수 없는 경로는 주어지지 않음
 */