package dev.anilerc.securitywithredis.auth;

import dev.anilerc.securitywithredis.user.UserDTO;
import dev.anilerc.securitywithredis.user.UserEntity;
import dev.anilerc.securitywithredis.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;


    public void register(@RequestBody UserDTO userDto) {
        var username = userDto.username();
        var encodedPassword = passwordEncoder.encode(userDto.password());

        var newUser = UserEntity.builder().username(username).password(encodedPassword).build();

        userRepository.save(newUser);
    }


    public void login(@RequestBody UserDTO loginReq, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

            var token = UsernamePasswordAuthenticationToken.unauthenticated(loginReq.username(), loginReq.password());

            var auth = authenticationManager.authenticate(token);

            SecurityContext context = securityContextHolderStrategy.createEmptyContext();

            context.setAuthentication(auth);

            securityContextHolderStrategy.setContext(context);

            securityContextRepository.saveContext(context, request, response);

    }

}
