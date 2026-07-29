class Solution {
    fun solution(numbers: String): Int {
        // "110" -> [1, 1, 0]
        val nums = numbers.toList().map { it.digitToInt() }

        // numbers로 만들어질 수 있는 숫자 조합 저장
        val combinations = mutableSetOf<Int>()

        // list -> 현재까지 거쳐온 인덱스를 "순서"대로 저장 <- 순열 문제기 때문
        fun dfs(list: MutableList<Int>) {
            // list는 인덱스이기 때문에 num에 의미있는 값으로 변환
            val n = list.map { nums[it] }.joinToString("").toInt()
            combinations.add(n)

            // 브루트포스 탐색
            for (i in nums.indices) {
                if (i !in list) {
                    list.add(i)
                    dfs(list)
                    list.removeLast()
                }
            }
        }

        for (i in nums.indices) {
            dfs(mutableListOf(i))
        }

        var cnt = 0
        for (c in combinations) {
            cnt += isPrime(c)
        }

        return cnt
    }

    // 소수인지 판별하는 방법 <- 외워야 함
    fun isPrime(n: Int): Int {
        if (n < 2) return 0
        val end = Math.sqrt(n.toDouble()).toInt()

        // 2부터 end까지의 숫자 중, n을 나누어떨어지게 하는 수가 '하나도 없다'면 true
        return if ((2..end).none { n % it == 0 }) 1 else 0
    }
}

/**
 * 루트를 씌우는 이유 :
 *  어떤 수 \(n\)의 약수들을 곱하기 형태(\(a \times b = n\))로 나타내면 언제나 두 숫자가 짝을 이룹니다.
 *  16 = 4 * 4 -> 따라서 n을 나누어떨어지게 하고 싶은 진짜 약수가 존재하는지 찾고 싶다면 루트(n)까지만 확인하면 됨
 */