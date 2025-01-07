package com.threeline.AccountService.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity{

    private String name;

    private String surName;

    private Long accountId;
}
