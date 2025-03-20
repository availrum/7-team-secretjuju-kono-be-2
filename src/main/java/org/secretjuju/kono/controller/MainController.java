package org.secretjuju.kono.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.secretjuju.kono.dto.response.UserResponseDto;
import org.secretjuju.kono.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
// 메인페이지 테스트를 위한 임시 컨트롤러
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	// UserService 주입
	private final UserService userService;

	@GetMapping("/main")
	public String main(@AuthenticationPrincipal OAuth2User oauth2User, HttpSession session, Model model) {
		if (oauth2User != null) {
			// 카카오 ID 추출
			Long kakaoId = Long.valueOf(oauth2User.getAttribute("id").toString());
			log.info("User with kakaoId {} accessed main page", kakaoId);

			// DB에서 사용자 정보 조회
			UserResponseDto userInfo = userService.getUserInfo(kakaoId);

			// 사용자 닉네임을 모델에 추가
			model.addAttribute("nickname", userInfo.getNickname());
			model.addAttribute("profileImage", userInfo.getProfile());

			// 세션 정보 추가
			model.addAttribute("session", new SessionInfo(session));
		}
		return "main";
	}
}

// 세션 정보를 담을 클래스 (기존 코드 유지)
@Getter
class SessionInfo {
	private final String id;
	private final String creationTime;

	public SessionInfo(HttpSession session) {
		this.id = session.getId();
		this.creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(session.getCreationTime()));
	}
}
