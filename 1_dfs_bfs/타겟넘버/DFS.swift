func solution(_ numbers: [Int], _ target: Int) -> Int {
    var cache = [[Int: Int]](repeating: [:], count: numbers.count)
    
    func dfs(_ index: Int, _ value: Int) -> Int {
        if index == numbers.count {
            return value == target ? 1 : 0
            
        } else if let cached = cache[index][value] {
            return cached
            
        } else {
            let addCount = dfs(index + 1, value + numbers[index])
            let subCount = dfs(index + 1, value - numbers[index])
            
            let totalCount = addCount + subCount
            cache[index][value] = totalCount
            
            return totalCount
        }
    }
    
    return dfs(0, 0)
}
