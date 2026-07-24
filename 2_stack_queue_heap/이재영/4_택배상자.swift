//
//  4_택배상자.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/131704
//
//  Created by jerry on 7/23/26.
//

func solution_0(_ order: [Int]) -> Int {
    var mainContainer: [Int] = [Int](1...order.count).reversed()
    var subContainer = [Int]()
    var count = 0
    
    for target in order {
        
        // 1. 메인 컨테이너에 타겟 박스가 있는 경우:
        if let mainLast = mainContainer.last, mainLast <= target {
            
            // 메인 컨테이너에 박스가 있는 동안 반복
            // -> 문제의 조건 상, 타겟 박스의 숫자가 메인 컨테이너의 마지막 박스 숫자보다 클 수 없음
            while let top = mainContainer.last {
                
                // 메인 컨테이너의 Top 이 타겟 박스인 경우:
                // Top을 꺼내 카운트 후
                // while 루프 중단
                if top == target {
                    mainContainer.removeLast()
                    count += 1
                    break
                    
                // 메인 컨테이너의 Top 이 타겟 박스가 아닌 경우:
                // 메인 컨테이너의 Top 을 서브 컨테이너에 Push 후,
                // while 루프 진행
                } else {
                    subContainer.append(mainContainer.removeLast())
                }
            }
            
        // 2. 서브 컨테이너의 Top 이 타겟 박스인 경우:
        // Top 을 꺼내고, 카운트
        } else if subContainer.last == target {
            subContainer.removeLast()
            count += 1
        
        // 3. 메인 컨테이너에 타겟 박스가 존재하지 않거나 서브 컨테이너의 Top이 아닌 경우:
        // 택배 싣기 중단
        } else {
            break
        }
    }
    
    return count
}



func solution_1(_ order: [Int]) -> Int {
    var currentBox = 1         // 메인 컨테이너에서 나올 다음 상자 번호
    var subContainer = [Int]() // 보조 컨테이너 (스택 구조)
    var count = 0              // 트럭에 실은 상자 개수
    
    for target in order {
        // 1. 목표 상자(target)에 도달할 때까지 메인에서 빼서 보조 컨테이너에 보관
        while currentBox <= target {
            subContainer.append(currentBox)
            currentBox += 1
        }
        
        // 2. 보조 컨테이너의 맨 위(마지막) 상자가 목표 상자인지 확인
        guard subContainer.last == target else {
            break
        }
        
        // 3. 목표 상자를 찾을 수 없다면 더 이상 실을 수 없으므로 종료
        subContainer.removeLast()
        count += 1
    }
    
    return count
}
