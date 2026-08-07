def solution(number, k):
    stack = []

    for num in number:
        while k > 0 and stack and stack[-1] < num:
            stack.pop()
            k -= 1
        stack.append(num)
        
    # or return "".join(stack[:len(stack) - k])
    while k > 0:
        stack.pop()
        k -= 1
        
    return "".join(stack)