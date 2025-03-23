package app.restgourmet.api.usermanagement.security;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import app.restgourmet.api.utils.CommonUtils;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  @Qualifier("accessJwtDecoder")
  private JwtDecoder accessTokenDecoder;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Value("${restgourmet.api.jwt-cookie-name}")
  private String jwtCookieName;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    try {
      Jwt jwt = getJwtFromCookie(request);

      if (jwt == null) {
        jwt = getJwtFromBearerToken(request);
      }

      // TODO: analyze why logging in after accessing 
      // http://localhost:5173/api/users 
      // is hitting db 9 times

      if (jwt != null) {
        UUID id = CommonUtils.parseUUID(jwt.getSubject()); // TODO: check if refresh token can be used to access resources
        UserDetailsImpl userDetails = userDetailsService.loadUserById(id);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.debug(
            "Set SecurityContextHolder to JwtAuthenticationToken [Principal={}, Credentials=[PROTECTED], Authenticated={}, Details={}]",
            authentication.getPrincipal(),
            authentication.isAuthenticated(),
            authentication.getDetails());
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  private Jwt getJwtFromCookie(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
    if (cookie != null) {
      return accessTokenDecoder.decode(cookie.getValue());
    }
    return null;
  }

  private Jwt getJwtFromBearerToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.toLowerCase().startsWith("bearer ")) {
      String jwt = bearerToken.substring(7);
      return accessTokenDecoder.decode(jwt);
    }
    return null;
  }
}