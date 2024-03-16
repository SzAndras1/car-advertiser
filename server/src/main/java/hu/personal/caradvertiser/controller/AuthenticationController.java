package hu.personal.caradvertiser.controller;

import hu.personal.caradvertiser.AuthenticationApi;
import hu.personal.caradvertiser.model.AuthenticationResponseDto;
import hu.personal.caradvertiser.model.LoginDto;
import hu.personal.caradvertiser.model.UserDto;
import hu.personal.caradvertiser.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController implements AuthenticationApi {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<UserDto> signup(UserDto userDto) {
        return ResponseEntity.ok(authenticationService.signup(userDto));
    }

    @Override
    public ResponseEntity<AuthenticationResponseDto> login(LoginDto loginDto) {
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }
}
