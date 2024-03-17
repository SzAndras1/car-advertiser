package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.mapper.UserMapper;
import hu.personal.caradvertiser.model.AuthenticationResponseDto;
import hu.personal.caradvertiser.model.LoginDto;
import hu.personal.caradvertiser.model.UserDto;
import hu.personal.caradvertiser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public UserDto signup(UserDto userDto) {
        userDto.setUsername(userDto.getUsername().toLowerCase());
        if (!userDto.getEmail().matches("^(.+)@(\\S+)$")) {
            throw new IllegalArgumentException("Wrong email format.");
        }
        userRepository.findByUsername(userDto.getUsername())
                .ifPresent(e -> {
                    throw new EntityExistsException();
                });
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    public AuthenticationResponseDto login(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));
        User searchForUser = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(EntityNotFoundException::new);
        String accessToken = jwtService.generateToken(searchForUser, "access");
        String refreshToken = jwtService.generateToken(searchForUser, "refresh");
        return new AuthenticationResponseDto()
                .accessToken(accessToken)
                .refreshToken(refreshToken);
    }
}
