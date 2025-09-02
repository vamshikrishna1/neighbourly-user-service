package com.neighbourly.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscription_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionRole extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;


    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


}
