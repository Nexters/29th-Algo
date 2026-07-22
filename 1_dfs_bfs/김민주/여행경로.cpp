#include <string>
#include <vector>
#include <map>
#include <iostream>
#include <algorithm>

using namespace std;

map <string, vector<string>> vc;
// 복잡하게 똑같이 map형 visited 만들지말고 처음 벡터[][] 데이터형 사용하기
int visited[10001];

int cnt = 0;
int n;
vector<string> answer;
bool isAns = false;


void dfs(string now, int cnt, vector<vector<string>> tickets){ 
    answer.push_back(now);
    if (cnt == n){  // 정답: dfs-> dfs-> ...리턴
        isAns = true;
        return;
    }
    
    for (int i=0; i<n; i++){
        if (visited[i] != 1){
            if (now == tickets[i][0]){
                visited[i] = 1;
                dfs(tickets[i][1], cnt+1, tickets);
                
                // 오답: dfs-> dfs-> ...
                // 리턴되지않고 돌아와서 아래 줄 실행 
                // -> 계속 pop back -> for문의 다른 i번째 경로 탐색
                if (!isAns){
                    visited[i] = 0;
                    answer.pop_back();
                }
            }
            
        }
        
    } 
}

vector<string> solution(vector<vector<string>> tickets) {
    n = tickets.size();
    // DFS에서 정렬하지말고 미리 정렬
    sort(tickets.begin(), tickets.end());
    dfs("ICN", 0, tickets);
    return answer;
}
