
class Solution {
    public int solution(int[] money) {
        int n = money.length;
        int[] dp1 = new int[n]; // 처음 집 x
        dp1[0] = 0;
        dp1[1] = money[1];
        int[] dp2 = new int[n]; // 처음 집 o
        dp2[0] = money[0]; 
        dp2[1] = money[0];
        int answer = 0;
        
        for (int i=2; i<n; i++){
            dp1[i] = Math.max(dp1[i-1], dp1[i-2]+ money[i]);
            dp2[i] = Math.max(dp2[i-1], dp2[i-2]+ money[i]);
        }
        answer = Math.max(dp1[n-1], dp2[n-2]);
        return answer;
    }
}