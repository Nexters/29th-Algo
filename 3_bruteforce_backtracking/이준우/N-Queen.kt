import kotlin.math.*

class Solution {
    fun solution(n: Int): Int {
        // bard[row] = col
        val board = IntArray(n)

        // 같은 열 or 대각선에 퀸이 존재하는지 체크 
        fun isCheck(row: Int, col: Int): Boolean {
            for (i in 0..<row) {
                // 같은 열에 있는지 혹은 대각선에 존재하는지 
                if (board[i] == col || (abs(row - i)) == abs(col - board[i])) {
                    return false
                }
            }

            return true
        }

        fun bf(row: Int): Int {
            // 끝까지 탐색 == 문제 조건에 만족
            if (row == n) {
                return 1
            }

            var result = 0
            for (col in 0..<n) {
                if (isCheck(row, col)) {
                    board[row] = col
                    result += bf(row + 1)
                }
            }

            return result
        }
        return bf(0)
    }
}

/**
 * n*n 개의 보드에서 n개의 퀸이 서로 잡을 수 없는 경우의 수 
 * 
 * 컨셉 : n*n의 보드라는 점에서 2차원 배열을 써야할 것 같지만, 
 * 문제의 요구사항을 만족하려면 퀸은 같은 행, 같은 열에 존재할 수 없다
 * 즉, 1차원 배열만 사용해서 구현할 수 있다.
 * 
 * n의 최대 수가 12라 2중 for문으로 풀면 풀릴 줄 앎...
 */