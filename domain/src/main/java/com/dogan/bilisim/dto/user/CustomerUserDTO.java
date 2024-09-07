package com.dogan.bilisim.dto.user;

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
