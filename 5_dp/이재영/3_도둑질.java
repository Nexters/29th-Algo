class Solution {
    public int solution(int[] money) {
        // 배열 길이가 3 미만일 경우 예외 처리 (Swift 코드의 인덱스 접근 런타임 에러 방지)
        if (money.length < 3) return 0;
        
        // money.dropLast() 대신 검사할 끝 인덱스(money.length - 1)를 전달합니다.
        int aCase = maxValue(money[0], 2, money.length - 1, money);
        int bCase = maxValue(0, 1, money.length, money);
        
        return Math.max(aCase, bCase);
    }

    private int maxValue(int startValue, int startIndex, int endIndex, int[] money) {
        int enable = startValue;
        int disable = startValue;
        
        for (int i = startIndex; i < endIndex; i++) {
            int newEnable = Math.max(disable, enable);
            int newDisable = enable + money[i];
            
            enable = newEnable;
            disable = newDisable;
        }
        
        return Math.max(enable, disable);
    }
}
