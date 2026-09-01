import Foundation

func solution(_ N: Int, _ road: [[Int]], _ K: Int) -> Int {
    var graph = Array(repeating: [(Int, Int)](), count: N + 1)
    var dist = Array(repeating: Int.max, count: N + 1)

    for r in road {
        graph[r[0]].append((r[1], r[2]))
        graph[r[1]].append((r[0], r[2]))
    }

    dist[1] = 0
    var queue = [(1, 0)]

    while !queue.isEmpty {
        let (curr, currDist) = queue.removeFirst()

        // 저장된 거리보다 멀면 패스
        if currDist > dist[curr] {
            continue
        }


        for (next, cost) in graph[curr] {
            let newDist = currDist + cost

            if newDist < dist[next] {
                dist[next] = newDist
                queue.append((next, newDist))
            }
        }
    }

    return dist.filter { $0 <= K }.count
}
