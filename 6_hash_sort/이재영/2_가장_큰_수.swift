//
//  2_가장_큰_수.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42746
//
//  Created by jerry on 8/25/26.
//

import Foundation

func solution(_ numbers: [Int]) -> String {
    let sorted = numbers
        .map { String($0) }
        //문자열로 합쳤을 때를 기준으로 정렬
        // 10, 9 -> "109" "910" -> 9 > 10
        .sorted { ($0 + $1) > ($1 + $0) }
    // 만약 가장 앞의 값이 "0" 인 경우, "00000" 와 같은 엣지케이스 예외처리
    return sorted[0] != "0" ? sorted.joined() : "0"
}
