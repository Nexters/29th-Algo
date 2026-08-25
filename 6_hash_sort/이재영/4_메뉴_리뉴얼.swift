import Foundation
/*
코스 만들기
코스는 각 길이 별로 가장 많은 횟수를 주문했어야 함
주문횟수는 최소 2회이며, 동일한 경우 전부 메뉴로 등록

*/
func solution(_ orders: [String], _ course: [Int]) -> [String] {
    let converted = orders.map { order in order.map { String($0) }.sorted() }
    var courses = [[String: Int]](repeating: [:], count: course.last! + 1)
    var map = [[String]](repeating: [], count: orders.count)
    var answer = [String]()

    func dfs(
        _ current: String,
        _ index: Int,
        _ menus: [String]
    ) {
        guard index < menus.count && current.count < course.last! else {
            return
        }
        
        for i in index..<menus.count {
            let newCourse = current + menus[i]
            courses[newCourse.count][newCourse, default: 0] += 1
            dfs(newCourse, i + 1, menus)
        }
    }
    
    for i in 0..<converted.count {
        dfs("", 0, converted[i])
    }

    for menuCount in course {
        let sorted = courses[menuCount].sorted { $0.value > $1.value }
        
        let max = sorted.first!.value
        guard max >= 2 else { break }
        for (course, count) in sorted {
            guard count == max else { break }
            answer.append(course)
        }
    }
    
    return answer.sorted()
}
