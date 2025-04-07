package org.secretjuju.kono;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionChecker implements CommandLineRunner {

	private final JdbcTemplate jdbcTemplate;

	public DatabaseConnectionChecker(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			String result = jdbcTemplate.queryForObject("SELECT 'DB 연결 성공' AS message", String.class);
			System.out.println(result);
		} catch (Exception e) {
			System.err.println("DB 연결 실패: " + e.getMessage());
		}
	}
}
