package in.OAndM.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] WHITELIST = { "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**",
			"/swagger-resources/**", "/swagger-resources/", "/api/database-endpoint/**", "/rti/**", "/rti/app/**",
			"/rti/prfmG/**", "/api/hrms/**", "/OandMWorks/**","/oandm/rti/app/**","/oandm/rti/prfmG/**" };

	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter(); // Converts the JWT
																									// (JSON Web Token)
																									// into Spring
																									// Security's
																									// Authentication
																									// object.
		// jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new
		// KeycloakRoleConverter());

		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(WHITELIST).permitAll()

				.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2ResourceServerCustomizer -> oauth2ResourceServerCustomizer
						.jwt(jwtCustomizer -> jwtCustomizer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
		http.csrf((csrf) -> csrf.disable());
		//http.cors(Customizer.withDefaults());

		return http.build();
	}

}
