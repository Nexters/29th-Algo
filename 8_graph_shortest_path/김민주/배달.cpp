#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int solution(int n, vector<vector<int>> road, int K) {
    int answer = 0;
    int INF = 1e9;
    // shortest path로 계산했을때 K이하만 배달 가능
    // 배달 가능한 마을의 개수를 구하기
    vector<vector<pair <int, int>>> graph(n+1);
    vector <int> dist(n+1, INF);
    dist[1] = 0;
    
    for (int i=0; i<road.size(); i++){
        graph[road[i][0]].push_back({road[i][1], road[i][2]});
        graph[road[i][1]].push_back({road[i][0], road[i][2]});
    }
    
    priority_queue < 
        pair<int, int>, 
        vector <pair<int, int>>, 
        greater <pair <int, int>> 
    >pq;
    
    
    // pq에서는 cost, node
    pq.push({0, 1});
    
    while (!pq.empty()){
        int cost = pq.top().first;
        int now = pq.top().second;
        pq.pop();
        
        if (cost > dist[now]){
            continue;
        }
        for (auto next : graph[now]){
            // graph에서는 node, cost
            int nextNode = next.first;
            int nextC = next.second;
            
            int newCost = cost + nextC;
            // 값 업데이트
            if (newCost < dist[nextNode]){
                dist[nextNode] = newCost;
                pq.push({newCost, nextNode});
            }
        }
    }
    for (int i=1; i<n+1; i++){
        if (dist[i] <= K){
            answer +=1;
        }
    }
    return answer;
}