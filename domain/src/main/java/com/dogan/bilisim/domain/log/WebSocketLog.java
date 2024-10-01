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
@Table(name = "WEB_SOCKET_LOG")
@SQLRestriction("DELETED = 0")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WebSocketLog extends BaseEntity {

    @Column(name = "SESSION_ID")
    private String sessionId;
    @Column(name = "USER_PREFIX")
    private String userPrefix;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "STATUS")
    private WebSocketLogStatus webSocketLogStatus;

}
