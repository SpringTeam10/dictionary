package com.example.team10searchengine.shared;

import lombok.*;
import org.springframework.data.redis.core.ZSetOperations;

@Data
@NoArgsConstructor
public class RankResponseDto {
    private String rankKeyword;
    private int rankScore;
    public static RankResponseDto convertToRankResponseDto(ZSetOperations.TypedTuple typedTuple){
        RankResponseDto responseRankingDto =new RankResponseDto();
        responseRankingDto.rankKeyword=typedTuple.getValue().toString();
        responseRankingDto.rankScore=typedTuple.getScore().intValue();
        return responseRankingDto;
    }
}
