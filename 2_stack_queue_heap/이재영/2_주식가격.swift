//
//  2_주식가격.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42584
//
//  Created by jerry on 7/23/26.
//  Swift 미지원

/*

 가격을 담는 스택을 오름차순 정렬이 되어있는 상태를 유지시킨다.
 
 1. top 보다 push 값이 낮은 경우
    (스택이 비어있지 않고, top이 낮은 경우) 루프 -> pop 시간 기록
 
 2. top 보다 값이 크거나 같은 경우
    스택에 추가
 
 */

func solution(_ prices: [Int]) -> [Int] {
    // 아직 가격이 떨어지지 않은 시점(인덱스)을 기록할 스택
    var stack = [Int]()
    var answer = [Int](repeating: 0, count: prices.count)
    
    // [1단계: 순회] 전체 시간을 순차적으로 탐색
    for currentTime in 0..<prices.count {
        let price = prices[currentTime]

        // [비교] 스택의 최상단 가격(과거) > 현재 가격
        // 가격이 떨어졌다면 유지된 시간(현재 시간 - 과거 시간)을 기록하고 스택에서 제거
        while let top = stack.last, prices[top] > price {
            answer[top] = currentTime - stack.removeLast()
        }
        
        // [기록] 현재 시간을 스택에 추가
        stack.append(currentTime)
    }
    
    let lastTime = prices.count - 1
    
    // [2단계: 잔여 처리] 끝까지 가격이 떨어지지 않은 시점들
    // 유지된 시간(마지막 시간 - 과거 시간)을 기록하고 스택에서 제거
    while let top = stack.last {
        answer[top] = lastTime - top
        stack.removeLast()
    }
    
    return answer
}
