package com.dogan.bilisim.domain.auth.token;

import com.dogan.bilisim.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;

@Entity
@SQLRestriction("DELETED = 0")
@SQLDelete(sql = "UPDATE access_token SET DELETED = 1 WHERE ID = ?")
@Table(name = "ACCESS_TOKEN", uniqueConstraints = {
        @UniqueConstraint(columnNames = "JTI", name = "acc_token_uniq_cons"),
        @UniqueConstraint(columnNames = "REFRESH_TOKEN_JTI", name = "ref_token_uniq_cons")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccessToken extends BaseEntity {

    @Column(name = "JTI", length = 36, unique = true)
    private String jti;
    @Column(name = "REFRESH_TOKEN_JTI", length = 36, unique = true)
    private String refreshTokenJti;
    @Column(name = "USER_ID")
    protected String userId;
    @Column(name = "EXPIRE_DATE")
    protected Instant expireDate;
}
