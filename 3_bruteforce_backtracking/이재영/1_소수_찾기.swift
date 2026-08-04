//
//  1_소수_찾기.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42839
//
//  Created by jerry on 7/29/26.
//
import Foundation

func solution(_ numbers: String) -> Int {
   // 중복된 숫자가 입력되는 경우에 대한 최적화 - 동일한 숫자가 적힌 종이 조각은 순서와 상관없이 사용
   var countOfNumbers = [Int](repeating: 0, count: 10)
  
   // 1. 사용할 수 있는 숫자 개수 집계
   for number in numbers {
       guard let value = number.wholeNumberValue else { continue }
       countOfNumbers[value] += 1
   }
   
   // 2. DFS를 통한 배열 기반 완전 탐색
   // DFS를 통해 생성된 숫자들 중복없이 저장 (Set)
   var makedNumbers = Set<Int>()
   
    
   func dfs(_ current: String) {
       // 입력 값을 모두 쓸 때 까지
       guard current.count < numbers.count else { return }
       
       // 0 ~ 9
       for number in 0...9 {
           // 사용가능한 숫자인지 확인
           guard countOfNumbers[number] > 0 else {
               continue
           }
           
           var newValue = current
           newValue += "\(number)"
           // 커스텀 Int 생성자 및 Array 확장을 사용한 정수 변환
           makedNumbers.insert(Int(newValue)!)
           // 숫자 사용
           countOfNumbers[number] -= 1
           // 해당 스코프가 끝날 때 사용된 숫자 복원
           defer { countOfNumbers[number] += 1 }
           
           dfs(newValue)
       }
   }
   
   dfs("")
   
   // 3. 소수 개수 확인
   var answer = 0

   for number in makedNumbers {
       if number.isPrime {
           answer += 1
       }
   }
   
   return answer
}

// MARK: - 소수 판별

extension Int {
   /*
    n 에 대한 약수는 n == a * b ( 1 ≤ a ≤ √n ≤ b ≤ n ) 라는 쌍으로 이뤄진다.
   ( n % a == 0 ) 라면, ( n % b == 0 )
    
    n % a == 0 (1 < a ≤ √n) 를 만족하는 a 가 존재하는지 확인하면 된다.
   */
   var isPrime: Bool {
       // 짝수 필터링
       guard self % 2 != 0 else {
           // 2 라면 소수
           return self == 2
       }
       
       // 소수값 제거
       let roundedSqrt = Int(sqrt(Double(self)))
       
       // 완전제곱수 미리 필터
       guard self != roundedSqrt * roundedSqrt else { return false }
       
       // (제곱근이 3이상인 경우) 3부터 제곱근까지 홀수만 탐색 3, 5, 7, 9 ...
       for div in stride(from: 3, through: roundedSqrt, by: 2) {
           // 나눠지는 경우는 소수 X
           guard self % div != 0 else { return false }
       }
       
       return true
   }
}
