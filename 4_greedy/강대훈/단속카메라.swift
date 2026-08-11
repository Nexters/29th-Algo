import Foundation

func solution(_ routes: [[Int]]) -> Int {
    let sortedRoutes = routes.sorted { $0[1] < $1[1] }
    var ans = 0
    var lastLocation = -30001

    for route in sortedRoutes {
        let start = route[0]
        let end = route[1]

        // 시작점이 마지막 카메라 위치보다 뒤에 있으면 아직 못만남
        if start > lastLocation {
            ans += 1
            lastLocation = end
        }
    }

    return ans
}
