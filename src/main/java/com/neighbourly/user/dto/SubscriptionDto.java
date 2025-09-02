package com.neighbourly.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionDto {

    private Long id;
    @NotBlank(message = "Subscription type is mandatory")
    private String type;
    @NotBlank(message = "Subscription name is mandatory")
    private String name;
    private String description;
    @NotNull(message = "Subscription price is mandatory")
    private Double price;
    private Long duration;
    @NotNull(message = "Subscription quantity is mandatory")
    private Long qty;
}
