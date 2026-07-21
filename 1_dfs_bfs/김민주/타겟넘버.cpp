#include <string>
#include <vector>

using namespace std;

// 전역 변수
int ans = 0;
int target;

void dfs(int i, int sum, vector<int> numbers);

int solution(vector<int> numbers, int t) {
    target = t;

    dfs(0, 0, numbers);

    return ans;
}

void dfs(int i, int sum, vector<int> numbers){
    if (i == numbers.size()){
        if (sum == target){
            ans += 1;
        }
        return;
    }
    dfs(i + 1, sum + numbers[i], numbers);
    dfs(i + 1, sum - numbers[i], numbers);
    
}