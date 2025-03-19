package org.secretjuju.kono.controller;

import org.secretjuju.kono.dto.request.PresignedUrlRequestDto;
import org.secretjuju.kono.dto.response.PresignedUrlResponseDto;
import org.secretjuju.kono.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
public class S3Controller {
	private final S3Service s3Service;

	@PostConstruct
	public void init() {
		log.info("S3Controller initialized");
	}

	@PostMapping("/presigned-url")
	public ResponseEntity<PresignedUrlResponseDto> getPresignedUrl(@RequestBody PresignedUrlRequestDto request) {
		log.info("Requesting presigned URL for file: {}", request.getFileName());

		PresignedUrlResponseDto response = s3Service.generatePresignedUrl(request.getFileName(),
				request.getContentType());

		return ResponseEntity.ok(response);
	}
}