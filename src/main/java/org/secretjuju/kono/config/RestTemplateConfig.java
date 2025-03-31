package org.secretjuju.kono.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		// 타임아웃 설정
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(5000); // 연결 타임아웃 5초
		factory.setReadTimeout(5000); // 읽기 타임아웃 5초
		restTemplate.setRequestFactory(factory);

		// 에러 핸들러 설정
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				try {
					return super.hasError(response);
				} catch (Exception e) {
					log.error("API 응답 처리 오류: {}", e.getMessage());
					return true;
				}
			}
		});

		return restTemplate;
	}
	// @Bean
	// public RestTemplate restTemplate(RestTemplateBuilder builder) {
	// return builder.build();
	// }
}