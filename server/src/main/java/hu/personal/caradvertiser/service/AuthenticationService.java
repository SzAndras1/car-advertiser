package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.mapper.UserMapper;
import hu.personal.caradvertiser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
}
