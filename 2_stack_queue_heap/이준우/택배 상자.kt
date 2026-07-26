class Solution {
    fun solution(order: IntArray): Int {
        var answer = 0
        var n = 1 // 주 컨테어너에서 나온 박스의 현재 순번
        val stack = ArrayDeque<Int>() // 보조 컨테이너

        // 기사님이 원하는 박스의 순번
        for (want in order) {
            // 보조 컨테이너에 기사님이 현재 원하는 순번의 상자까지 담는다.
            while (stack.isEmpty() || stack.last() != want) {
                if (n > order.size) break

                stack.add(n)
                n++
            }

            // 보조 컨테이너의 현재 순번이 기사님이 원하는 순번이면 꺼낸다.
            if (stack.isNotEmpty() && stack.last() == want) {
                stack.removeLast()
                answer += 1
            } else { // 그렇지 않으면 해당 시뮬레이션을 종료한다. 
                break
            }
        }

        return answer
    }
}