package com.dogan.bilisim.service.user;


import com.dogan.bilisim.dao.user.AppUserRepository;
import com.dogan.bilisim.domain.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public Optional<AppUser> findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

}
