def solution(N, number):
    DP = [set() for _ in range(9)]
    answer = -1
    for i in range(1, 9):
        nums = {int(str(N) * i)}
        for j in range(1, i):
            for x in DP[j]:
                for y in DP[i-j] :
                    nums.add(x + y)
                    nums.add(x - y)
                    nums.add(x * y)
                    if y != 0:
                        nums.add(x // y)
        if number in nums:
            answer = i
            break
        DP.append(nums)
    return answer
                    
                
            
            
        