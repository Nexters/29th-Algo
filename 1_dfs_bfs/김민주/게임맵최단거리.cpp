#include <vector>
#include <stdio.h>
#include <iostream>
#include <queue>
using namespace std;

int mvi[] = {0,1,0,-1};
int mvj[] = {1,0,-1,0};

int solution(vector<vector<int> > maps)
{
    int answer = 0;
    int nowi = 0;
    int nowj = 0;
    int n = maps.size();
    int m = maps[0].size();
    queue <pair<int, int>> q;
    int visited[101][101] = {0,};
    q.push({0,0});
    visited[0][0] = 1;
    
    while (!q.empty()){
        nowi = q.front().first;
        nowj = q.front().second;
        q.pop();
        
        if ((nowi == n-1)&&(nowj == m-1)){
            return visited[nowi][nowj];
        }
        
        for (int k=0; k<4; k++){
            int newi = nowi + mvi[k];
            int newj = nowj + mvj[k];
            if ((newi >= 0 && newi < n)&&(newj >= 0 && newj < m)){
                if ((maps[newi][newj] == 1)&&(visited[newi][newj] == 0)){
                    q.push({newi, newj});
                    visited[newi][newj] = visited[nowi][nowj] +1;
                }
            }
        }        
    }
    return -1;
}

