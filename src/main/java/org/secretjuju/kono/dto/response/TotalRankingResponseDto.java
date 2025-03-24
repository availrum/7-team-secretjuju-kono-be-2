package org.secretjuju.kono.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalRankingResponseDto {
	private String nickname;
	private String profileImageUrl;
	private List<String> badgeImageUrl;
	private Long totalAssets;
	private int rank;
}