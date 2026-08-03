class Solution {
    fun solution(relation: Array<Array<String>>): Int {
        val rowCnt = relation.size
        val columnCnt = relation[0].size

        // 모든 컬럼 조합을 한 번의 DFS로 생성
        val combinations = mutableListOf<List<Int>>()

        fun makeCombination(
            start: Int,
            picked: List<Int>,
        ) {
            if (picked.isNotEmpty()) combinations.add(picked)
            for (i in start..<columnCnt) {
                makeCombination(i + 1, picked + i)
            }
        }
        makeCombination(0, emptyList())

        // 최소성 판정은 작은 집합이 먼저 확정돼 있어야 성립
        combinations.sortBy { it.size }

        // 유일성: 조합으로 만든 행 값들이 전부 달라야 함
        fun isUnique(columns: List<Int>): Boolean {
            val seen = mutableSetOf<String>()
            for (row in relation) {
                seen.add(columns.joinToString(",") { row[it] })
            }
            return seen.size == rowCnt
        }

        val keys = mutableListOf<List<Int>>()

        for (columns in combinations) {
            // 최소성: 이미 확정된 후보키를 포함하면 탈락
            if (keys.any { columns.containsAll(it) }) continue
            if (isUnique(columns)) keys.add(columns)
        }

        return keys.size
    }
}

/**
 * 유일성, 최소성을 만족
 *  - 릴레이션의 모든 튜플을 유일하게 식별하는 데 꼭 필요한 속성으로만 구성
 *  
 *  한 번 만족한 칼럼에 대한 재사용은 X
 *  
 *  최소성을 만족하기 위해 같은 depth에서 탐색을 위해 bfs를 사용했으나 특정 케이스르 잡지 못함
 *      - [0, 1, 2]랑 [1, 2]가 후보 키로 판별되는 문제
 *  
 *  컨셉 : 브루트포스로 전체 칼럼 조합을 생성, 정렬을 통해 칼럼의 수가 조합부터 탐색
 *      이전에 후보 키로 등록된 조합인 경우 pass 아닌 경우 +1
 */