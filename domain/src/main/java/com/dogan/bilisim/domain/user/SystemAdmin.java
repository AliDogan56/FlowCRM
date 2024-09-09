package com.dogan.bilisim.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "SYSTEM_ADMIN")
@SQLRestriction("DELETED = 0")
@SQLDelete(sql = "UPDATE app_user SET DELETED =  1 WHERE ID = ?")
@Getter
@Setter
@ToString
public class SystemAdmin extends AppUser {

    public SystemAdmin() {
        super(UserRole.SYSTEM_ADMIN);
    }
}
