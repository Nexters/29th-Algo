//
//  1_큰_수_만들기.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42883
//

import Foundation
/*
 stack 을 내림차순으로 정렬되게 유지한다.
 
 stack 에 먼저 들어간 수는 가장 높은 자릿 수를 맡게된다.
 
 (새로 담을 인자가 top 초과라면, pop) 루프
 새로 담을 인자가 top 이하라면, push)
 
 1. 제거가능한 수 만큼 제거했다면, 남은 숫자를 모두 담아 수 완성
 
 2. numbers를 모두 순행했을 때, 제거 가능 숫자가 남았다면, 남은 숫자만큼 top 에서 제거, top은 항상 최솟값이기 때문에 가능 
 
 
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
