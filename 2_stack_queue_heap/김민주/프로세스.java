import java.util.*;

class Solution {
    public int solution(int[] priorities, int location) {
        int answer = 0;
        Queue <Integer> q = new LinkedList<>();
        Queue <Integer> qnum = new LinkedList<>();
        PriorityQueue <Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        int ln = priorities.length;
        boolean flag = false;
        
        for (int i=0; i<ln; i++){
            q.offer(priorities[i]);
            qnum.offer(i);
            pq.offer(priorities[i]);
        }
        
        
        while (!flag){
            int now = q.poll();
            int nownum = qnum.poll();
            int biggest = pq.peek();
            
            if (now == biggest){
                answer +=1;
                pq.poll();
                // return
                if (location == nownum){
                    flag = true;
                    break;
                }
            }else {
                q.offer(now);
                qnum.offer(nownum);
            }
            
        }
    
        return answer;
    }
}