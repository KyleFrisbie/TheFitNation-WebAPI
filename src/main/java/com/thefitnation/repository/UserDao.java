package com.thefitnation.repository;

import com.thefitnation.model.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Created by michael on 2/19/17.
 */
@Repository
public interface UserDao extends PagingAndSortingRepository<UserCredential, Long> {

//    Optional<UserCredential> findOneByActivationKey(String activationKey);
//
//    List<UserCredential> findAllByActivatedIsFalseAndCreatedDateBefore(LocalDate localDate);
//
//    Optional<UserCredential> findOneByResetKey(String resetKey);
//
//    Optional<UserCredential> findOneByEmail(String email);
//
//    Optional<UserCredential> findOneByLogin(String login);

}
