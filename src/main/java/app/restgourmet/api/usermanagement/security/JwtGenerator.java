package app.restgourmet.api.usermanagement.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import app.restgourmet.api.utils.AppConstants;

@Component
public class JwtGenerator {
  @Autowired
  @Qualifier("accessJwtEncoder")
  private JwtEncoder encoder;

  @Autowired
  @Qualifier("refreshJwtEncoder")
  private JwtEncoder refreshEncoder;

  public String generateAccessToken(UserDetailsImpl userDetails) {
    Instant now = Instant.now();

    // if you want to add scope to the token
    // String scope = userDetails
    // .getAuthorities().stream()
    // .map(GrantedAuthority::getAuthority)
    // .collect(Collectors
    // .joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("spring-security-jwt")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(AppConstants.Security.ACCESS_TOKEN_EXPIRATION_TIME))
        .subject(userDetails.getUsername())
        // .claim("scope", scope)
        .build();

    JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

    return encoder.encode(
        JwtEncoderParameters.from(jwsHeader, claims))
        .getTokenValue();
  }

  public String generateRefreshToken(UserDetailsImpl userDetails) {
    Instant now = Instant.now();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("spring-security-jwt")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(AppConstants.Security.REFRESH_TOKEN_EXPIRATION_TIME))
        .subject(userDetails.getUsername())
        .build();

    JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

    return refreshEncoder.encode(
        JwtEncoderParameters.from(jwsHeader, claims))
        .getTokenValue();
  }
}
