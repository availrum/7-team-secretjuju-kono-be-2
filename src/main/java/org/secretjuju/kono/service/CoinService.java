package org.secretjuju.kono.service;

import java.util.Optional;

import org.secretjuju.kono.dto.request.CoinRequestDto;
import org.secretjuju.kono.dto.response.CoinResponseDto;
import org.secretjuju.kono.entity.CoinInfo;
import org.secretjuju.kono.repository.CoinRepository;
import org.springframework.stereotype.Service;
@Service
public class CoinService {

	private final CoinRepository coinRepository;

	public CoinService(CoinRepository coinRepository) {
		this.coinRepository = coinRepository;
	}

	public CoinResponseDto getCoinByName(CoinRequestDto coinRequestDto) {
		Optional<CoinInfo> coinInfo = coinRepository.findByTicker(coinRequestDto.getTicker());
		CoinResponseDto coinResponseDto = new CoinResponseDto(coinInfo.orElse(null));
		return coinResponseDto;
	}

}
