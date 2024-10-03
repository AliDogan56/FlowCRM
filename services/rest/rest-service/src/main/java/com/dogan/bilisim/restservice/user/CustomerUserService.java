package com.dogan.bilisim.restservice.user;


import com.dogan.bilisim.dao.user.CustomerUserRepository;
import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;
import com.dogan.bilisim.domain.user.CustomerUser;
import com.dogan.bilisim.dto.notification.SendNotificationDTO;
import com.dogan.bilisim.dto.rest.user.CustomerUserDTO;
import com.dogan.bilisim.dto.rest.user.converter.CustomerUserEntityDtoConverter;
import com.dogan.bilisim.restservice.auth.exception.FlowCrmRestException;
import com.dogan.bilisim.restservice.kafka.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CustomerUserService {

    private final CustomerUserRepository customerUserRepository;
    private final CustomerUserEntityDtoConverter customerUserEntityDtoConverter;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducerService;


    public CustomerUserDTO createCustomerUser(CustomerUserDTO customerUserDTO, String userPrefix) {

        validate(customerUserDTO);
        if (customerUserRepository.findByUsernameOrEmail(customerUserDTO.getUsername(), customerUserDTO.getEmail()) != null) {
            throw new FlowCrmRestException("ApiMessages.ThereIsAlreadyUserWithThatUsernameOrEmail", FlowCrmErrorCode.BAD_REQUEST_PARAMS);
        }

        CustomerUser customerUser = customerUserEntityDtoConverter.toCustomerUserEntity(customerUserDTO);
        customerUser.setPassword(passwordEncoder.encode(customerUserDTO.getPassword()));

        sendNotification(userPrefix, "Customer User Has Been Created!");
        return customerUserEntityDtoConverter.toCustomerUserDTO(customerUserRepository.save(customerUser));
    }

    private void validate(CustomerUserDTO customerUserDTO) {
        if (customerUserDTO.getUsername() == null) {
            throw new FlowCrmRestException("ApiMessages.UsernameCanNotBeNull!", FlowCrmErrorCode.BAD_REQUEST_PARAMS);
        }

        if (customerUserDTO.getFirstName() == null) {
            throw new FlowCrmRestException("ApiMessages.FirstnameCanNotBeNull!", FlowCrmErrorCode.BAD_REQUEST_PARAMS);
        }

        if (customerUserDTO.getLastName() == null) {
            throw new FlowCrmRestException("ApiMessages.LastnameCanNotBeNull!", FlowCrmErrorCode.BAD_REQUEST_PARAMS);
        }

        if (customerUserDTO.getEmail() == null) {
            throw new FlowCrmRestException("ApiMessages.EmailCanNotBeNull!", FlowCrmErrorCode.BAD_REQUEST_PARAMS);
        }

        if (customerUserDTO.getPassword() == null) {
            throw new FlowCrmRestException("ApiMessages.PasswordCanNotBeNull!", FlowCrmErrorCode.BAD_REQUEST_PARAMS);
        }
    }

    public CustomerUserDTO updateCustomerUser(CustomerUserDTO customerUserDTO, String userPrefix) {
        try {
            validate(customerUserDTO);
        } catch (FlowCrmRestException e) {
            if (!e.getMessage().equals("ApiMessages.PasswordCanNotBeNull!")) {
                throw e;
            }
        }
        CustomerUser toBeUpdatedCustomerUser = customerUserRepository.findById(customerUserDTO.getId()).orElseThrow(() -> new FlowCrmRestException("ApiMessages.ThereIsNoCustomerUser", FlowCrmErrorCode.BAD_REQUEST_PARAMS));
        List<CustomerUser> customerUserList = customerUserRepository.findAllByUsernameOrEmail(customerUserDTO.getUsername(), customerUserDTO.getEmail());
        customerUserList = customerUserList.stream().filter(customerUser -> !Objects.equals(customerUser.getId(), toBeUpdatedCustomerUser.getId())).toList();
        if (!customerUserList.isEmpty()) {
            throw new FlowCrmRestException("ApiMessages.ThereIsAlreadyUserWithThatUsernameOrEmail", FlowCrmErrorCode.BAD_REQUEST_PARAMS);
        }

        toBeUpdatedCustomerUser.setUsername(customerUserDTO.getUsername());
        toBeUpdatedCustomerUser.setFirstName(customerUserDTO.getFirstName());
        toBeUpdatedCustomerUser.setLastName(customerUserDTO.getLastName());
        toBeUpdatedCustomerUser.setPassword(customerUserDTO.getPassword() == null ? toBeUpdatedCustomerUser.getPassword() : passwordEncoder.encode(customerUserDTO.getPassword()));
        toBeUpdatedCustomerUser.setEmail(customerUserDTO.getEmail());
        toBeUpdatedCustomerUser.setRegion(customerUserDTO.getRegion());

        sendNotification(userPrefix, "Customer User Has Been Updated!");
        return customerUserEntityDtoConverter.toCustomerUserDTO(customerUserRepository.save(toBeUpdatedCustomerUser));

    }

    public void deleteCustomerUser(Long customerUserId, String userPrefix) {
        sendNotification(userPrefix, "Customer User Has Been Deleted!");
        customerUserRepository.deleteById(customerUserId);
    }

    public CustomerUserDTO findCustomerUserById(Long customerUserId, String userPrefix) {
        CustomerUser customerUser = customerUserRepository.findById(customerUserId).orElseThrow(() -> new FlowCrmRestException("ApiMessages.ThereIsNoCustomerUser", FlowCrmErrorCode.BAD_REQUEST_PARAMS));
        sendNotification(userPrefix, "Customer User Has Been Accessed!");
        return customerUserEntityDtoConverter.toCustomerUserDTO(customerUser);
    }

    private void sendNotification(String userPrefix, String message) {
        try {
            SendNotificationDTO sendNotificationDTO = new SendNotificationDTO(userPrefix, message);
            kafkaProducerService.sendMessage(sendNotificationDTO);
        } catch (JsonProcessingException ignored) {

        }

    }

    public Page<CustomerUserDTO> searchCustomerUsers(String firstName, String lastName, String region, String email, Pageable pageable, String userPrefix) {
        List<CustomerUser> customerUsers = (List<CustomerUser>) customerUserRepository.findAll();

        List<CustomerUserDTO> customerUserDTOS = new ArrayList<>();


        customerUsers = filterCustomerUsersByFirstName(firstName, customerUsers);
        customerUsers = filterCustomerUsersByLastName(lastName, customerUsers);
        customerUsers = filterCustomerUsersByRegion(region, customerUsers);
        customerUsers = filterCustomerUsersByEmail(email, customerUsers);

        List<CustomerUser> paginatedList = paginateCustomerUsers(customerUsers, pageable);

        paginatedList.forEach(customerUser -> {
            CustomerUserDTO customerUserDTO = customerUserEntityDtoConverter.toCustomerUserDTO(customerUser);
            customerUserDTOS.add(customerUserDTO);
        });

        sendNotification(userPrefix, "Customer Users Has Been Searched!");

        return new PageImpl<>(customerUserDTOS, pageable, customerUsers.size());
    }

    private List<CustomerUser> filterCustomerUsersByFirstName(String firstName, List<CustomerUser> customerUsers) {
        if (firstName != null) {
            customerUsers = customerUsers.stream().filter(customer -> customer.getFirstName() != null &&
                    customer.getFirstName().toLowerCase().startsWith(firstName.toLowerCase())).toList();
        }
        return customerUsers;
    }

    private List<CustomerUser> filterCustomerUsersByLastName(String lastName, List<CustomerUser> customerUsers) {
        if (lastName != null) {
            customerUsers = customerUsers.stream().filter(customer -> customer.getFirstName() != null &&
                    customer.getLastName().toLowerCase().startsWith(lastName.toLowerCase())).toList();
        }
        return customerUsers;
    }

    private List<CustomerUser> filterCustomerUsersByRegion(String region, List<CustomerUser> customerUsers) {
        if (region != null) {
            customerUsers = customerUsers.stream().filter(customer -> customer.getFirstName() != null &&
                    customer.getRegion().toLowerCase().startsWith(region.toLowerCase())).toList();
        }
        return customerUsers;
    }

    private List<CustomerUser> filterCustomerUsersByEmail(String email, List<CustomerUser> customerUsers) {
        if (email != null) {
            customerUsers = customerUsers.stream().filter(customer -> customer.getFirstName() != null &&
                    customer.getEmail().toLowerCase().startsWith(email.toLowerCase())).toList();
        }
        return customerUsers;
    }

    private List<CustomerUser> paginateCustomerUsers(List<CustomerUser> customerUsers, Pageable pageable) {
        customerUsers = sortCustomerUsers(customerUsers, pageable);

        int total = customerUsers.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);

        List<CustomerUser> paginatedList;
        if (start <= end) {
            paginatedList = customerUsers.subList(start, end);
        } else {
            paginatedList = List.of();
        }
        return paginatedList;
    }

    private List<CustomerUser> sortCustomerUsers(List<CustomerUser> customerUsers, Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            Comparator<CustomerUser> comparator = null;

            for (Sort.Order order : sort) {
                Comparator<CustomerUser> customerComparator = Comparator.comparing(customerUser -> switch (order.getProperty()) {
                    case "id" -> customerUser.getId().toString();
                    case "username" -> customerUser.getUsername();
                    case "firstName" -> customerUser.getFirstName();
                    case "email" -> customerUser.getEmail();
                    case "createdAt" -> customerUser.getCreatedAt().toString();
                    default -> null;
                }, Comparator.nullsLast(String::compareToIgnoreCase));

                if (order.isDescending()) {
                    customerComparator = customerComparator.reversed();
                }

                if (comparator == null) {
                    comparator = customerComparator;
                } else {
                    comparator = comparator.thenComparing(customerComparator);
                }
            }

            if (comparator != null) {
                customerUsers = customerUsers.stream().sorted(comparator).collect(Collectors.toList());
            }
        }
        return customerUsers;
    }
}
