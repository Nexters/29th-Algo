class Solution {
    fun solution(distance: Int, rocks: IntArray, n: Int): Int {
        var answer = 0
        val _rocks = rocks.toMutableList()
        _rocks.add(distance)
        _rocks.sort()

        var left = 1
        var right = distance

        while (left <= right) {
            val minGap = (left + right) / 2

            var removed = 0
            var current = 0

            for (rock in _rocks) {
                if (rock - current < minGap) removed++
                else current = rock
            }

            if (removed > n) {
                right = minGap - 1
            } else {
                answer = minGap
                left = minGap + 1
            }
        }

        return answer
    }
}
