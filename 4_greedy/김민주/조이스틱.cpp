#include <string>
#include <vector>
#include <iostream>
#include <stdio.h>

using namespace std;

int solution(string name) {
    int answer = 0;
    int n = name.length();
    int min_move = n - 1; // 기본: 오른쪽 끝까지 순차 이동

    for (int i = 0; i < n; i++) {
        // 1. 알파벳 변경 조작 횟수 (상하)
        answer += min(name[i] - 'A', 'Z' - name[i] + 1);

        // 2. 연속된 A 확인 후 최소 이동값 계산 (좌우)
        int next = i + 1;
        while (next < n && name[next] == 'A') {
            next += 1;
        }

        // i까지 갔다 돌아와서 뒤쪽부터 방문 vs 뒤쪽 먼저 방문 후 i로 오기
        min_move = min(min_move, i + n - next + min(i, n - next));
    }

    return answer + min_move;
    
    
}