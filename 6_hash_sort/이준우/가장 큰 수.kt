class Solution {
    fun solution(numbers: IntArray): String {
        val _numbers = numbers.map { it.toString() }.sortedWith(
            Comparator { a, b ->
                // 내림차순이기에 b가 먼저
                b.repeat(3).compareTo(a.repeat(3))
            }
        )


        return if (_numbers[0] == "0") return "0" else _numbers.joinToString("")
    }
}

/** 
 * 0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수
 * 
 * 
 * 앞 자리가 큰 수로 정렬해야 함
 * 30과 3이 있을 떄는 330이 되도록 
 *  - 3 > 30
 *      - 30에 자릿수에 맞춰서 뒤에 3을 붙인 후 비교
 *      
 * 정렬할 때 3배를 곱하고 계산
 *  333 > 303030
 */
