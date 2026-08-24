class Solution {
    fun solution(genres: Array<String>, plays: IntArray): IntArray {
        val genreToSongs = genres.indices.groupBy { genres[it] }

        val sortedGenres = genreToSongs.keys.sortedByDescending { genre ->
            genreToSongs.getValue(genre).sumOf { plays[it] }
        }

        val answer = mutableListOf<Int>()
        for (genre in sortedGenres) {
            val topSongs = genreToSongs.getValue(genre).sortedWith(
                compareByDescending<Int> { plays[it] }.thenBy { it }
            ).take(2)
            answer.addAll(topSongs)
        }

        return answer.toIntArray()
    }
}
/**
 * 장르별로 가장 많이 재생된 노래를 두 개씩 모아서 베스트 앨범을 출시
 * 
 * 1. 속한 노래가 많이 재생된 장르를 먼저 수록
 * 2. 장르 내에서 많이 재생된 노래를 먼저 수록 
 * 3. 장르 내에서 재생횟수가 같은 노래 중에서 고유 번호가 낮은 노래를 먼저 수록
 * 
 * 1. 각 장르별로 합계를 계산 
 * 2. 각 장르별로 PQ를 만듦
 */