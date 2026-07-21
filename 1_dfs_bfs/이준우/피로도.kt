class Solution {
    fun solution(
        k: Int,
        dungeons: Array<IntArray>,
    ): Int {
        val dungeonList = dungeons.toList()

        // 어느 던전으로 들어갈 것인지에 대한 선택 
        fun dfs(
            cur: Int,
            list: List<IntArray>,
        ): Int {
            if (cur <= 0 && list.isEmpty()) {
                return 1
            }

            var tmp = 0
            for ((i, d) in list.withIndex()) {
                // 접근 가능한 던전인 경우
                if (d[0] <= cur) {
                    tmp = Math.max(tmp, 1 + dfs(cur - d[1], list.filterIndexed { idx, _ -> idx != i }))
                }
            }

            return tmp
        }
        
        return dfs(k, dungeonList)
    }
}

/*
최소 필요 피로도 : 각 던전마다 타험을 시작하기 위해 필요한 피로도
소모 피로도 : 던전 탐험을 머쳤을 때 소모되는 피로도

최대한 많은 던전을 목표로
 */