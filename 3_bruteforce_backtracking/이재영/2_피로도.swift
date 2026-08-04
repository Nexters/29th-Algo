func solution(_ k: Int, _ dungeons: [[Int]]) -> Int {
    
    // 방문 상태 확인
    var isVisitedDungeon = [Bool](repeating: false, count: dungeons.count)
    // 현재까지 방문한 던전이 동일하다면 피로도 소모가 같으므로,
    // 중복되는 경로로 볼 수 있다.
    var isVisitedRoute = [Bool](repeating: false, count: 256)
    
    var maxCount = 0
    
    func dfs(_ remain: Int, _ visitedCount: Int) {
        guard maxCount < dungeons.count else { return }
        
        for i in 0..<dungeons.count {
            // 현재의 경로에서 방문했던 던전이 아니며,
            // 남은 피로도가 요구 피로도 이상인지
            guard !isVisitedDungeon[i],
                  remain >= dungeons[i][0] else { continue }
            
            // 방문 체크
            isVisitedDungeon[i] = true
            // 방문 체크 복원
            defer { isVisitedDungeon[i] = false }
            
            // 방문 루트인지 확인
            let routeID = isVisitedDungeon.routeID
            
            if !isVisitedRoute[routeID] {
                // 루트 방문 체크
                isVisitedRoute[routeID] = true
                
                maxCount = max(maxCount, visitedCount + 1)
                
                dfs(remain - dungeons[i][1], visitedCount + 1)
            }
        }
    }
    dfs(k, 0)
    
    return maxCount
}

extension [Bool] {
    
    var routeID: Int {
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
