class Solution {
    fun find(parent: IntArray, x: Int): Int {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x])
        }

        return parent[x]
    }

    fun union(parent: IntArray, a: Int, b: Int) {
        var _a = find(parent, a)
        var _b = find(parent, b)

        // 기준: 큰 숫자가 작은 숫자 밑으로 들어감
        if (_a < _b)
            parent[_b] = _a
        else
            parent[_a] = _b
    }

    fun solution(n: Int, costs: Array<IntArray>): Int {
        var answer = 0

        val _costs = costs.sortedBy { it[2] }
        val parent = IntArray(n + 1) { it }
        
        for ((a, b, c) in _costs) {
            // 같은 부모를 공유 = 같은 집합 = 이미 간선이 형성
            if(find(parent, a) != find(parent, b)) {
                union(parent, a, b)
                answer += c
            }
        }

        return answer
    }
}

/** 
 * n개의 섬 사이에 다리를 건설하는 비용 costs가 주어짐 
 * 
 * 모든 섬이 통행 가능하게 하면서 최소 금액을 구해야함 
 * 최소 스패닝 트리를 구하는 문제
 * 
 * 크루스칼 알고리즘을 활용 <- 서로소 알고리즘
 */
