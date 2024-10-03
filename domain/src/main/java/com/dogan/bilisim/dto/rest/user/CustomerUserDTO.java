package com.dogan.bilisim.dto.rest.user;

import com.dogan.bilisim.dto.common.AppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserDTO extends AppUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String region;


}
