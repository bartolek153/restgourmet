package app.restgourmet.api.usermanagement.security;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import app.restgourmet.api.utils.AppConstants;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthEntryPoint authEntryPoint;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(
                "/api/auth/**", "/error",
                "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
            .permitAll()
            .anyRequest()
            .authenticated())
        .sessionManagement((sessionManagement) -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // disable
        .exceptionHandling((exc) -> exc.authenticationEntryPoint(authEntryPoint));

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  PasswordEncoder pwdEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Qualifier("accessJwtDecoder")
  JwtDecoder jwtDecoder() {
    SecretKey originalKey = new SecretKeySpec(AppConstants.Security.ACCESS_JWT_SECRET.getBytes(), "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(originalKey).build();
  }

  @Bean
  @Qualifier("refreshJwtDecoder")
  JwtDecoder refreshJwtDecoder() {
    SecretKey originalKey = new SecretKeySpec(AppConstants.Security.REFRESH_JWT_SECRET.getBytes(), "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(originalKey).build();
  }

  @Bean
  @Qualifier("accessJwtEncoder")
  JwtEncoder jwtEncoder() throws JOSEException {
    SecretKey key = new SecretKeySpec(AppConstants.Security.ACCESS_JWT_SECRET.getBytes(), "HmacSHA256");
    JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<SecurityContext>(key);
    return new NimbusJwtEncoder(immutableSecret);
  }

  @Bean
  @Qualifier("refreshJwtEncoder")
  JwtEncoder refreshJwtEncoder() throws JOSEException {
    SecretKey key = new SecretKeySpec(AppConstants.Security.REFRESH_JWT_SECRET.getBytes(), "HmacSHA256");
    JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<SecurityContext>(key);
    return new NimbusJwtEncoder(immutableSecret);
  }
}
