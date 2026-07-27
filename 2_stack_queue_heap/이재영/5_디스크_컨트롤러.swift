//
//  5_디스크_컨트롤러.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42627
//
//  Created by jerry on 7/23/26.
//


/*
 
 (Shortest Remaining Time) 스케줄링 알고리즘 -> 소요시간을 통한 우선순위 외에는 평균 반환시간에 영향을 주지 않음
 
 1. 작업 요청 시, 작업을 대기 큐에 저장
 
 2. 작업을 하고 있지 않고, 대기 큐가 비어있지 않다면, 대기 큐에서 가장 우선순위가 높은 작업을 시작
    (우선순위 기준: 소요시간(짧을수록) > 작업의 요청 시각(빠를수록 ≒ 작을수록) > 작업의 번호(작을수록))
    => 우선순위에서 `소요시간` 외에는 반환시간 평균을 구하는 데 영향을 주지 않음
 
 3. 작업은 시작되면, 마칠 때까지 수행
 
 4. "작업 추가"와 "작업 (선택 및) 시작" 중 "작업 추가" 이벤트를 선행
 
*/

import Foundation

func solution(_ jobs: [[Int]]) -> Int {
    // 1. 작업 우선순위 큐 초기화 (최소 힙: 소요 시간 -> 요청 시각 -> ID 순으로 빠른 순)
    var priorityQueue = Heap<DiskJob>(sort: >)
    
    // 2. 요청 시각을 기준으로 내림차순 정렬된 스택 생성 (마지막 요소 O(1) 추출 목적)
    var sortedJobStack = jobs.sorted(by: { $0[0] > $1[0] })
    
    // 3. 현재 시간과 총 반환 시간 초기화
    var current = 0
    var totalReturnTime = 0
    
    // 4. 대기 큐와 작업 스택이 모두 빌 때까지 메인 루프 반복
    while !(priorityQueue.isEmpty && sortedJobStack.isEmpty) {
        
        // 4-1. 현재 시간 이하에 요청된 모든 작업을 작업 스택에서 빼내어 우선순위 큐에 삽입
        while let top = sortedJobStack.last, top[0] <= current {
            let top = sortedJobStack.removeLast()
            priorityQueue.insert(DiskJob(createdAt: top[0], duration: top[1]))
        }
        
        // 4-2. 우선순위 큐에서 가장 우선순위가 높은(가장 짧은) 작업 추출
        guard let endJob = priorityQueue.remove() else {
            // 4-2-1. 큐가 비어있다면 유휴 상태이므로, 다음 작업의 요청 시간으로 현재 시간 즉시 이동
            if let nextArrival = sortedJobStack.last {
                current = nextArrival[0]
            }
            continue
        }
        
        // 4-3. 작업 실행: 현재 시간을 작업의 소요 시간만큼 증가
        current += endJob.duration
        
        // 4-4. 반환 시간(현재 완료 시간 - 작업 요청 시각)을 총 반환 시간에 누적
        totalReturnTime += current - endJob.createdAt
    }
    
    // 5. 총 누적 반환 시간을 전체 작업 개수로 나누어 평균 반환 시간(정수) 반환
    return totalReturnTime / jobs.count
}

struct DiskJob {
    // 전체 작업 객체 생성 횟수를 추적하여 고유 ID 발급용으로 사용
    private static var indexer = 0
    
    // 작업 번호
    private let id: Int
    // 작업 요청 시각
    let createdAt: Int
    // 작업 소요 시간
    let duration: Int
    
    init(createdAt: Int, duration: Int) {
        // 현재 발급 카운트를 고유 ID로 할당하고 카운트 1 증가
        self.id = DiskJob.indexer
        self.createdAt = createdAt
        self.duration = duration
        
        DiskJob.indexer += 1
    }
}

extension DiskJob: Comparable {
    // 힙 구조에서 사용할 정렬 기준 구현 - 우선순위 기준으로 비교 로직 구현
    // 1순위 외에는 생략해도 문제에선 동일
    static func < (lhs: Self, rhs: Self) -> Bool {
        if lhs.duration != rhs.duration {
            // 1순위: 소요 시간이 짧은 작업 우선
            return lhs.duration > rhs.duration
        } else if lhs.createdAt != rhs.createdAt {
            // 2순위: 소요 시간이 같다면 요청 시각이 빠른 작업 우선
            return lhs.createdAt > rhs.createdAt
        } else {
            // 3순위: 둘 다 같다면 고유 번호가 작은(먼저 생성된) 작업 우선
            return lhs.id > rhs.id
        }
    }
}

// TODO: - 복습 필요
struct Heap<T> {
    private var elements: [T] = []
    private let sort: (T, T) -> Bool
    
    init(sort: @escaping (T, T) -> Bool) {
        self.sort = sort
    }
    
    var isEmpty: Bool { elements.isEmpty }
    var count: Int { elements.count }
    func peek() -> T? { elements.first }
    
    mutating func insert(_ element: T) {
        elements.append(element)
        siftUp(from: elements.count - 1)
    }
    
    mutating func remove() -> T? {
        guard !isEmpty else { return nil }
        
        elements.swapAt(0, elements.count - 1)
        let result = elements.removeLast()
        
        if !isEmpty {
            siftDown(from: 0)
        }
        
        return result
    }
    
    private mutating func siftUp(from index: Int) {
        var child = index
        var parent = (child - 1) >> 1
        
        while child > 0 && sort(elements[child], elements[parent]) {
            elements.swapAt(child, parent)
            child = parent
            parent = (child - 1) >> 1
        }
    }
    
    private mutating func siftDown(from index: Int) {
        var parent = index
        let half = elements.count >> 1
        
        while parent < half {
            let left = (parent << 1) + 1
            let right = left + 1
            var candidate = parent
            
            if sort(elements[left], elements[candidate]) {
                candidate = left
            }
            
            if right < elements.count && sort(elements[right], elements[candidate]) {
                candidate = right
            }
            
            if candidate == parent {
                return
            }
            
            elements.swapAt(parent, candidate)
            parent = candidate
        }
    }
}
