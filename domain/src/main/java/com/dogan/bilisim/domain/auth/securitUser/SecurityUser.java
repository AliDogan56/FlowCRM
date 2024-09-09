package com.dogan.bilisim.domain.auth.securitUser;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
public class SecurityUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -797397440703066079L;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private UserPrincipal userPrincipal;

    public SecurityUser(@NotNull Collection<? extends GrantedAuthority> authorities, boolean enabled, UserPrincipal userPrincipal) {
        this.authorities = authorities;
        this.enabled = enabled;
        this.userPrincipal = userPrincipal;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

}
