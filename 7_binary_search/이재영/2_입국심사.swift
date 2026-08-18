//
//  2_입국심사.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/43238
//
//  Created by jerry on 8/17/26.
//

func solution(_ n: Int, _ times: [Int]) -> Int64 {
    
    func isPossible(_ limitTime: Int) -> Bool {
        var totalSuccessed = 0
        
        for time in times {
            totalSuccessed += limitTime / time
        }
        return totalSuccessed >= n
    }
    
    return binarySearch(0, 1_000_000_000 * n, isPossible)
}

func binarySearch(
    _ min: Int,
    _ max: Int,
    _ condition: (Int) -> Bool
) -> Int64 {
    var left = min
    var right = max
    var mid = (left + right) / 2
    var answer = mid
    
    while left <= right {
        if condition(mid) {
            right = mid-1
            answer = mid
        } else {
            left = mid + 1
        }
        mid = (left + right) / 2
    }
    
    return Int64(answer)
}
