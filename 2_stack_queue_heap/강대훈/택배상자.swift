import Foundation

func solution(_ order:[Int]) -> Int {
    var currentNumber = 1
    var stack: [Int] = []
    var cnt = 0

    for i in 0..<order.count {
        while currentNumber <= order[i] {
            stack.append(currentNumber)
            currentNumber += 1
        }

        if stack.last == order[i] {
            cnt += 1
            _ = stack.popLast()
        } else {
            break
        }
    }

    return cnt
}
