package org.secretjuju.kono.service;

import org.secretjuju.kono.dto.CoinRequestDto;
import org.secretjuju.kono.dto.CoinResponseDto;
import org.secretjuju.kono.dto.UserRequestDto;
import org.secretjuju.kono.dto.UserResponseDto;
import org.secretjuju.kono.entity.CoinInfo;
import org.secretjuju.kono.repository.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
