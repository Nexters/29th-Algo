//
//  2_등굣길.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42898
//
//  Created by jerry on 9/8/26.
//

import Foundation

/*
 집 = (0, 0)
 학교 = (m-1, n-1)
 물에 잠긴 지역: puddles -> [[a, b]] -> (a - 1, b - 1)
 
 반환: { 최단 경로 수 } % 1,000,000,007
*/

func solution(_ m: Int, _ n: Int, _ puddles: [[Int]]) -> Int {
    var board = [[Int]](reapting: [Int](repeating: 0, count: m), count: n)
    board[0][0] = 1
    let enableBoard = nonePuddleBoard(m, n, puddles)
    
    func value(_ row: Int, _ column: Int) -> Int {
        guard 0 <= column && column < m, 0 <= row, row < n else { return 0 }
        return board[row][column]
    }
    
    func newValue(_ row: Int, _ column: Int) -> Int {
        guard enableBoard[row][column] else { return 0 }
        
        return (value(row, column-1) + value(row-1, column)) % 1_000_000_007
    }
    
    for distance in 1..<(m+n) {
        for row in 0...min(distance, n-1) {
            let column = distance - row
            guard column < m else { continue }
            guard column >= 0 else { break }
            board[row][column] = newValue(row, column)
        }
    }
            
    return board[n-1][m-1]
}

func nonePuddleBoard(_ m: Int, _ n: Int, _ pudles: [[Int]]) -> [[Bool]] {
    var puddleBoard = [[Bool]](reapting: [Bool](repeating: true, count: m), count: n)
    
    for puddle in puddles {
        let (x, y) = (puddle[0] - 1, puddle[1] - 1)
        puddleBoard[y][x] = false
    }
    
    return puddleBoard
}
