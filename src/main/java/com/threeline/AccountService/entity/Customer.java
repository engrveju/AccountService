package com.threeline.AccountService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity{

    private String name;

    private String surname;

    @NotNull
    private String email;

    private Long accountId;
}
