package org.secretjuju.kono.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CoinPriceService {

	// 티커를 키로, 현재가를 값으로 저장하는 ConcurrentHashMap
	private final ConcurrentHashMap<String, Double> coinPriceMap = new ConcurrentHashMap<>();

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	@Getter
	@NoArgsConstructor
	public static class CoinTickerDto {
		private String market;

		@JsonProperty("trade_price")
		private Double tradePrice;

	}

	public CoinPriceService(RestTemplate restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	// 5분마다 모든 코인 가격 업데이트
	@Scheduled(fixedRate = 300000) // 5분마다 실행
	public void updateAllCoinPrices() {
		try {
			// API에서 모든 코인 정보 가져오기
			String response = restTemplate.getForObject("https://api.upbit.com/v1/ticker/all", String.class);

			// ObjectMapper를 사용하여 JSON 배열 파싱
			List<CoinTickerDto> tickers = objectMapper.readValue(response, new TypeReference<List<CoinTickerDto>>() {
			});

			// 맵 업데이트
			for (CoinTickerDto ticker : tickers) {
				coinPriceMap.put(ticker.getMarket(), ticker.getTradePrice());
			}

			log.info("코인 가격 업데이트 완료: {} 개 코인", coinPriceMap.size());
		} catch (Exception e) {
			log.error("코인 가격 업데이트 중 오류 발생", e);
		}
	}

	// 특정 코인의 현재가 조회
	public Double getPrice(String ticker) {
		return coinPriceMap.getOrDefault(ticker, 0.0);
	}

	// 모든 코인의 현재가 조회
	public Map<String, Double> getAllPrices() {
		return Map.copyOf(coinPriceMap); // 불변 맵으로 복사하여 반환
	}

	// 티커 접두사 변환 ("KRW-BTC" -> "BTC")
	public Double getPriceBySymbol(String symbol) {
		return getPrice("KRW-" + symbol);
	}

	// "BTC"와 같은 심볼만으로 구성된 맵 반환
	public Map<String, Double> getAllPricesWithSimpleSymbols() {
		return coinPriceMap.entrySet().stream().filter(entry -> entry.getKey().startsWith("KRW-"))
				.collect(Collectors.toMap(entry -> entry.getKey().substring(4), // "KRW-BTC" -> "BTC"
						Map.Entry::getValue));
	}

	// 초기 데이터 로드
	public void initializePriceData() {
		updateAllCoinPrices();
	}

}
