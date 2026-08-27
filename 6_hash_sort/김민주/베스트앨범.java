import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        ArrayList <Integer> answer = new ArrayList<>();
        int n = genres.length;
        // 장르, 재생 총합
        HashMap <String, Integer> gen = new HashMap<>();
        // 장르 - idx, song재생 횟수
        HashMap <String, HashMap <Integer, Integer>> map = new HashMap<>();
        
        
        for (int i=0; i<n; i++){
            HashMap <Integer, Integer> music = new HashMap<>();
            if (gen.containsKey(genres[i])){
                music.put(i, plays[i]);
                gen.put(genres[i], gen.get(genres[i]) + plays[i]);
                map.get(genres[i]).put(i, plays[i]);
                // int[] plist = Arrays.copyOf(map.get(genres[i]), 3); 
                // Arrays.sort(plist, Collections.reverseOrder());
                // map.put(genres[i], {i, plist[i]});
            }else{
                music.put(i, plays[i]);
                gen.put(genres[i], plays[i]);
                map.put(genres[i], music);
            }
        }
        int mlen = gen.size();
        // 리스트로 변경 : 장르, 재생 총합이니까 장르를 리스트로, 총합 기준 정렬.
        List<String> keySet = new ArrayList<>(gen.keySet());
        // 정렬
        keySet.sort((g1, g2) -> gen.get(g2) - gen.get(g1));
        
        for (int i=0; i<mlen; i++){
            String nowG = keySet.get(i);
            HashMap <Integer, Integer> nowGenre = map.get(nowG);
            // 리스트로 변경 :  idx를 리스트로, 재생횟수 기준 정렬. 
            List <Integer> idxSong = new ArrayList<>(nowGenre.keySet());
            // 정렬
            idxSong.sort((s1, s2) -> {
                if (nowGenre.get(s2).equals(nowGenre.get(s1))){
                    return s1 - s2;
                }else{
                    return nowGenre.get(s2) - nowGenre.get(s1);
                }
            });
                    
                        
            // 가장 큰수, 다음 큰수 i 넣기
            answer.add(idxSong.get(0));
            if (idxSong.size() > 1 ){
                answer.add(idxSong.get(1));
            }
        }
        
        // int[] ans = new int[answer.size()];
        // for (int i=0; i<answer.size(); i++ ){
        //     ans[i] = answer.get(i);
        // }
        return answer.stream().mapToInt(x -> x).toArray();
        
    }
}
