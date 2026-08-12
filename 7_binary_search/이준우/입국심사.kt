class Solution {
    fun solution(n: Int, times: IntArray): Long {
        // 최악의 경우 : 제일 빠르게 끝내는 사람이 n명 모두를 커버했을 경우
        var answer = times.min().toLong() * n
        
        // start ~ end => 시간의 범위
        var start = 0L
        var end = answer
        
        while(start <= end) {
            val mid = (start + end) / 2
            // mid 시간일 때 몇 명을 받을 수 있는지 카운트
            var cnt = times.sumOf { mid / it.toLong() }
            
            // n명보다 적으면, 더 큰 범위여야 하기에 저점을 높임
            if(cnt < n) {
                start = mid + 1    
            }
            // n보다 많으면, 더 작은 범위여야 하기에 고점을 높임 
            else {
                answer = minOf(answer, mid)
                end = mid - 1
            }
        }
        
        return answer
    }
}

/**
 * 컨셉 : 이진 탐색으로 N명을 수용할 수 있는 최적의 시간을 찾음
 * (현재 시간 / 걸리는 시간) = 현재 시간에 안내 테스크가 수용할 수 있는 인원의 수
 *      이 합을 근거로 이진탐색을 수행 
 *      
 * Long.MAX_VALUE로 풀이 했더니 오버플로가 터짐
 */