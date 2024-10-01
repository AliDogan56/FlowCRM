package com.dogan.bilisim.domain.auth.securitUser;

import com.dogan.bilisim.domain.auth.token.AccessJwtToken;
import com.dogan.bilisim.domain.auth.token.JwtToken;
import com.dogan.bilisim.domain.user.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class SecurityUserDetails implements UserDetails {
    private JwtToken jwtToken;
    private Long id;
    private String username;
    private String password;
    private List<String> roles;

    public static SecurityUserDetails build(AppUser user, String jti, String token, List<String> scopes, Claims claims, final Instant expireDate) {
        return SecurityUserDetails.builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getUsername())
                .jwtToken(new AccessJwtToken(token, jti, claims, expireDate))
                .roles(scopes).build();
    }

    @JsonIgnore
    @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
