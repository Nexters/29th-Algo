#include <string>
#include <vector>
#include <iostream>

using namespace std;

vector <vector<int>> vc;
vector <int> visited;

void dfs(int n, vector<vector<int>> computers, int i);

int solution(int n, vector<vector<int>> computers) {
    int answer = 0;
    vc.resize(n);
    visited.resize(n);
    
    for (int i=0; i<n; i++){
        for (int j=0; j<n; j++){
            if (computers[i][j]== 1){
                vc[i].push_back(j);
                vc[j].push_back(i);
            }
            
        }
    }
    
    for (int i=0; i<n; i++){
        if (visited[i] == 0){   // 방문 안했으면 새 네트워크
            answer +=1;
            dfs(n, computers, i);
        }
    }
        
    return answer;
}

void dfs(int n, vector<vector<int>> computers, int i){
    visited[i] = 1;
    for (int node: vc[i]){
        if (visited[node] == 0){ 
            dfs(n, computers, node);
        }
    }
    
}

