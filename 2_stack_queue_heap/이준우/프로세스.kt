import java.util.*

class Solution {
    // 우선순위, 몇 번째로 나오는지 궁금한 값
    fun solution(priorities: IntArray, location: Int): Int {
        var answer = 0

        // (우선순위, 인덱스)의 queue
        val q = ArrayDeque<Pair<Int, Int>>()
        for(i in 0..priorities.lastIndex) {
            q.add(priorities[i] to i)
        }

        // sp의 우선순위를 기준으로 p의 값을 추출
        // [1, 1, 9, 1, 1, 1] -> [9, 1, 1, 1, 1, 1]
        val sp = PriorityQueue<Int>(reversedOrder()).apply {addAll(priorities.toList())}
        while(q.isNotEmpty()) {
            // 우선순위 검사
            // sp는 현재 q의 우선순위랑 동기화되어 있음
            if(sp.first() == q.first().first) {
                answer += 1
                val (_, idx) = q.removeFirst()
                // location이랑 동일할 시 answer를 반환 
                if(idx == location) return answer
                s.removeFist()
            }
            else {
                q.add(q.removeFirst())
            }
        }

        return answer
    }
}

/*
queue로 원하는 값을 꺼내기 위해서는 queue는 꺼내야할지 다시 주입해야할지 알고 있어야 함
즉, queue는 전체 우서순위를 이해하고 있어야 함.

값을 꺼낼 때 검사
    현재 가장 높은 우선순위다 싶으면 제거
    아니면 다시 주입 

priorities 를 역순으로 정렬한 값 -> 뽑을지 말지 결정하는 기준이 됨.
 */
