package com.neighbourly.user.repository;

import com.neighbourly.user.constants.SUBSCRIPTION_TYPE;
import com.neighbourly.user.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByType(SUBSCRIPTION_TYPE type);
}
