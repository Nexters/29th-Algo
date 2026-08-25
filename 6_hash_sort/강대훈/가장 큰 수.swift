func solution(_ numbers:[Int]) -> String {
    let strNumbers = numbers.map { String($0) }
    let sorted = strNumbers.sorted { $0 + $1 > $1 + $0 }
    let ans = sorted.joined()
    return Int(ans) == 0 ? "0" : ans
}
