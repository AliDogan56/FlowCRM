package com.dogan.bilisim.dto.common.converter;


import com.dogan.bilisim.domain.user.AppUser;
import com.dogan.bilisim.dto.common.AppUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppUserEntityDtoConverter {

    public AppUserDTO toAppUserDTO(AppUser appUser, AppUserDTO newAppUserDTOInstance) {
        if (appUser == null) {
            return null;
        }
        newAppUserDTOInstance.setId(appUser.getId());
        newAppUserDTOInstance.setUsername(appUser.getUsername());
        newAppUserDTOInstance.setRole(appUser.getRole());
        return newAppUserDTOInstance;
    }

    public AppUser toAppUserEntity(AppUserDTO appUserDTO, AppUser newAppUserInstance) {
        if (appUserDTO == null) {
            return null;
        }
        newAppUserInstance.setId(appUserDTO.getId());
        newAppUserInstance.setUsername(appUserDTO.getUsername());
        newAppUserInstance.setRole(appUserDTO.getRole());
        return newAppUserInstance;
    }

}
