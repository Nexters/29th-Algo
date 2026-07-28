import java.util.*;

class Solution {
    public int solution(int[] order) {
        Stack <Integer> st = new Stack <>();     // 보조 컨테이너
        int answer = 0;
        int truckIdx =0;     // 트럭에 실어야하는 박스 truckIdx
    
        for (int now=1; now<=order.length; now++){ // 컨테이너 벨트에서 오는 1~n 배열
            st.push(now);

            while (!st.isEmpty() && order[truckIdx] == st.peek()){
                st.pop();
                answer += 1;
                truckIdx += 1;
            }
        }
        return answer;
    }
}
