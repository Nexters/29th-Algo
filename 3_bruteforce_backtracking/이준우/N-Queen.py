def solution(n):
    board = [0] * n

    def is_check(row, col):
        for i in range(row):
            if board[i] == col or (abs(i - row) == abs(board[i] - col)):
                return False

        return True

    def bf(row):
        if row == n:
            return 1

        result = 0
        for col in range(n):
            if is_check(row, col):
                board[row] = col
                result += bf(row + 1)

        return result

    return bf(0)
