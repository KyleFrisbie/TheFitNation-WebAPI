package com.thefitnation.repository;

import com.thefitnation.model.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 * @author michael menard
 * @version 0.0.1
 * @since 2/24/17.
 */
@Repository
public interface UserCredentialRepo  extends PagingAndSortingRepository<UserCredential, Long> {

//    /**
//     *
//     * @param activationKey
//     * @return
//     */
//    Optional<UserCredential> findOneByActivationKey(String activationKey);
//
//    /**
//     *
//     * @param localDate
//     * @return
//     */
//    List<UserCredential> findAllByActivatedIsFalseAndCreatedDateBefore(LocalDate localDate);
//
//    /**
//     *
//     * @param resetKey
//     * @return
//     */
//    Optional<UserCredential> findOneByResetKey(String resetKey);
//
//    /**
//     *
//     * @param email
//     * @return
//     */
//    Optional<UserCredential> findOneByEmail(String email);
//
//    /**
//     *
//     * @param login
//     * @return
//     */
//    Optional<UserCredential> findOneByLogin(String login);
}
