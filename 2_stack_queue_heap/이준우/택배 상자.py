def solution(order):
    answer = 0
    stack = []
    n = 1
    
    for want in order:
        while (not stack or want != stack[-1]) and n <= len(order):
            stack.append(n)
            n += 1
            
        if stack and stack[-1] == want:
            answer += 1
            stack.pop()
        else:
            break
    
    return answer 