#include <string>
#include <vector>
#include <iostream>
#include <algorithm>
#include <queue>

using namespace std;

vector<vector <int>> v;
vector <bool> visited;
vector <int> tm;



// void bfs(int node, int cnt, int n){
    
// }

int solution(int n, vector<vector<int>> edge) {
    int answer = 0;
    int ln = edge.size();
    
    v.resize(n+1);
    visited.resize(n+1);
    tm.resize(n+1, 0);
    
    for (int i=0; i<ln; i++){
        int a = edge[i][0];
        int b = edge[i][1];
        v[a].push_back(b);
        v[b].push_back(a);
    }
    
    queue <int> que;
    que.push(1);
    visited[1] = true;
    int cnt =0;
    
    while(!que.empty()){
        int node = que.front();
        que.pop();
        cnt += 1;
        for (int one: v[node]){
            
            if (visited[one] == false){
                visited[one] = true;
                tm[one] = tm[node]+1;
                que.push(one);
            }
        } 
        
    }
    int maxOne = *max_element(tm.begin()+2, tm.end());
    
    for (int i=2; i<=n; i++){
        if (maxOne == tm[i]){
            answer += 1;
        }
    }
    
    
    return answer;
}
