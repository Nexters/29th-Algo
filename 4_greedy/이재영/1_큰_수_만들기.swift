//
//  1_큰_수_만들기.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42883
//

import Foundation
/*
 stack 에서의 top 을 최솟값으로 유지한다.
 
*/
func solution(_ numbers: String, _ k: Int) -> String {
    var stack = [Character]()
    var removeCount = 0

    for number in numbers {
        while removeCount < k {
            guard let top = stack.last, top < number else {
                break
            }
            stack.removeLast()
            removeCount += 1
        }
        stack.append(number)
    }
    
    return String(stack.dropLast(k - removeCount))
}
