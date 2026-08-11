#include <string>
#include <vector>
#include <algorithm>

using namespace std;

int set[101];

int getP(int node){
    if (set[node] == node){
        return node;
    }
    return set[node] = getP(set[node]);
}

bool cmp(vector <int> a, vector <int> b){
    return a[2] < b[2];
}

int solution(int n, vector<vector<int>> costs) {
    int answer = 0;
    int cost =0;
        
    for(int i=0; i<n; i++){
        set[i] = i;
    }
    
    sort(costs.begin(), costs.end(), cmp);
    
    for (int i=0; i<costs.size(); i++){
        int start = getP(costs[i][0]);
        int end = getP(costs[i][1]);
        cost = costs[i][2];
        if (start!=end){
            // 싸이클이 만들어지지않은 경우
            answer += cost;
            // 대소 비교 추가하면 좋음
            set[start] = end;
        }
    }
    
    return answer;
}