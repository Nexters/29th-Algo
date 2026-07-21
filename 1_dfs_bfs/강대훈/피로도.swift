import Foundation

func solution(_ k: Int, _ dungeons: [[Int]]) -> Int {
    var ans = 0
    var visited = [Bool](repeating: false, count: dungeons.count)

    dfs(k, 0, 0)

    func dfs(_ hp: Int, _ idx: Int, _ cnt: Int) {
        ans = max(ans, cnt)

        if idx >= dungeons.count {
            return
        }

        // 1 2 3
        // 1 3 2
        // 2 1 3
        // 2 3 1
        // 3 1 2
        // 3 2 1

        for i in 0..<dungeons.count {
            if visited[i] { continue }
            if hp < dungeons[i][0] { continue }

            visited[i] = true
            dfs(hp - dungeons[i][1], idx + 1, cnt + 1)
            visited[i] = false
        }
    }

    return ans
}
