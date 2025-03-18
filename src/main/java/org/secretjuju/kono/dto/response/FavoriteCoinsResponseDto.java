package org.secretjuju.kono.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.secretjuju.kono.entity.CoinInfo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCoinsResponseDto {
    private List<CoinInfoResponseDto> data;
    
    public static FavoriteCoinsResponseDto fromEntityList(List<CoinInfo> coinInfoList) {
        List<CoinInfoResponseDto> coinInfoResponseDtos = coinInfoList.stream()
                .map(CoinInfoResponseDto::fromEntity)
                .collect(Collectors.toList());
        
        return FavoriteCoinsResponseDto.builder()
                .data(coinInfoResponseDtos)
                .build();
    }
} 