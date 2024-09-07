package com.dogan.bilisim.domain.user;

import com.dogan.bilisim.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "APP_USER")
@SQLRestriction("DELETED = 0")
@SQLDelete(sql = "UPDATE APP_USER SET DELETED =  '1' WHERE ID = ?")
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@JsonSubTypes({
        @JsonSubTypes.Type(CustomerUser.class)
})
@NoArgsConstructor
public class AppUser extends BaseEntity {

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "ROLE", nullable = false)
    private UserRole role;

    public AppUser(UserRole role) {
        this.role = role;
    }
}
