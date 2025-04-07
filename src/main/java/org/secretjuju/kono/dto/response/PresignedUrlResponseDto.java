package org.secretjuju.kono.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresignedUrlResponseDto {
	private String presignedUrl;
	private String objectKey;
	private String uploadedFileUrl;
}