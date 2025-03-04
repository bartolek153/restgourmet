package app.restgourmet.api.controllers;

import app.restgourmet.api.usermanagement.dto.auth.LoginRequestDto;
import app.restgourmet.api.usermanagement.dto.auth.RegisterRequestDto;
import app.restgourmet.api.usermanagement.service.spec.IAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User authentication management")
public class AuthenticationController {

  private final IAuthenticationService authenticationService;

  public AuthenticationController(IAuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  @Operation(summary = "Creates an user")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto request) {
    return authenticationService.register(request);
  }

  @PostMapping("/login")
  @Operation(summary = "Authenticates an user and returns a new token")
  public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
    return authenticationService.login(request);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Generates a new access token using the refresh token from cookies")
  public ResponseEntity<String> newToken(@CookieValue("${restgourmet.api.jwt-refresh-cookie-name}") String refToken) {
    return authenticationService.token(refToken);
  }
}
