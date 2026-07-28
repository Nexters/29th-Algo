import java.util.*;

class Solution {
    public int solution(int[] scov, int K) {
        PriorityQueue <Integer> pq = new PriorityQueue<>();
        int answer = 0;
        for (int score: scov){
            pq.offer(score);
        }
        
        while (!pq.isEmpty()){
            if (pq.peek() >= K){
                break;
            }else if (pq.size() <= 1){
                answer = -1;
                break;
            }
            int s1 = pq.poll();
            int s2 = pq.poll();
            int newS = s1+(s2*2);
            pq.offer(newS);
            answer += 1;
        }
        
        return answer;
    }
}