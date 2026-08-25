//
//  3_베스트앨범.swift
//  https://school.programmers.co.kr/learn/courses/30/lessons/42579
//
//  Created by jerry on 8/25/26.
//

import Foundation

func solution(_ genres: [String], _ plays: [Int]) -> [Int] {
    var genrePlays = [String: Int]()
    var genreSongs = [String: [(id: Int, play: Int)]]()
    
    for (id, (genre, play)) in zip(genres, plays).enumerated() {
        genrePlays[genre, default: 0] += play
        genreSongs[genre, default: []].append((id: id, play: play))
    }
    
    let sortedGenres = genrePlays.keys.sorted { genrePlays[$0]! > genrePlays[$1]! }
    
    return sortedGenres.flatMap { genre -> [Int] in
        let songs = genreSongs[genre]!
        let topSongs = songs.sorted {
            if $0.play == $1.play {
                return $0.id < $1.id
            } else {
                return $0.play > $1.play
            }
        }.prefix(2)
        
        return topSongs.map { $0.id }
    }
}
