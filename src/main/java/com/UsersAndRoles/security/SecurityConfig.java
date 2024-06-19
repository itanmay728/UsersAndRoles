package com.UsersAndRoles.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private static final String[] WHITELIST = {

			"/",

			"/login",
			
			"/registration",
			
			"/saveUser",

			"/css/**", "/images/**", "/js/**", "/vendors/**","/style.css","/index.js","/hero-img.png"

	};
	
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@SuppressWarnings({ "removal" })
	@Bean

	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http

				.authorizeHttpRequests((authz) -> authz

						.requestMatchers(WHITELIST)

						.permitAll().requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()

				)
				.formLogin(form -> {
					try {
						form.loginPage("/").loginProcessingUrl("/").usernameParameter("email")
								.passwordParameter("password").defaultSuccessUrl("/dashboard", true).failureUrl("/login?error")
								.permitAll().and()
								.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
										.logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true))
								.rememberMe(me -> me.rememberMeParameter("remember-me")).httpBasic(withDefaults());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
		return http.build();

	}
}
