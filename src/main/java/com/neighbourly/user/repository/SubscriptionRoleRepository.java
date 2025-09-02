package com.neighbourly.user.repository;

import com.neighbourly.user.entity.SubscriptionRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubscriptionRoleRepository extends JpaRepository<SubscriptionRole, Long> {


    @Query(value = """
            SELECT DISTINCT r.role_name from roles r join subscription_roles sr on r.id=sr.role_id
            where sr.subscription_id in :subscriptionIds
            """, nativeQuery = true)
    Set<String> findRoleNamesBySubscriptionIds(@Param("subscriptionIds") Set<Long> subscriptionId);
}
