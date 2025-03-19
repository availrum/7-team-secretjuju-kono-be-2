package org.secretjuju.kono.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.secretjuju.kono.entity.CoinInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCoinsResponseDto {
	private List<CoinInfoResponseDto> data;

	public static FavoriteCoinsResponseDto fromEntityList(List<CoinInfo> coinInfoList) {
		List<CoinInfoResponseDto> coinInfoResponseDtos = coinInfoList.stream().map(CoinInfoResponseDto::fromEntity)
				.collect(Collectors.toList());

		return FavoriteCoinsResponseDto.builder().data(coinInfoResponseDtos).build();
	}
}