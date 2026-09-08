#include <string>
#include <vector>

using namespace std;

void dp(vector<vector<int>>& triangle, int a, int b){
    if (b == 0){
        triangle[a][b] += triangle[a-1][b]; 
    }else if (b == triangle[a].size()-1){
        triangle[a][b] += triangle[a-1][b-1]; 
    }else {
        triangle[a][b] += max(triangle[a-1][b-1], triangle[a-1][b]); 
    }
    
}

int solution(vector<vector<int>> triangle) {
    int answer = 0;
    int l = triangle.size();
    for (int i=1; i<l; i++){
        for (int j=0; j<triangle[i].size(); j++){
            dp(triangle, i, j);
        }
    }
    int l2 = triangle[l-1].size();

    for (int i=0; i<l2; i++){
        answer = max(triangle[l-1][i], answer);
    }
    return answer;
}