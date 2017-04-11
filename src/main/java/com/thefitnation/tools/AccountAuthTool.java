package com.thefitnation.tools;

import com.thefitnation.domain.User;
import com.thefitnation.security.AuthoritiesConstants;

import java.util.Optional;

/**
 * Created by kylel on 4/9/2017.
 */
public class AccountAuthTool {

    public static boolean isUserLoggedInAndAdmin(Optional<User> user) {
        return (user.isPresent() && user.get().getAuthorities().contains(AuthoritiesConstants.ADMIN));
    }

    public static User getLoggedInUser(Optional<User> user) {
        return user.orElse(null);
    }
}
