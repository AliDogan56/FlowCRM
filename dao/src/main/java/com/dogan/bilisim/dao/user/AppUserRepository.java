package com.dogan.bilisim.dao.user;


import com.dogan.bilisim.domain.user.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
}
