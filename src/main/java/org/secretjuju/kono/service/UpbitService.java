package org.secretjuju.kono.service;

import org.secretjuju.kono.dto.response.TickerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UpbitService {

	private final RestTemplate restTemplate;

	@Value("${upbit.api.url}")
	private String upbitApiUrl;

	public UpbitService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public TickerResponse getTicker(String market) {
		String url = upbitApiUrl + "/ticker?markets=" + market;
		ResponseEntity<TickerResponse> response = restTemplate.getForEntity(url, TickerResponse.class);
		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
			return response.getBody();
		} else {
			throw new RuntimeException("업비트 API 호출 실패");
		}
	}
}