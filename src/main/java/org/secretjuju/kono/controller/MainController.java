package org.secretjuju.kono.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@GetMapping("/main")
	public String main(@AuthenticationPrincipal OAuth2User oauth2User, HttpSession session, Model model) {
		if (oauth2User != null) {
			Map<String, Object> attributes = oauth2User.getAttributes();
			Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

			if (properties != null) {
				model.addAttribute("nickname", properties.get("nickname"));
			}

			// 세션 정보 추가
			model.addAttribute("session", new SessionInfo(session));
		}
		return "main";
	}
}

// 세션 정보를 담을 클래스
@Getter
class SessionInfo {
	private final String id;
	private final String creationTime;

	public SessionInfo(HttpSession session) {
		this.id = session.getId();
		this.creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(session.getCreationTime()));
	}
}