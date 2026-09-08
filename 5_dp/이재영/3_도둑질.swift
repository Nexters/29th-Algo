//
//  3_도둑질.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42897
//
//  Created by jerry on 9/8/26.
//

import Foundation
/*
 
 1. 처음 집 방문 O -> 마지막 집 방문 불가
 2. 처음 집 방문 X -> 마지막 집 방문 가능
 
 */
func solution(_ money: [Int]) -> Int {
    // 첫번째 집 방문
    let aCase = maxValue(money[0], 2, money.dropLast())
    // 첫번째 집 미방문
    let bCase = maxValue(0, 1, money)
    
    return max(aCase, bCase)
}

func maxValue(_ startValue: Int, _ startIndex: Int, _ money: [Int]) -> Int {
    var enable = startValue
    var disable = startValue
    
    for i in startIndex..<money.count {
        var newEnable = max(disable, enable)
        var newDisable = enable + money[i]
        
        enable = newEnable
        disable = newDisable
    }
    
    return max(enable, disable)
}
