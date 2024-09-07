package com.dogan.bilisim.dto.user;

import com.dogan.bilisim.domain.user.UserRole;
import com.dogan.bilisim.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO extends BaseDTO {

    private String username;
    private String password;
    private UserRole role;


}
