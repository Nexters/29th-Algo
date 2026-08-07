class Solution {
    fun solution(number: String, k: Int): String {
        var cnt = k // 뺄 수 있는 값
        val nums = number.map { it.digitToInt() }

        val stack = mutableListOf<Int>()
        for (num in nums) {
            // stack.peek() 보다 현재 값이 더 크면 stack을 비움 
            while (
                stack.isNotEmpty() &&
                stack.last() < num &&
                cnt > 0
            ) {
                stack.removeLast()
                cnt--
            }
            stack.add(num)
        }

        // k를 다 소진하지 못한 경우
        // or return stack.subList(0, stack.size - cnt).joinToString("")
        while (cnt > 0) {
            stack.removeLast()
            cnt--
        }

        return stack.joinToString("")
    }
}

/**
 * 컨셉 : stack을 활용한 그리디 문제
 *  앞자리에 큰 수가 올 수 있도록 함
 *  제일 큰 수 -> 앞 자리가 크면 장땡
 *  순차적으로 탐색하던 중, stack.peek()보다 큰 경우 stack을 pop() 함
 *  stack에 저장된 수가 정답이 됨 
 */