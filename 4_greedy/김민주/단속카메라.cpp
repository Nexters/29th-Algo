#include <string>
#include <vector>
#include <cmath>
#include <iostream>
#include <algorithm>

using namespace std;

int solution(vector<vector<int>> routes) {
    int n = routes.size();
    int answer = 0;
    
    vector <pair <int, int>> rt;
    for (int i=0; i<n; i++){
        // rt : {나감, 들어옴}
        rt.push_back({routes[i][1], routes[i][0]});
    }
    // 2번째 값(나간시점)으로 정렬
    sort(rt.begin(), rt.end());
    
    int idx =0;
    int cctv = rt[idx].second -1;
    while (idx < n){
        if (cctv < rt[idx].second){
            // 새 cctv 
            cctv = rt[idx].first;
            answer += 1;
        }
        idx +=1;
    }
    
    
    return answer;
}