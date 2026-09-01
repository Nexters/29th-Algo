import Foundation

func solution(_ n: Int, _ vertex: [[Int]]) -> Int {
    var graph = Array(repeating: [Int](), count: n + 1)
    var dist = Array(repeating: -1, count: n + 1)

    for v in vertex {
        graph[v[0]].append(v[1])
        graph[v[1]].append(v[0])
    }

    dist[1] = 0
    var queue = [1]

    while !queue.isEmpty {
        let curr = queue.removeFirst()

        for next in graph[curr] {
            if dist[next] == -1 {
                dist[next] = dist[curr] + 1
                queue.append(next)
            }
        }
    }

    let maxDist = dist.max()!
    return dist.filter { $0 == maxDist }.count
}
