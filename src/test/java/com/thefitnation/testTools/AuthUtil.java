package com.thefitnation.testTools;

import com.thefitnation.domain.User;
import com.thefitnation.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthUtil {
    public static Optional<User> logInUser(String login, String password, UserRepository userRepository) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.setContext(securityContext);

        return userRepository.findOneByLogin(login);
    }
}
