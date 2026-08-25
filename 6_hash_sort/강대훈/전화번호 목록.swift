func solution(_ phoneBook: [String]) -> Bool {
    let phoneSet = Set(phoneBook)

    for phone in phoneBook {
        let arr = Array(phone)

        for i in 1..<arr.count {
            let n = String(arr[0..<i])
            if phoneSet.contains(n) {
                return false
            }
        }
    }

    return true
}
