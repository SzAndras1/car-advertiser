package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.exception.EntityNotFoundException;
import hu.personal.caradvertiser.exception.NotValidException;
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

import java.util.LinkedList;
import java.util.List;

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
        validateUser(userDto);
        userRepository.findByUsername(userDto.getUsername())
                .ifPresent(e -> {
                    throw new NotValidException("username", "The username already exists.");
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
                .orElseThrow(() -> new EntityNotFoundException("username", "This username does not exist."));
        String accessToken = jwtService.generateToken(searchForUser, "access");
        String refreshToken = jwtService.generateToken(searchForUser, "refresh");
        return new AuthenticationResponseDto()
                .accessToken(accessToken)
                .refreshToken(refreshToken);
    }

    private void validateUser(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        List<String> wrongFields = new LinkedList<>();
        if (username.length() < 3 || username.length() > 50) {
            wrongFields.add("username");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            wrongFields.add("password");
        }
        if (!email.matches("^(.+)@(\\S+)$")) {
            wrongFields.add("email");
        }
        if (wrongFields.size() > 0) {
            throw new NotValidException(String.join(", ", wrongFields), "Data not provided correctly");
        }
    }
}
