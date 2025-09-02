package com.neighbourly.user.mapper;

import com.neighbourly.user.dto.SubscriptionDto;
import com.neighbourly.user.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDto toDto(Subscription subscription);
    Subscription toEntity(SubscriptionDto subscriptionDto);

    default java.util.List<SubscriptionDto> toDto(java.util.List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(this::toDto)
                .toList();
    }
}
