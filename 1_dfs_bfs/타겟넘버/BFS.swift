func solution(_ numbers: [Int], _ target: Int) -> Int {
    var nodes: [Int: Int] = [0: 1]
    
    for number in numbers {
        var newNodes = [Int: Int]()
        
        for node in nodes.keys {
            newNodes[node + number, default: 0] += nodes[node, default: 0]
            newNodes[node - number, default: 0] += nodes[node, default: 0]
        }
        
        nodes = newNodes
    }
    
    return nodes[target, default: 0]
}
