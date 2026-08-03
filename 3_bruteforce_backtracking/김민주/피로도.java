
class Solution {
    int n;
    int mx =0;
    boolean[] visited;
    public int solution(int k, int[][] dungeons) {
        // sort 할지 말지 생각해보기
        n = dungeons.length;
        visited = new boolean[n];
        dfs(0, k, dungeons);
        
        return mx;
    }
    
    void dfs(int ans, int hp, int[][] dg){
        mx = Math.max(ans, mx);
        for (int i=0; i<n; i++){
            if (visited[i]){ continue; }
            
            if (hp >= dg[i][0]){
                visited[i] = true;
                dfs(ans+1, hp- dg[i][1], dg);
                
                visited[i] = false;
            }
            
            
        }
        return;
    }
}