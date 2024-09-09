package com.dogan.bilisim.rest.user;


import com.dogan.bilisim.domain.page.PageData;
import com.dogan.bilisim.domain.page.PageUtil;
import com.dogan.bilisim.domain.page.SortOrder;
import com.dogan.bilisim.domain.user.UserRole;
import com.dogan.bilisim.dto.user.AppUserDTO;
import com.dogan.bilisim.service.user.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AppUserController {

    private final AppUserService appUserService;

    @Operation(summary = "Search Customer User")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @GetMapping("/systemowner/search")
    public ResponseEntity<PageData<AppUserDTO>> searchSystemOwner(
            @RequestParam(required = false) String username,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "16") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String orderProperty,
            @RequestParam(required = false, defaultValue = "ASC") String orderType) {


        SortOrder sortOrder = new SortOrder(orderProperty, orderType.equals(SortOrder.Direction.ASC.toString()) ? SortOrder.Direction.ASC : SortOrder.Direction.DESC);
        Page<AppUserDTO> appUserDTOS = appUserService.searchAppUsers(
                username,
                UserRole.SYSTEM_OWNER,
                PageRequest.of(page, pageSize,
                        PageUtil.INSTANCE.toSort(sortOrder)));

        return ResponseEntity.ok(PageUtil.INSTANCE.pageToPageData(new PageImpl<>(appUserDTOS.getContent(),
                PageRequest.of(page, pageSize, PageUtil.INSTANCE.toSort(sortOrder)),
                appUserDTOS.getTotalElements())));

    }

    @Operation(summary = "Search Customer User")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @GetMapping("/systemadmin/search")
    public ResponseEntity<PageData<AppUserDTO>> searchSystemAdmins(
            @RequestParam(required = false) String username,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "16") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String orderProperty,
            @RequestParam(required = false, defaultValue = "ASC") String orderType) {


        SortOrder sortOrder = new SortOrder(orderProperty, orderType.equals(SortOrder.Direction.ASC.toString()) ? SortOrder.Direction.ASC : SortOrder.Direction.DESC);
        Page<AppUserDTO> appUserDTOS = appUserService.searchAppUsers(
                username,
                UserRole.SYSTEM_ADMIN,
                PageRequest.of(page, pageSize,
                        PageUtil.INSTANCE.toSort(sortOrder)));

        return ResponseEntity.ok(PageUtil.INSTANCE.pageToPageData(new PageImpl<>(appUserDTOS.getContent(),
                PageRequest.of(page, pageSize, PageUtil.INSTANCE.toSort(sortOrder)),
                appUserDTOS.getTotalElements())));

    }

}
