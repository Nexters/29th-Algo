import java.util.*;

class Solution {
    // 우선순위 : 
        // 작업의 소요시간이 짧은 것, 
        // 작업의 요청 시각이 빠른 것, 
        // 작업의 번호가 작은 것 순으로
    PriorityQueue <int[]> pq = new PriorityQueue<>(
        (o1, o2) -> {
            if (o1[0] != o2[0]){
                return o1[0] - o2[0];
            }
            return o1[1] - o2[1];
        } 
    );
    
    public int solution(int[][] jobs) {
        int now =0;
        int answer = 0;
        int idx = 0;
        
        Arrays.sort(jobs, (o1, o2) -> 
            {
                return o1[0]-o2[0];
        });
        
        while(idx < jobs.length || !pq.isEmpty()){
            // System.out.println(idx+": "+now);            
            while (idx < jobs.length && now >= jobs[idx][0]){
                // System.out.println("["+jobs[idx][0]+", "+jobs[idx][1]+"]");
                // pq 에 넣기 (소요 시간, 요청 시간)
                pq.offer(new int[]{jobs[idx][1], jobs[idx][0]});
                idx += 1;
            }
            
            if (!pq.isEmpty()){
                int tasks[] = pq.poll();
                now += tasks[0];
                answer += (now - tasks[1]);
                // System.out.println("=> "+now);
            }else{
                now += 1;
            }
            // System.out.println("now: "+now);
        }
        int ans = answer/jobs.length;
        
        return ans;
    }
}