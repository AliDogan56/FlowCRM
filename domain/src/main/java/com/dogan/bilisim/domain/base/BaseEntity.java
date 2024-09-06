package com.dogan.bilisim.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Getter
    @Setter(onMethod = @__(@JsonIgnore))
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Getter
    @Setter(onMethod = @__(@JsonIgnore))
    @Column(name = "UPDATED_AT")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @Getter(onMethod = @__(@JsonIgnore))
    @Setter(onMethod = @__(@JsonIgnore))
    @Column(name = "DELETED")
    private Boolean deleted = false;
}
