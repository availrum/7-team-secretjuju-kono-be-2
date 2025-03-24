package org.secretjuju.kono.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PresignedUrlRequestDto {
	private String fileName;
	private String contentType;
}