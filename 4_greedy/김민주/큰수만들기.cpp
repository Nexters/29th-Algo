#include <string>
#include <vector>
#include <iostream>
#include <stdio.h>

using namespace std;

string solution(string number, int k) {
    string answer = "";
    int n = number.length();
    int tlen = n-k;
    
    int maxx =0;
    int maxIdx =0;
    int cnt =0;
    for (int i=k; i>=0; i--){
        
        if (number[i]-'0' >= maxx){
            maxx = number[i]-'0';
            maxIdx = i;
        }
    }
    answer += (maxx+'0');
    int start = maxIdx+1;
    maxx = 0;
    cnt += 1;
    
    while(cnt < n-k){
        for (int i=start; i<=k+cnt; i++){
            if (number[i]-'0' > maxx){
                maxx = number[i]-'0';
                maxIdx = i;
            }
        }
        cnt += 1;
        answer += (maxx+'0');
        start = maxIdx+1;
        maxx = 0;
    }
    
    
    return answer;
}