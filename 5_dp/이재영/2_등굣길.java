class Solution {
    private static final int MOD = 1_000_000_007;

    public int solution(int m, int n, int[][] puddles) {
        int[][] board = new int[n][m];
        boolean[][] isPuddle = new boolean[n][m];

        // 물웅덩이 세팅
        for (int[] puddle : puddles) {
            int x = puddle[0] - 1;
            int y = puddle[1] - 1;
            isPuddle[y][x] = true;
        }

        board[0][0] = 1;

        // 대각선 순회 (distance = row + column)
        for (int distance = 1; distance < (m + n); distance++) {
            for (int row = 0; row <= Math.min(distance, n - 1); row++) {
                int col = distance - row;
                
                // 유효하지 않은 인덱스 처리
                if (col >= m) {
                    continue;
                }
                if (col < 0) {
                    break;
                }
                
                // 물웅덩이인 경우 경로 수를 0으로 유지
                if (isPuddle[row][col]) {
                    continue;
                }
                
                int paths = 0;
                // 위쪽에서 오는 경로 더하기
                if (row > 0) {
                    paths = (paths + board[row - 1][col]) % MOD;
                }
                // 왼쪽에서 오는 경로 더하기
                if (col > 0) {
                    paths = (paths + board[row][col - 1]) % MOD;
                }
                
                board[row][col] = paths;
            }
        }
        
        return board[n - 1][m - 1];
    }
}
