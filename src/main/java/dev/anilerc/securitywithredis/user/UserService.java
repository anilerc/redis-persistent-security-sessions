package dev.anilerc.securitywithredis.user;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(UserDto userDto) {

        var username = userDto.username();
        var encodedPassword = passwordEncoder.encode(userDto.password());

        var newUser = UserEntity.builder().username(username).password(encodedPassword).build();

        userRepository.save(newUser);
    }

}
