package com.nazran.springboot3firebseauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntitySoftDelete extends BaseEntity {

    @JsonIgnore
    @Column(name = "deleted_at")
    protected OffsetDateTime deletedAt;
}