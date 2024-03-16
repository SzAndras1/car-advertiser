package hu.personal.caradvertiser.controller;

import hu.personal.caradvertiser.AuthenticationApi;
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
        return null;
    }
}
