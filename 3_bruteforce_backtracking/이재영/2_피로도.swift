func solution(_ k: Int, _ dungeons: [[Int]]) -> Int {
    var isVisitedDungeon = [Bool](repeating: false, count: dungeons.count)
    var isVistedCase = [Bool](repeating: false, count: 256)

    func dfs(_ remain: Int) -> Int {
        var maxCount = 0
        for i in 0..<dungeons.count {
            guard !isVisitedDungeon[i] else { continue }
            
            if remain >= dungeons[i][0] {
                isVisitedDungeon[i] = true
                let rawValue = isVisitedDungeon.rawValue
                if !isVistedCase[rawValue] {
                    isVistedCase[rawValue] = true
                    maxCount = max(dfs(remain - dungeons[i][1]) + 1, maxCount)
                }
                isVisitedDungeon[i] = false
            }
        }
        
        return maxCount
    }
    
    return dfs(k)
}

extension [Bool] {
    var rawValue: Int {
        var rawValue = 0
        var radix = 1
        for isTrue in self {
            if isTrue {
                rawValue += radix
            }
            radix *= 2
        }
        
        return rawValue
    }
}
