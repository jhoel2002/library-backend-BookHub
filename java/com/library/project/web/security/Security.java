package com.library.project.web.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.library.project.web.jwt.JwtTokenFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class Security {
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.cors(withDefaults());
		http.sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.exceptionHandling(ext -> ext.authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}).accessDeniedHandler((request, response, ex)->{response.addHeader("access_denied_reason", "not_authorized");response.sendError(403, "Access Denied");}).authenticationEntryPoint((request, response, ex)->{response.addHeader("access_denied_reason", "authentication_required");response.sendError(HttpServletResponse.SC_UNAUTHORIZED);}));
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(auth -> auth
					.requestMatchers("/v1/usuarios/**").permitAll()
					.requestMatchers("/v1/autores/**").permitAll()
					.requestMatchers("/v1/carreras/**").permitAll()
					.requestMatchers("/v1/estudiantes/**").permitAll()
					.requestMatchers("/v1/libros/**").permitAll()
					.requestMatchers("/v1/pasillos/**").permitAll()
					.requestMatchers("/v1/prestamos/**").permitAll()
					.requestMatchers("/v1/generos/**").permitAll()
					.requestMatchers("/v1/auth/**").permitAll()
					.requestMatchers("/v1/auth/checkToken").authenticated()
					.anyRequest().authenticated());
		return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	  CorsConfiguration configuration = new CorsConfiguration();
	  configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200","https://library-app-frontend-pi.vercel.app"));
	  configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
	  configuration.setAllowCredentials(true);
	  configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
	  configuration.setExposedHeaders(Arrays.asList("X-Get-Header"));
	  configuration.setMaxAge(3600L);
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  source.registerCorsConfiguration("/**", configuration);
	  return source;
	}
}
