import java.util.*;

class Solution {
    public int[] solution(int[] pr) {
        int n = pr.length;
        int[] answer = new int[n];
        Stack <Integer> st = new Stack<>();
        
        for (int i=0; i<n; i++){
            
            while (!st.isEmpty() && pr[st.peek()] > pr[i]){
                // stack이 차있고, 
                // 스택 맨 위 값 > 현재 값 => 현재 값이 더 작음
                // 스택에서 꺼내고 ans에 저장
                int pIdx = st.pop();
                answer[pIdx] = i - pIdx;
                
            }
            st.push(i); 
        }
        // answer 조립
        while (!st.isEmpty()){
            int nowIdx = st.pop();
            answer[nowIdx] = (n-1)-nowIdx;
        }
        
        return answer;
    }
}