package dev.anilerc.securitywithredis.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {

        userService.addUser(userDto);

        return new ResponseEntity<>("User creation success!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserDto loginReq, HttpServletRequest request, HttpServletResponse response) {
        try {
            var token = UsernamePasswordAuthenticationToken.unauthenticated(loginReq.username(), loginReq.password());

            var auth = authenticationManager.authenticate(token);

            SecurityContext context = securityContextHolderStrategy.createEmptyContext();

            context.setAuthentication(auth);

            securityContextHolderStrategy.setContext(context);

            securityContextRepository.saveContext(context, request, response);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

}
