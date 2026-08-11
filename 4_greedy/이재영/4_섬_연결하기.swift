//
//  4_섬_연결하기.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42861
//

import Foundation

/*
 모든 섬을 연결하기 위한 최소비용을 구해야한다.
 n은 섬의 수
 두 섬(costs[i][0], costs[i][1])을 연결하는 비용 == costs[i][2]
*/

func solution(_ n: Int, _ costs: [[Int]]) -> Int {
    var totalCost = 0
    
    // 1. parent 배열을 자기 자신의 인덱스로 초기화
    var parent = Array(0..<n)
    
    // 2. 표준 경로 압축 적용
    func find(_ current: Int) -> Int {
        if parent[current] == current {
            return current
        }
        parent[current] = find(parent[current])
        return parent[current]
    }
    // 3. 다리 건설 비용이 작은 순서로 탐색
    for i in costs.sorted { $0[2] < $1[2] } {
        let u = i[0]
        let v = i[1]
        
        let cost = i[2]
        
        let rootU = find(u)
        let rootV = find(v)
        
        // root 가 같다면 연결되어있는 섬
        if rootU != rootV {
            // root 를 취합해 이후 find 에서 연결된 섬 모두 업데이트
            parent[rootV] = rootU
            totalCost += cost
        }
    }
    
    return totalCost
}
