import Foundation

func solution(_ stones: [Int], _ k: Int) -> Int {
    var lo = 1
    var hi = stones.max()!
    var answer = 0

    func canCross(_ x: Int) -> Bool {
        var consecutive = 0
        for stone in stones {
            if stone - x < 0 {
                consecutive += 1
                if consecutive >= k {
                    return false
                }
            } else {
                consecutive = 0
            }
        }
        return true
    }

    while lo <= hi {
        let mid = (lo + hi) / 2
        if canCross(mid) {
            answer = mid
            lo = mid + 1
        } else {
            hi = mid - 1
        }
    }

    return answer
}
