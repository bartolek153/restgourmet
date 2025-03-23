package app.restgourmet.api.usermanagement.service.spec;

import org.springframework.http.ResponseEntity;

import app.restgourmet.api.usermanagement.dto.auth.LoginRequestDto;
import app.restgourmet.api.usermanagement.dto.auth.RegisterRequestDto;

public interface AuthenticationService {
  ResponseEntity<String> register(RegisterRequestDto request);

  ResponseEntity<String> login(LoginRequestDto request);

  ResponseEntity<String> newToken(String refreshRoken);

  ResponseEntity<String> logout();
}
