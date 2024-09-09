package com.dogan.bilisim.dao.user;


import com.dogan.bilisim.domain.user.CustomerUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerUserRepository extends CrudRepository<CustomerUser, Long> {

    CustomerUser findByUsernameOrEmail(String username, String email);

    List<CustomerUser> findAllByUsernameOrEmail(String username, String email);
}
