package org.secretjuju.kono.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
@NoArgsConstructor // 기본 생성자 생성
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "nickname", nullable = false, length = 50, unique = true)
	private String nickname;

	@Column(name = "profile_image_url", length = 255)
	private String profileImageUrl;

	@Column(name = "kakao_id", unique = true, columnDefinition = "BIGINT UNSIGNED")
	private Long kakaoId;

	// 양방향 관계 설정
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CoinTransaction> transactions = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Badge> badges = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CoinHolding> coinHoldings = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private CashBalance cashBalance;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CoinFavorite> coinFavorites = new ArrayList<>();

}
