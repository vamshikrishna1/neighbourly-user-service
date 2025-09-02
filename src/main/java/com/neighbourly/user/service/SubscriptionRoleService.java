package com.neighbourly.user.service;

import com.neighbourly.user.repository.SubscriptionRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionRoleService {

    private final SubscriptionRoleRepository subscriptionRoleRepository;

    public Set<String> getRoleNamesBySubscriptionIds(Set<Long> subscriptionIds) {
        return subscriptionRoleRepository.findRoleNamesBySubscriptionIds(subscriptionIds);
    }

}
