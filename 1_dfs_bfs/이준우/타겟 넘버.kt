class Solution {
    fun solution(numbers: IntArray, target: Int): Int {

        // 현재 인덱스, 현재 값
        fun dfs(idx: Int, cur: Int): Int {
            // 마지막 노드까지 탐색을 완료한 이후
            if(idx >= numbers.size) {
                // target이랑 동일한 경우 1을 반환, 그렇지 않으면 0을 반환
                // 동일한 값이 하나 존재한다는 의미로 1을 반환 즉, 카운팅의 의미
                return if(cur == target) 1 else 0
            }
            
            // 현재 값을 더한 경우, 뺀 경우를 나눠서 탐색
            return dfs(idx + 1, cur + numbers[idx]) + dfs(idx + 1, cur - numbers[idx])
        }
        
        return dfs(0, 0)
    }
}