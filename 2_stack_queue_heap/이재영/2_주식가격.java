//
//  2_주식가격.java
//  https://school.programmers.co.kr/learn/courses/30/lessons/42584
//
//  Created by jerry on 7/28/26.
//  **Swift로 작성해 번역시켰습니다.**

/*
 가격을 담는 스택을 오름차순 정렬이 되어있는 상태를 유지시킨다.
 
 1. top 보다 push 값이 낮은 경우
 (스택이 비어있지 않고, top이 낮은 경우) 루프 -> pop 시간 기록
 
 2. top 보다 값이 크거나 같은 경우
 스택에 추가
 */

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    
    public int[] solution(int[] prices) {
        // 아직 가격이 떨어지지 않은 시점(인덱스)을 기록할 스택
        Deque<Integer> stack = new ArrayDeque<>();
        int[] answer = new int[prices.length];
        
        // [1단계: 순회] 전체 시간을 순차적으로 탐색
        for (int currentTime = 0; currentTime < prices.length; currentTime++) {
            int price = prices[currentTime];
            
            // [비교] 스택의 최상단 가격(과거) > 현재 가격
            // 가격이 떨어졌다면 유지된 시간(현재 시간 - 과거 시간)을 기록하고 스택에서 제거
            while (!stack.isEmpty() && prices[stack.peek()] > price) {
                int top = stack.pop();
                answer[top] = currentTime - top;
            }
            
            // [기록] 현재 시간을 스택에 추가
            stack.push(currentTime);
        }
        
        int lastTime = prices.length - 1;
        
        // [2단계: 잔여 처리] 끝까지 가격이 떨어지지 않은 시점들
        // 유지된 시간(마지막 시간 - 과거 시간)을 기록하고 스택에서 제거
        while (!stack.isEmpty()) {
            int top = stack.pop();
            answer[top] = lastTime - top;
        }
        
        return answer;
    }
}
