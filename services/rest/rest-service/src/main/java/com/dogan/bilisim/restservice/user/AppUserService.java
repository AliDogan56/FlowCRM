package com.dogan.bilisim.restservice.user;


import com.dogan.bilisim.dao.user.AppUserRepository;
import com.dogan.bilisim.domain.user.AppUser;
import com.dogan.bilisim.domain.user.UserRole;
import com.dogan.bilisim.dto.common.AppUserDTO;
import com.dogan.bilisim.dto.common.converter.AppUserEntityDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserEntityDtoConverter appUserEntityDtoConverter;

    public Optional<AppUser> findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public Page<AppUserDTO> searchAppUsers(String username, UserRole userRole, Pageable pageable) {
        List<AppUser> appUsers = (List<AppUser>) appUserRepository.findAll();

        List<AppUserDTO> appUserDTOS = new ArrayList<>();


        appUsers = filterAppUsersByRole(userRole, appUsers);
        appUsers = filterAppUsersByUsername(username, appUsers);

        List<AppUser> paginatedList = paginateCustomerUsers(appUsers, pageable);

        paginatedList.forEach(appUser -> {
            AppUserDTO appUserDTO = appUserEntityDtoConverter.toAppUserDTO(appUser, new AppUserDTO());
            appUserDTOS.add(appUserDTO);
        });


        return new PageImpl<>(appUserDTOS, pageable, appUsers.size());

    }

    private List<AppUser> filterAppUsersByUsername(String username, List<AppUser> appUsers) {
        if (username != null) {
            appUsers = appUsers.stream().filter(customer -> customer.getUsername() != null &&
                    customer.getUsername().toLowerCase().startsWith(username.toLowerCase())).toList();
        }
        return appUsers;
    }

    private List<AppUser> filterAppUsersByRole(UserRole userRole, List<AppUser> appUsers) {
        if (userRole != null) {
            appUsers = appUsers.stream().filter(customer -> customer.getRole() != null &&
                    customer.getRole().equals(userRole)).toList();
        }
        return appUsers;
    }

    private List<AppUser> paginateCustomerUsers(List<AppUser> appUsers, Pageable pageable) {
        appUsers = sortCustomerUsers(appUsers, pageable);

        int total = appUsers.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);

        List<AppUser> paginatedList;
        if (start <= end) {
            paginatedList = appUsers.subList(start, end);
        } else {
            paginatedList = List.of();
        }
        return paginatedList;
    }

    private List<AppUser> sortCustomerUsers(List<AppUser> appUsers, Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            Comparator<AppUser> comparator = null;

            for (Sort.Order order : sort) {
                Comparator<AppUser> appUserComparator = Comparator.comparing(customerUser -> switch (order.getProperty()) {
                    case "id" -> customerUser.getId().toString();
                    case "username" -> customerUser.getUsername();
                    case "createdAt" -> customerUser.getCreatedAt().toString();
                    default -> null;
                }, Comparator.nullsLast(String::compareToIgnoreCase));

                if (order.isDescending()) {
                    appUserComparator = appUserComparator.reversed();
                }

                if (comparator == null) {
                    comparator = appUserComparator;
                } else {
                    comparator = comparator.thenComparing(appUserComparator);
                }
            }

            if (comparator != null) {
                appUsers = appUsers.stream().sorted(comparator).collect(Collectors.toList());
            }
        }
        return appUsers;
    }
}
