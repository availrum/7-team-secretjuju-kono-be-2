package org.secretjuju.kono.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameUpdateRequest {

	@NotBlank(message = "닉네임은 필수 입력 항목입니다")
	@Size(min = 2, max = 10, message = "닉네임은 2~10자 사이여야 합니다")
	@Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "닉네임은 한글, 영문, 숫자만 사용 가능합니다")
	private String nickname;
}