package org.secretjuju.kono.config;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import org.secretjuju.kono.service.OAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final OAuth2UserService oAuth2UserService;
	private final ClientRegistrationRepository clientRegistrationRepository;

	public SecurityConfig(OAuth2UserService oAuth2UserService,
			ClientRegistrationRepository clientRegistrationRepository) {
		this.oAuth2UserService = oAuth2UserService;
		this.clientRegistrationRepository = clientRegistrationRepository;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.maximumSessions(1).expiredUrl("/login"))
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/", "/login", "/oauth2/**", "/css/**", "/images/**", "/js/**")
								.permitAll().anyRequest().authenticated())
				.oauth2Login(oauth2 -> oauth2.loginPage("/login").defaultSuccessUrl("/main", true));

		return http.build();
	}

	@Bean
	public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver() {
		DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(
				clientRegistrationRepository, "/oauth2/authorization");

		// OAuth 요청 시 `prompt=login`을 추가하여 항상 로그인 창이 뜨도록 설정
		resolver.setAuthorizationRequestCustomizer(
				customizer -> customizer.additionalParameters(params -> params.put("prompt", "login")));

		return resolver;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 프론트엔드 주소
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// 로그인 성공 후 실행되는 핸들러
	// 사용자 정보를 json형태로 응답
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return ((request, response, authentication) -> {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
			Map<String, Object> attributes = defaultOAuth2User.getAttributes();

			// 카카오 로그인 응답 확인을 위한 로그
			System.out.println("Login Success - User Attributes: " + attributes);

			// JSON 응답 생성
			String body = """
					{"success": true, "attributes": %s}
					""".formatted(attributes.toString());

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());

			PrintWriter writer = response.getWriter();
			writer.println(body);
			writer.flush();
		});
	}

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowSemicolon(true);
		return firewall;
	}
}
