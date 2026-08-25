import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        int[] answer = {};
        int n = genres.length();
        HashMap <String, int[]> map = new HashMap<>();
        
        for (int i=0; i<n; i++){
            if (map.containsKey(genres[i])){
                
                int[] plist = Arrays.copyOf(map.get(genres[i]), 3); 
                plist[2] = plays[i];

                Arrays.sort(plist, Collections.reverseOrder());
                map.put(genres[i], {plist[0], plist[1], 0});
            }else{
                map.put(genres[i], {plays[i], 0, 0});
            }
        }
        
        int mlen = map.size();
        
        for (int i=0; i<mlen; i++){
            
        }
        
        return answer;
    }
}









