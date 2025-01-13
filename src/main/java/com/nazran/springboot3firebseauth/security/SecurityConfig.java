package com.nazran.springboot3firebseauth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] PUBLIC_MATCHER = {
			"/v3/api-docs/**",
			"/configuration/ui",
			"/swagger-ui.html",
			"/swagger-resources/**",
			"/configuration/security/**",
			"/swagger-ui/**",
			"/api/v1/auth/**",
			"/api/v1/interests/**",
			"/webjars/**"
	};


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers(PUBLIC_MATCHER).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.headers(httpSecurityHeadersConfigurer ->
						httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) //to make accessible h2 console, it works as frame
				.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
						httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new CustomAuthEntryPoint()))
				.addFilterBefore(new FirebaseAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Set the custom AuthenticationManager for Firebase Authentication
        http.authenticationManager(customAuthenticationManager());
        return http.build();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() {
        return authentication -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        };
    }
}
