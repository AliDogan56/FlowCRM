package com.dogan.bilisim.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@ToString
@SQLRestriction("DELETED = 0")
@SQLDelete(sql = "UPDATE APP_USER SET DELETED =  '1' WHERE ID = ?")
@Table(name = "CUSTOMER_USER")
@Entity
public class CustomerUser extends AppUser {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EMAIL")
    protected String email;
    @Column(name = "REGION")
    protected String region;

    public CustomerUser() {
        super(UserRole.CUSTOMER);
    }
}
