#include <string>
#include <vector>

using namespace std;

int solution(int n, vector<vector<int>> results) {
    int answer = 0;
    int ln = results.size();
    vector<vector<bool>> vc;
    vc.resize(n+1);
    // 초기화
    for (int i=0; i<n+1; i++){
        vc[i].resize(n+1, false);
    }
    
    for (int i=0; i<ln; i++){
        // 이긴 , 진
        vc[results[i][0]][results[i][1]] = true;
    }
    
    for (int i=1; i<n+1; i++){
        for (int j=1; j<n+1; j++){
            for (int k=1; k<n+1; k++){
                if (vc[j][i] == true && vc[i][k] == true){
                    vc[j][k] = true;
                }
            }
        }
    }
    
    for (int i=1; i<n+1; i++){
        int cnt =0;
        for (int j=1; j<n+1; j++){
            if (vc[j][i] == true){
                cnt +=1;
            }
            if (vc[i][j] == true){
                cnt +=1;
            }
        }
        if (cnt == n-1){
            answer +=1;
        }
    }
 
    
    return answer;
}
