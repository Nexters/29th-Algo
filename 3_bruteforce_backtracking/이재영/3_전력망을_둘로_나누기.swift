//
//  3_전력망을_둘로_나누기.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/86971
//
//  Created by jerry on 7/29/26.
//

/*
 트리형태로 이뤄진 송전탑
 
 하나의 전선을 끊어, 두 개의 전력망 네트워크로 분할
 두 개의 전력망이 가진 송전탑 개수의 차이 중 최솟값
 
 백트래킹, 연결된 노드의 수가 기록된 최솟값 보다 큰 경우 중단.
 
 탐섹이 끝났을 때, 연결된 노드의 수가 (n/2)인 경우 중단 -> 최솟값
 
 
 // bfs 종료 조건
 // 최솟값이 나온 경우, minCount == n / 2 (중단)
 // 현재 연결된 노드의 수가 maxCount 보다 크거나 같다. (중단)
 // 더 이상 연결할 노드가 없다.
 //    maxCount, minCount 저장
*/

func solution(_ n: Int, _ wires: [[Int]]) -> Int {
    var maxCount = n
    var minCount = 0
    var abs: Int { maxCount - minCount }
    let minimumAbs = n % 2
    
    
    let graph: [Int: [Int]] = {
        var graph = [Int: [Int]]()
        for wire in wires {
            graph[wire[0], default: []].append(wire[1])
            graph[wire[1], default: []].append(wire[0])
        }
        return graph
    }()
    
    func bfs(_ cuttedWire: (lhs: Int, rhs: Int)) {
        guard abs != minimumAbs else { return }
        
        var newGraph = graph
        newGraph[cuttedWire.lhs]?.removeAll(where: { $0 == cuttedWire.rhs })
        newGraph[cuttedWire.rhs]?.removeAll(where: { $0 == cuttedWire.lhs })
        
        var isVisited = [Bool](repeating: false, count: n + 1)
        isVisited[1] = true
        var size = 1
        let queue = Queue()
        queue.enqueue(1)
        
        while let node = queue.dequeue() {
            guard size < maxCount else { return }
            
            newGraph[node, default: []].forEach {
                guard !isVisited[$0] else { return }
                isVisited[$0] = true
                queue.enqueue($0)
                size += 1
            }
        }
        let currentMax = max(size, n - size)
        
        if currentMax < maxCount {
            maxCount = currentMax
            minCount = n - currentMax
        }
    }
        
    for wire in wires {
        bfs((lhs: wire[0], rhs: wire[1]))
    }
    
    return abs
}


final class Queue {
    private var elements = [Int]()
    private var index = 0
    
    var isEmpty: Bool { index >= elements.count }
    
    func enqueue(_ element: Int) {
        elements.append(element)
    }
    
    func dequeue() -> Int?  {
        guard !isEmpty else { return nil }
        defer { index += 1 }
        return elements[index]
    }
}
