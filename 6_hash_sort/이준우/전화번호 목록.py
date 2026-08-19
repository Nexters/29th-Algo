def solution(phone_book):
    # 접두사를 확인하는 것이기 때문에 길이가 작은 값부터 탐색
    phone_book.sort(key=lambda x: len(x))
    tmp = set()

    n = len(phone_book[0]) # 제일 작은 문자
    for number in phone_book:
        # phone-book에 접두사가 있는지 확인하는 과정
        for i in range(n, len(number)):
            if number[0:i] in tmp:
                return False
        tmp.add(number)

    return True

"""
컨셉 : 전화번호를 앞에서부터 slice 하면서 tmp에 값이 있는지 확인 <- 접두사 확인
최악의 경우 : 1_000_000(phone_book 길이) * 20(전화번호 길이)
"""
