import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        int n = phone_book.length;
        // 정렬
        Arrays.sort(phone_book);
        
        for (int i=0; i<n-1; i++){
            if (phone_book[i+1].startsWith(phone_book[i])){
                return false;
            }
        }
        
        // 해시

        Map<String, Integer> map = new HashMap<>();
        Arrays.sort(phone_book);
        
        for (int i=0; i<n; i++){
            map.put(phone_book[i], i);
        }

        for (int i = 0; i < phone_book.length; i++) {
            // 전화번호의 길이만큼 순회
            for (int j = 0; j < phone_book[i].length(); j++) {
                // 해당 전화번호의 접두어가 key로 가진 경우
                // if (map.containsKey(phone_book[i].substring(0,j))) {
                if (map.containsKey(phone_book[i].substring(0,j))) {
                    return false;
                }
            }
        }


        return true;
    }
}