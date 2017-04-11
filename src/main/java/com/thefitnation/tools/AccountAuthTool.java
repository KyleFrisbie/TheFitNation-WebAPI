package com.thefitnation.tools;

import com.thefitnation.domain.Authority;
import com.thefitnation.domain.User;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.security.AuthoritiesConstants;
import com.thefitnation.security.SecurityUtils;

import java.util.Optional;

/**
 * Created by kylel on 4/9/2017.
 */
public class AccountAuthTool {

    public static boolean isUserLoggedInAndAdmin(Optional<User> user) {
        return (user.isPresent() && user.get().getAuthorities().contains(AuthoritiesConstants.ADMIN));
    }

    public static User getLoggedInUser(UserRepository userRepository) {
        Optional<User> user = userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
        return user.orElse(null);
    }

    public static boolean isAdmin(User user) {
        for (Authority role : user.getAuthorities()) {
            if (role.getName().equals(AuthoritiesConstants.ADMIN)) {
                return true;
            }
        }
        return false;
    }
}
