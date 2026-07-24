import Foundation

func solution(_ priorities:[Int], _ location:Int) -> Int {
    var count = 0
    var loc = location
    var queue = priorities

    while !queue.isEmpty {
        let maxValue = queue.max()!
        let value = queue.removeFirst()

        if value >= maxValue {
            count += 1

            if loc == 0 {
                break
            }
        } else {
            queue.append(value)
        }

        loc = loc == 0 ? queue.count - 1 : loc - 1
    }

    return count
}
