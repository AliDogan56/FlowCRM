package com.dogan.bilisim.restservice.auth;


import com.dogan.bilisim.domain.auth.securitUser.SecurityUserDetails;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<SecurityUserDetails> login(String username, String password);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<SecurityUserDetails> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param request the current request
     */
    void logout(HttpServletRequest request);

}
