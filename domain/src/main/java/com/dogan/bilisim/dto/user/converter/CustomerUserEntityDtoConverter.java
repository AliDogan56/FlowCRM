package com.dogan.bilisim.dto.user.converter;

import com.dogan.bilisim.domain.user.CustomerUser;
import com.dogan.bilisim.dto.user.CustomerUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerUserEntityDtoConverter extends AppUserEntityDtoConverter {

    public CustomerUserDTO toCustomerUserDTO(CustomerUser customerUser) {
        CustomerUserDTO customerUserDTO = (CustomerUserDTO) super.toAppUserDTO(customerUser, new CustomerUserDTO());
        customerUserDTO.setFirstName(customerUser.getFirstName());
        customerUserDTO.setLastName(customerUser.getLastName());
        customerUserDTO.setRegion(customerUser.getRegion());
        customerUserDTO.setEmail(customerUser.getEmail());
        return customerUserDTO;
    }

    public CustomerUser toCustomerUserEntity(CustomerUserDTO customerUserDTO) {
        CustomerUser customerUser = (CustomerUser) super.toAppUserEntity(customerUserDTO, new CustomerUser());
        customerUser.setFirstName(customerUserDTO.getFirstName());
        customerUser.setLastName(customerUserDTO.getLastName());
        customerUser.setRegion(customerUserDTO.getRegion());
        customerUser.setEmail(customerUserDTO.getEmail());
        return customerUser;
    }


}
