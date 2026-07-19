class Solution {
    fun solution(numbers: IntArray, target: Int): Int {
        var answer = 0

        // 현재 인덱스, 연산자, 연산된 값
        // 연산자(op)가 0일 경우 +, 1일 경우 -
        fun dfs(idx: Int, op: Int, cur: Int) {
            // 연산자에 따라 현재 값을 계산
            val current = cur + if(op==1) numbers[idx] else - numbers[idx]

            // 마지막 인덱스에 도달한 경우
            if(idx == numbers.lastIndex) {
                // 현재 값이랑 target이랑 같은 경우 +1
                if(current == target) answer +=1

                return
            }

            // +, - 각각 탐색
            dfs(idx + 1, 0, current)
            dfs(idx + 1, 1, current)
        }
        
        dfs(0, 0, 0)
        dfs(0, 1, 0)

        return answer
    }
}


/*
브루트포스 알고리즘

현재 인덱스의 값으 + 인지, -인지 검사하면서 내려감
마지막 인덱스까지 탐색했을 때, target인 경우 answer += 1
 */