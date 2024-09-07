package com.dogan.bilisim.dao.auth;


import com.dogan.bilisim.domain.auth.token.AccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, Long> {
    Optional<AccessToken> findByJti(final String jti);

    void deleteAccessTokenByJti(final String jti);

    void deleteByUserId(String userId);
}
