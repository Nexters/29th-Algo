import java.util.*;

class Solution {
    int na, nb;
    int mn;
    List <Integer>[] arr;
    int cnt;
    int dfs(int node, boolean[] visited){
        visited[node] = true;
        cnt += 1;
        
        for (int newone: arr[node]){
            if (!visited[newone]){
                dfs(newone, visited);
                
            }
        }
        return cnt;
        
    }
    
    public int solution(int n, int[][] wires) {
        int ln = wires.length;
        int answer = ln;
        int cntDfs = ln;
        arr = new ArrayList[n+1];
        mn = n;
        
        for (int i=1; i<n+1; i++){
            arr[i] = new ArrayList<>();
        }
        for (int i=0; i<ln; i++){
            arr[wires[i][0]].add(wires[i][1]);
            arr[wires[i][1]].add(wires[i][0]);
        }
        
        for (int i=0; i<ln; i++){
            int nowi = wires[i][0];
            int nowj = wires[i][1];
            arr[nowi].remove(Integer.valueOf(nowj));
            arr[nowj].remove(Integer.valueOf(nowi));
            cnt = 0;
            boolean[] visited = new boolean [n+1];

            cntDfs = dfs(wires[0][0], visited);
            int a = Math.min(n-cntDfs, cntDfs);
            int b = Math.max(n-cntDfs, cntDfs);
            answer = Math.min(b-a, answer);
            
            arr[nowi].add(nowj);
            arr[nowj].add(nowi);
            
        }
        return answer;
    }
}