import Foundation

func solution(_ numbers:String) -> Int {
    var list = numbers.map { $0 }
    var visited = [Bool](repeating: false, count: list.count)
    var res = Set<Int>()
    var cnt = 0
    
    dfs(0, "")
    
    for val in res {
        if isPrime(val) { cnt += 1 }
    }
    
    return cnt
    
    func dfs(_ n: Int, _ str: String) {
        res.insert(Int(str) ?? 0)
        
        for i in 0..<numbers.count {
            if visited[i] { continue }
            visited[i] = true
            dfs(i, str + String(list[i]))
            visited[i] = false
        }
    }
    
    func isPrime(_ n: Int) -> Bool {
        if n < 2 { return false }
        
        for i in 2..<n {
            if n % i == 0 { return false }
        }
        
        return true
    }
}
