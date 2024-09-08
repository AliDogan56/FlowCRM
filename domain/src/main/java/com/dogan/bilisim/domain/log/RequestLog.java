package com.dogan.bilisim.domain.log;

import com.dogan.bilisim.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "REQUEST_LOG")
@SQLRestriction("DELETED = 0")
@SQLDelete(sql = "UPDATE request_log SET DELETED =  1 WHERE ID = ?")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestLog extends BaseEntity {

    @Column(name = "METHOD")
    private String method;
    @Column(name = "URL")
    private String url;
    @Column(name = "IP_ADDRESS")
    private String ipAddress;
    @Column(name = "OS")
    private String operatingSystem;
    @Column(name = "USER_ID")
    private String userId;

}
