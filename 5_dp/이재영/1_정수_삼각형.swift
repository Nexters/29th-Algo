//
//  1_정수_삼각형.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/43105
//
//  Created by jerry on 9/8/26.
//

import Foundation
/*
 nodes[i][j] -> max(nodes[i-1][max(0, j-1)],nodes[i-1][min(j, i-1)]) + triangle[i][j]
*/
func solution(_ triangle: [[Int]]) -> Int {
    var nodes = triangle
    
    for i in 1..<nodes.count {
        for j in 0..<nodes[i].count {
            nodes[i][j] += max(
                nodes[i-1][max(0, j-1)],
                nodes[i-1][min(j, i-1)]
            )
        }
    }
    
    return nodes[triangle.count-1].max()!
}
