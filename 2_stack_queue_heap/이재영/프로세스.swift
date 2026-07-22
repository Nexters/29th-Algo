/*
 운영체제가 아래의 원칙에 따라 프로세스를 관리했을 때,
 특정 프로세스가 몇 번째로 실행되는지
 
 1. 실행 대기 큐에서 우선순위가 가장 높고 (가까운?) 프로세스를 선택 (enqueue / dequeue 를 반복)
 2. 선택된 프로세스는 완료할 때까지 중단되지 않음
 
 입력값 - 프로세스 우선순위 리스트: [Int], 실행 순서를 확인할 프로세스의 인덱스: Int
 
 우선순위는 1~9의 정수 -> 1회 카운트를 통해 배열에서 관리하면, 조기 중단 가능
 우선순위는 9칸 짜리 [Int] 를 통해 카운팅하고, 프로세스 종료 시 -1
*/

func solution(_ priorities: [Int], _ location: Int) -> Int {
    let tracker = PriorityTracker()
    let queue = Queue()
    
    // priorities 배열을 순회하며 초기 데이터 세팅
    for i in 0..<priorities.count {
        tracker.add(priorities[i])
        queue.enqueue(i)
    }
    var maxPriority = tracker.maxPriority
    // dequeue() 결과가 nil이 아닐 때까지 반복 수행
    while let element = queue.dequeue() {
        // 현재 요소의 우선순위가 남은 프로세스 중 가장 높은지 확인
        if priorities[element] == maxPriority {
            tracker.remove(maxPriority)
            maxPriority = tracker.maxPriority
            
            // 찾고자 하는 인덱스(location)라면 루프 탈출
            if element == location { break }
        } else {
            // 우선순위가 낮다면 실행하지 않고 큐의 맨 뒤에 다시 삽입
            queue.enqueue(element)
        }
    }
    
    // (전체 프로세스 개수) - (아직 실행되지 않고 남아있는 프로세스 개수) = 실행된 프로세스 순서
    return priorities.count - tracker.remainCount
}

final class Queue {
    // 내부적으로 정수 배열을 사용해 데이터를 저장
    private var elements = [Int]()
    // 커서(인덱스) 역할을 하는 변수로, 배열 앞부분을 지우지 않고 가리키기만 함
    private var currentIndex: Int = 0
    
    private var isEmpty: Bool { elements.count <= currentIndex }
    
    func enqueue(_ element: Int) {
        elements.append(element)
    }
    
    func dequeue() -> Int? {
        guard !isEmpty else { return nil }
        defer { currentIndex += 1 }
        return elements[currentIndex]
    }
}

// 우선순위 빈도 추적
final class PriorityTracker {
    // 인덱스를 우선순위 값(1~9)으로 직관적으로 사용하기 위해 크기를 10으로 설정
    private var counts = [Int](repeating: 0, count: 10)
    
    // 최고 우선순위를 계산하여 반환
    var maxPriority: Int {
        // 9부터 1까지 역순으로 반복
        for priority in stride(from: 9, through: 1, by: -1) {
            if counts[priority] > 0 { return priority }
        }
        return 0
    }
    
    var remainCount: Int { counts.reduce(0, +) }
    
    func add(_ priority: Int) {
        counts[priority] += 1
    }
    
    func remove(_ priority: Int) {
        counts[priority] -= 1
    }
}
