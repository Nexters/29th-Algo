//
//  1_정수_삼각형.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/43105
//
//  Created by jerry on 9/8/26.
//

import java.util.Arrays;
/*
 i번째 줄, j번째 노드에 도착하는 경우에서의 최대값
                    
 nodes[i][j] == max(nodes[i-1][max(0, j-1)], nodes[i-1][min(j, i-1)]) + triangle[i][j]
*/
class Solution {
    public int solution(int[][] triangle) {
        int[][] nodes = new int[triangle.length][];

        for (int i = 0; i < triangle.length; i++) {
            nodes[i] = triangle[i].clone();
        }

        for (int i = 1; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                nodes[i][j] += Math.max(
                    nodes[i - 1][Math.max(0, j - 1)],
                    nodes[i - 1][Math.min(j, i - 1)]
                );
            }
        }

        return Arrays.stream(nodes[nodes.length - 1])
            .max()
            .getAsInt();
    }
}
