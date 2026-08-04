import Foundation

func solution(_ n:Int, _ wires:[[Int]]) -> Int {
    var graph = [[Int]](repeating: [], count: n + 1)
    var ans = Int.max

    for wire in wires {
        graph[wire[0]].append(wire[1])
        graph[wire[1]].append(wire[0])
    }

    for wire in wires {
        let (v1, v2) = (wire[0], wire[1])
        var visited = [Bool](repeating: false, count: n + 1)
        var cnt = 0

        visited[v1] = true
        visited[v2] = true

        dfs(v1)

        ans = min(ans, abs(cnt - (n - cnt)))

        func dfs(_ n: Int) {
            cnt += 1

            for next in graph[n] {
                if visited[next] { continue }
                visited[next] = true
                dfs(next)
            }
        }
    }

    return ans
}

// 1 : [2]
// 2 : [1, 7]
// 3 : [4, 7]
// 4 : [3, 5]
// 5 : [4]
// 6 : [7]
// 7 : [2, 3, 6]
