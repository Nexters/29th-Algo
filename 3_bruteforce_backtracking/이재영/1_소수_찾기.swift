//
//  1_소수_찾기.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42839
//
//  Created by jerry on 7/29/26.
//
import Foundation

/*
 [의사코드: 소수 찾기]
 1. 입력받은 문자열을 순회하여 숫자(0~9)별 개수를 배열(countOfNumbers)에 저장한다.
 2. DFS를 이용해 가능한 모든 숫자의 배열 조합을 완전 탐색한다.
    2.1. 배열에 숫자를 추가하고, 확장(Extension) 로직을 통해 정수(Int)로 변환한 뒤 Set에 저장해 중복을 방지한다.
    2.2. 사용한 숫자의 잔여 개수를 차감하고 재귀 호출을 통해 다음 자릿수를 생성한다.
    2.3. defer 블록을 통해 재귀 호출이 끝나면 사용한 숫자의 개수를 복구(백트래킹)한다.
    2.4. 배열의 길이가 입력 문자열의 길이와 같아지면 탐색을 종료한다.
 3. Set에 모인 생성된 숫자들을 순회하며 isPrime 확장을 통해 소수인지 판별한다.
 4. 소수로 판별된 숫자의 총개수를 반환한다.
*/

func solution(_ numbers: String) -> Int {
    var countOfNumbers = [Int](repeating: 0, count: 10)
    var makedNumbers = Set<Int>()
    var answer = 0
    
    // 1. 사용할 수 있는 숫자 개수 집계
    for number in numbers {
        guard let value = number.wholeNumberValue else { continue }
        countOfNumbers[value] += 1
    }
    
    // 2. DFS를 통한 배열 기반 완전 탐색
    func dfs(_ current: [Int]) {
        guard current.count < numbers.count else { return }
        
        for number in 0...9 {
            guard countOfNumbers[number] > 0 else {
                continue
            }
            
            var newValue = current
            newValue.append(number)
            
            // 커스텀 Int 생성자 및 Array 확장을 사용한 정수 변환
            makedNumbers.insert(newValue.joined())
            
            countOfNumbers[number] -= 1
            defer { countOfNumbers[number] += 1 }
            
            dfs(newValue)
        }
    }
    
    dfs([])
    
    // 3. 소수 개수 확인
    for number in makedNumbers {
        if number.isPrime {
            answer += 1
        }
    }
    
    return answer
}

// MARK: - 소수 판별

extension Int {
    
    private var roundedSqrt: Int { Int(sqrt(Double(self))) }
    private var isEven: Bool { self % 2 == 0 }
    
    var isPrime: Bool {
        // 2, 3 인 경우 소수
        if self == 2 || self == 3 {
            return true
            
        // 2보다 작거나, 짝수인 경우 필터
        } else if self < 2 || isEven {
            return false
        }
        
        // 3부터 제곱근까지 홀수만 탐색
        for div in stride(from: 3, through: roundedSqrt, by: 2) {
            guard self % div != 0 else { return false }
        }
        
        return true
    }
}

//MARK: - 숫자 변환

extension Array<Int> {
    
    func joined() -> Int {
        var newValue = self
        while newValue.last == 0 {
            newValue.removeLast()
        }
        
        var value = 0
        var mult = 1
        
        for element in newValue {
            value += element * mult
            mult *= 10
        }
        
        return value
    }
}

