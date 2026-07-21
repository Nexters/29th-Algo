func solution(_ n: Int, _ computers: [[Int]]) -> Int {
    
    var count = 0
    var isVisited = [Bool](repeating: false, count: n)
    let connected: [[Int]] = {
        var connected = [[Int]](repeating: [Int](), count: n)
        for i in 0..<n {
            for j in 0..<n {
                if computers[i][j] == 1 && i != j {
                    connected[i].append(j)
                }
            }
        }
        return connected
    }()
    
    func visit(_ startNode: Int) {
        let queue = Queue()
        queue.enqueue(startNode)
        isVisited[startNode] = true
        
        while let nexNode = queue.dequeue() {
            for connectedNode in connected[nexNode] {
                guard !isVisited[connectedNode] else { continue }
                queue.enqueue(connectedNode)
                isVisited[connectedNode] = true
            }
        }
    }
    
    for i in 0..<n {
        guard !isVisited[i] else { continue }
        visit(i)
        count += 1
    }
    
    return count
}

final class Queue {
    private var elements = [Int]()
    private var index = 0
    var isEmpty: Bool { elements.count <= index }
    
    func enqueue(_ element: Int) {
        elements.append(element)
    }
    
    func dequeue() -> Int? {
        guard !isEmpty else { return nil }
        defer { index += 1 }
        return elements[index]
    }
}
