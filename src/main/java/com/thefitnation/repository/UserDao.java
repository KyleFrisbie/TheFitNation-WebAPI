package com.thefitnation.repository;

import com.thefitnation.model.*;
import java.time.*;
import java.util.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Created by michael on 2/19/17.
 */
@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(LocalDate localDate);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

}
