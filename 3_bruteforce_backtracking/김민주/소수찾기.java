import java.util.*;

class Solution {
    Set<Integer> numberSet= new HashSet<>();
    
    public int solution(String numbers) {
        boolean[] visited = new boolean[numbers.length()];
        
        dfs(numbers, "", visited);
        
        int answer = 0;
        for (int num :numberSet) {
            if (isPrime(num)) {
                answer++;
            }
        }
        
        return answer; 
    }
    
    // 백트래킹
    void dfs(String numbers, String current, boolean[] visited) {
        if (!current.equals("")) {
            numberSet.add(Integer.parseInt(current));
        }
        
        for (int i = 0; i < numbers.length(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(numbers, current + numbers.charAt(i), visited);
                visited[i] = false; 
            }
        }
    }
    
    // 소수 판별 함수
    boolean isPrime(int num) {
        if (num < 2) return false;
        
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        
        return true;
    }
}