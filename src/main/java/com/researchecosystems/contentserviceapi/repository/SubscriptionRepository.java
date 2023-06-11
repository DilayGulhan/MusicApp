package com.researchecosystems.contentserviceapi.repository;

import com.researchecosystems.contentserviceapi.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription , String> {
    Optional<Subscription> findByName(String name);
}
