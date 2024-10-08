package com.dogan.bilisim.restcontroller.gateway.user;


import com.dogan.bilisim.domain.page.PageData;
import com.dogan.bilisim.domain.page.PageUtil;
import com.dogan.bilisim.domain.page.SortOrder;
import com.dogan.bilisim.dto.rest.user.CustomerUserDTO;
import com.dogan.bilisim.restcontroller.gateway.base.BaseController;
import com.dogan.bilisim.restservice.user.CustomerUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerUserController extends BaseController {

    private final CustomerUserService customerUserService;

    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @Operation(summary = "Create Customer User Event", security = @SecurityRequirement(name = "bearer"))
    @PostMapping("")
    public ResponseEntity<CustomerUserDTO> createCustomerUser(@RequestBody CustomerUserDTO customerUserDTO, HttpServletRequest request) {

        return ResponseEntity.ok(customerUserService.createCustomerUser(customerUserDTO, getUserPrefix(request)));
    }

    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @Operation(summary = "Update Customer User", security = @SecurityRequirement(name = "bearer"))
    @PostMapping("/update")
    public ResponseEntity<CustomerUserDTO> updateCustomerUser(@RequestBody CustomerUserDTO customerUserDTO, HttpServletRequest request) {
        return ResponseEntity.ok(customerUserService.updateCustomerUser(customerUserDTO, getUserPrefix(request)));
    }

    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @Operation(summary = "Delete Customer User By Id", security = @SecurityRequirement(name = "bearer"))
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteCustomerUser(@PathVariable("userId") Long userId, HttpServletRequest request) {
        customerUserService.deleteCustomerUser(userId, getUserPrefix(request));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @Operation(summary = "Find Customer User By Id", security = @SecurityRequirement(name = "bearer"))
    @GetMapping("/{userId}")
    public ResponseEntity<CustomerUserDTO> findCustomerUserById(@PathVariable("userId") Long userId, HttpServletRequest request) {
        return ResponseEntity.ok(customerUserService.findCustomerUserById(userId, getUserPrefix(request)));
    }

    @Operation(summary = "Search Customer User")
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<PageData<CustomerUserDTO>> searchCustomerUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "16") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String orderProperty,
            @RequestParam(required = false, defaultValue = "ASC") String orderType, HttpServletRequest request) {


        SortOrder sortOrder = new SortOrder(orderProperty, orderType.equals(SortOrder.Direction.ASC.toString()) ? SortOrder.Direction.ASC : SortOrder.Direction.DESC);
        Page<CustomerUserDTO> customerUsers = customerUserService.searchCustomerUsers(
                firstName,
                lastName,
                region,
                email,
                PageRequest.of(page, pageSize,
                        PageUtil.INSTANCE.toSort(sortOrder)), getUserPrefix(request));

        return ResponseEntity.ok(PageUtil.INSTANCE.pageToPageData(new PageImpl<>(customerUsers.getContent(),
                PageRequest.of(page, pageSize, PageUtil.INSTANCE.toSort(sortOrder)),
                customerUsers.getTotalElements())));

    }

}
