package com.example.ssar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChan(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests((auth) -> auth
//				.requestMatchers("/", "login", "loginProc", "/join", "/joinProc").permitAll()
//				.requestMatchers("/admin").hasRole("ADMIN")
//				.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
//				.requestMatchers("/temp/**").denyAll().anyRequest().authenticated());
		
		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/", "login", "loginProc", "/join", "/joinProc").permitAll()
				.requestMatchers("/").hasAnyRole("USER")
				.requestMatchers("/manager").hasAnyRole("MANAGER")
				.requestMatchers("/admin").hasAnyRole("ADMIN")
				.requestMatchers("/temp/**").denyAll().anyRequest().authenticated());

		http.formLogin((auth) -> auth.loginPage("/login").loginProcessingUrl("/loginProc").permitAll());

//		http.httpBasic(Customizer.withDefaults());

		http.sessionManagement((session) -> session.maximumSessions(1) // 하나의 아이디에 대한 다중 로그인 허용 개수
				.maxSessionsPreventsLogin(true) // 다중 로그인 개수를 초과하였을 경우 처리 방법 - true : 초과시 새로운 로그인 차단 - false : 초과시 기존 세션
												// 하나 삭제
		);

		http.sessionManagement(session -> session.sessionFixation().changeSessionId() // 로그인 시 동일한 세션에 대한 id 변경
		// .sessionFixation().none() // 로그인 시 세션 정보 변경 안함
		// .sessionFixation().newSession() // 로그인 시 세션 새로 생성
		);

		http.csrf((auth) -> auth.disable()); // csrf 방지

		http.logout(auth -> auth.logoutUrl("/logout").logoutSuccessUrl("/"));

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public RoleHierarchy roleHierarchy() {

		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

		hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");

		return hierarchy;
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//
//		UserDetails user1 = User.builder().username("user1").password(bCryptPasswordEncoder().encode("1234"))
//				.roles("ADMIN").build();
//
//		UserDetails user2 = User.builder().username("user2").password(bCryptPasswordEncoder().encode("1234"))
//				.roles("USER").build();
//
//		return new InMemoryUserDetailsManager(user1, user2);
//	}

}
