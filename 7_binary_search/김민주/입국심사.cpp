#include <string>
#include <vector>
#include <algorithm>
#include <iostream>
using namespace std;

long long solution(int n, vector<int> times) {
    long long answer = 0;
    long long m = times.size();
    
    sort(times.begin(), times.end());
    long long start =1;
    long long end = (long long)n*times[m-1];
    long long mid = (start+end)/2;
    long long visitor = 0;
    
    while(start <= end){
        visitor = 0;
        mid = (start+end)/2;
        
        for (int i=0; i<m; i++){
            visitor += (mid / times[i]);
        }
        
        if (visitor >= n){
            answer = mid;
            end = mid-1;
            // break;
        }else{
            start = mid+1;
        }
    }
    
    
    return answer;
}